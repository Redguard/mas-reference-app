import {action, autorun, computed, makeObservable, observable} from 'mobx';
import {generateInitialCards} from './generateInitialCards';
import {Card} from './Card';
import {Timer} from './Timer';

import {NativeModules} from 'react-native';
const { WelcomeCTF } = NativeModules;

export class Game {
  cards: Card[] = [];
  clicks = 0;
  streak: number = 0;
  timer = new Timer();
  /* Nobody can find it here, right?
  I heard that reverse engineering is illegal, or something */
  API_KEY = "458C0DC0-AA89-4B6D-AF74-564981068AD8";

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
    console.log('startGame()');
    this.cards = generateInitialCards();
    this.clicks = 0;
    this.timer.reset();
  }

  onClick(card: Card) {
    console.log('onClick() card', card.type);
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
    console.log('visibleCards.length', visibleCards.length);
    if (visibleCards.length !== 2) {
      return;
    }
    if (visibleCards[0].matches(visibleCards[1])) {
      this.streak += 1;
      visibleCards[0].makeMatched();
      visibleCards[1].makeMatched();
      /* native */
      WelcomeCTF.matched()
      if (this.isCompleted) {
        this.timer.stop();
      }
    } else {
      this.streak = 0;
      visibleCards[0].hide();
      visibleCards[1].hide();
    }
  }

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
    for (const card in Card){
      console.log("Show all cards");
      console.log(card);
    }
  }
}

export const game = new Game();

autorun(() => {
  if (__DEV__) {
    console.log('autorun game clicks', game.clicks, 'cards', game.cards);
  }
});
