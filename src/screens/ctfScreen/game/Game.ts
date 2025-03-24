import {action, computed, makeObservable, observable, runInAction} from 'mobx';
import {generateInitialCards} from './generateInitialCards';
import {Card} from './Card';
import {Timer} from './Timer';
import verifySignature from '../util/ObfuscatedGameValidator.js';
import Toast from 'react-native-toast-message';

import {NativeModules} from 'react-native';
import { CardState } from './CardState.ts';
import { CardType } from './CardType.ts';
import deobfuscate from '../util/ObfuscatedAes.js';
const { WelcomeCTF } = NativeModules;

export class Game {
  serverConnectionEstablished: Boolean = true;
  cheatDetected: Boolean = false;
  session: String = '';
  numbersOfGames = 0;
  cards: Card[] = [];
  clicks = 0;
  timer = new Timer();
  gameState: String = '';
  deckOpenedByDebugMenu: boolean = false;
  /* Nobody can find it here, right?
  I heard that reverse engineering is illegal, or something */
  API_KEY = '458C0DC0-AA89-4B6D-AF74-564981068AD8';

  constructor() {
    makeObservable(this, {
      cards: observable,
      clicks: observable,
      startGame: action,
      onClick: action,
      moves: computed,
      isCompleted: computed,
    });
  }

  alert_if_reconnect(){
    // test if we regained connection to the server again
    if (!this.serverConnectionEstablished){
      //alert the user, that we now have a connection to the server
      Toast.show({
        type: 'info',
        text1: 'Established connection to server.',
        position: 'bottom',
      });
      this.serverConnectionEstablished = true;
    }
  }

  async startGame() {
    this.cards = generateInitialCards();

    // try to get a valid new deck from the server, if the server is not available just skip this step
    try {
      console.log('init server game');
      const cardTypeNames: CardType[] = Object.values(CardType) as CardType[];
      const controller = new AbortController();
      const timeoutId = setTimeout(() => controller.abort(), 200);
      const response = await fetch('https://anticheat.mas-reference-app.org:8001/8dj21k01sx/api/v1/init', {
        method: 'POST',
        signal: controller.signal,
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          cards: cardTypeNames,
        }),
      });
      clearTimeout(timeoutId);
      const json = await response.json();

      this.alert_if_reconnect();

      const status = json.payload.status;

      if (status === 'error'){
        this.cheatDetected = true;
        throw Error(json.payload.reason);
      }

      // get the session, and decrypt the cards and init the state of the game
      this.session = json.payload.session;

      const serverDeck = JSON.parse(deobfuscate(json.payload.encryptedServerDeck));
      for (var d in serverDeck){
        this.cards[Number(d)].type = serverDeck[d] as CardType;
      }
      const valid =  await verifySignature(json.payload, json.signature);
      if (!valid){
        this.cheatDetected = true;
        Toast.show({
          type: 'error',
          text1: 'Cheating attempt detected.',
          position: 'bottom',
        });
      }

    } catch (error) {
      console.log(error);
      if (this.serverConnectionEstablished && (String(error).includes('Network request failed') || String(error).includes('Abort'))){
        //alert the user, that we lost the connection to the server
        Toast.show({
          type: 'error',
          text1: 'Lost connection to server.',
          position: 'bottom',
        });
        this.serverConnectionEstablished = false;
      }
    }

    this.deckOpenedByDebugMenu = false;

    // we want the game stat to be in memory for easy memory scan
    const cardsWithoutGame = this.cards.map(card => {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const { game, ...rest } = card;
      return rest;
    });
    const cardsString = JSON.stringify(cardsWithoutGame);
    this.gameState = cardsString.toString();
    runInAction(() => {
      this.clicks = 0;
    });
    this.timer.reset();
  }

  onClick(card: Card) {
    // console.log('onClick() card', card.type);
    if (!this.timer.isStarted) {
      this.timer.start();
    }
    if (this.notMatchedCards().length > 0) {
      // Ignore clicks while cards are not matched (ie red)
      return;
    }
    this.clicks++;
    card.makeVisible();
    this.evaluateMatch();
  }

  async evaluateMatch() {
    const visibleCards = this.visibleCards();
    // console.log('visibleCards.length', visibleCards.length);
    if (visibleCards.length !== 2) {
      return;
    }
    if (visibleCards[0].matches(visibleCards[1])) { // Correct match

      // update the server state
      try {
        var deck = Array(this.cards.length).fill('');
        for (var d in deck){
          if (this.cards[d].state === CardState.Visible || this.cards[d].state === CardState.Matched){
            deck[d] = this.cards[d].type.toString();
          }
        }
        const controller = new AbortController();
        const timeoutId = setTimeout(() => controller.abort(), 200);
        const response = await fetch('https://anticheat.mas-reference-app.org:8001/8dj21k01sx/api/v1/validate', {
          method: 'POST',
          signal: controller.signal,
          headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            session: this.session,
            openDeck: deck,
          }),
        });
        clearTimeout(timeoutId);
        const json = await response.json();

        this.alert_if_reconnect();

        const status = json.payload.status;
        // validate the signature
        const valid =  await verifySignature(json.payload, json.signature);
        if (!valid){
          this.cheatDetected = true;
          Toast.show({
            type: 'error',
            text1: 'Cheating attempt detected.',
            position: 'bottom',
          });
        }
        // test if the server detected any fishy behavior
        if (status === 'error'){
          this.cheatDetected = true;
          throw Error(json.payload.reason);
        }
      } catch (error) {
        console.log(error);
        if (this.serverConnectionEstablished && (String(error).includes('Network request failed') || String(error).includes('Abort'))){
          //alert the user, that we lost the connection to the server
          Toast.show({
            type: 'error',
            text1: 'Lost connection to server.',
            position: 'bottom',
          });
          this.serverConnectionEstablished = false;
        }
      }

      WelcomeCTF.addStreak();
      visibleCards[0].makeMatched();
      visibleCards[1].makeMatched();
      WelcomeCTF.matched();
      if (this.isCompleted) {
        this.numbersOfGames += 1;
        this.timer.stop();
      }
    } else { // Wrong match
      WelcomeCTF.resetStreak();
      visibleCards[0].hide();
      visibleCards[1].hide();
    }
  }

  get streak (): number { return WelcomeCTF.getStreak(); }

  get moves(): number {
    return Math.floor(this.clicks / 2);
  }

  get isCompleted(): boolean {
    return this.cards.every(card => card.isMatched);
  }

  get totalScore(): number {
    /* Delegated to native code */
    return WelcomeCTF.getScore();
  }

  // helpers

  notMatchedCards(): Card[] {
    return this.cards.filter(card => card.isNotMatched);
  }

  visibleCards(): Card[] {
    return this.cards.filter(card => card.isVisible);
  }

  async showAllCards(){
    //reset the game, it should not be THAT easy
    await this.startGame();
    this.deckOpenedByDebugMenu = true;
    for (const card in this.cards){
      this.cards[card].makeVisible();
    }
    setTimeout(() => {
      for (const card in this.cards){
        runInAction(() => {
          this.cards[card].cover();
        });
      }
    }, 3000);
  }

  perfectGame() : Boolean {
     if (this.numbersOfGames === 0){
      return false;
     }

    return this.moves === (this.cards.length / 2);
  }

  noDebugPerfectGame() : Boolean {
    return (!this.deckOpenedByDebugMenu && this.perfectGame()) ? true : false;
  }

  serverValidatedWin() : Boolean {
    if (this.serverConnectionEstablished){
      if (this.cheatDetected){
        // validate result with the server
        return false;
      }
      else{
        return false;
      }
    }
    else{
      return false;
    }
  }
}


export const game = new Game();


