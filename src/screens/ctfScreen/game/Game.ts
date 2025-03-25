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

// this code will be obfuscated in the release build
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

  async startGame() {
    this.cheatDetected = false;
    this.cards = generateInitialCards();

    // try to get a valid new deck from the server, if the server is not available just skip this step
    try {
      console.log('init server game');
      const cardTypeNames: CardType[] = Object.values(CardType) as CardType[];
      const controller = new AbortController();
      const timeoutId = setTimeout(() => controller.abort(), 500);
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

      // validate the signature
      const valid =  await verifySignature(json.payload, json.signature);
      if (!valid){
        this.cheatDetected = true;
      }

      // test if we got connection to the server again
      if (!this.serverConnectionEstablished){
        //alert the user, that we now have a connection to the server
        Toast.show({
          type: 'info',
          text1: 'Established connection to server.',
          position: 'bottom',
        });
        this.serverConnectionEstablished = true;
      }

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

    if(this.cheatDetected){
      Toast.show({
        type: 'error',
        text1: 'Cheating attempt detected.',
        position: 'bottom',
      });
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
    if (visibleCards.length !== 2) {
      return;
    }
    if (visibleCards[0].matches(visibleCards[1])) { // Correct match

      // update server
      try {
        var deck = Array(this.cards.length).fill('');
        for (var d in deck){
          if (this.cards[d].state === CardState.Visible || this.cards[d].state === CardState.Matched){
            deck[d] = this.cards[d].type.toString();
          }
        }
        const controller = new AbortController();
        const timeoutId = setTimeout(() => controller.abort(), 500);
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
        // validate the signature
        const valid =  await verifySignature(json.payload, json.signature);
        if (!valid){
          this.cheatDetected = true;
        }

        const status = json.payload.status;
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

      if(this.cheatDetected){
        Toast.show({
          type: 'error',
          text1: 'Cheating attempt detected.',
          position: 'bottom',
        });
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

  debugPerfectGame() : Boolean {
    if (this.deckOpenedByDebugMenu){
      return this.moves === (this.cards.length / 2);
    }
    else {
      return false;
    }
  }

  noDebugPerfectGame() : Boolean {
    if (!this.deckOpenedByDebugMenu){
      return this.moves === (this.cards.length / 2);
    }
    else {
      return false;
    }
  }

  serverValidatedWin() : Boolean {
    if (this.serverConnectionEstablished){
      if (this.cheatDetected){
        return false;
      }
      else{
        return true;
      }
    }
    else{
      return false;
    }
  }
}


export const game = new Game();


