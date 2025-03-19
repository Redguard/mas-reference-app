import {action, computed, makeObservable, observable} from 'mobx';
import {generateInitialCards} from './generateInitialCards';
import {Card} from './Card';
import {Timer} from './Timer';

import {NativeModules} from 'react-native';
const { WelcomeCTF } = NativeModules;

export class Game {
  numbersOfGames = 0;
  cards: Card[] = [];
  clicks = 0;
  timer = new Timer();
  /* Nobody can find it here, right?
  I heard that reverse engineering is illegal, or something */
  API_KEY = '458C0DC0-AA89-4B6D-AF74-564981068AD8';
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

  startGame() {
    this.cards = generateInitialCards();
    this.deckOpenedByDebugMenu = false;

    // we want the game stat to be in memory for easy memory scan
    const cardsWithoutGame = this.cards.map(card => {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const { game, ...rest } = card;
      return rest;
    });
    const cardsString = JSON.stringify(cardsWithoutGame);
    this.gameState = cardsString.toString();
    this.clicks = 0;
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

  evaluateMatch() {
    const visibleCards = this.visibleCards();
    // console.log('visibleCards.length', visibleCards.length);
    if (visibleCards.length !== 2) {
      return;
    }
    if (visibleCards[0].matches(visibleCards[1])) { // Correct match
      WelcomeCTF.addStreak();
      visibleCards[0].makeMatched();
      visibleCards[1].makeMatched();
      /* native */
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

  showAllCards(){
    //reset the game, it should not be THAT easy
    this.startGame();
    this.deckOpenedByDebugMenu = true;
    for (const card in this.cards){
      this.cards[card].makeVisible();
    }
    setTimeout(() => {
      for (const card in this.cards){
        this.cards[card].cover();
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
    // TODO: Implement
    return false;
  }
}


export const game = new Game();


