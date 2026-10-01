package org.example;

import org.view.View;

/**
 * This class contains all game logic: dealing cards, checking Black Jacks, playing, checking
 * victory conditions and displaying game state in view.
 *
 * @param <T> type of the view object that should be used for IO. Object must belong to a subclass
 *          of View and support its abstract methods.
 */
public class Game<T extends View> {
    Deck deck;
    User<T> user;
    Dealer<T> dealer;
    T view;

    /**
     * Creates deck, user and dealer objects and configures them.
     *
     * @param view view object to be used for IO.
     */
    Game(T view) {
        this.view = view;
        this.deck = new Deck();
        this.user = new User<>();
        this.dealer = new Dealer<>();
        this.view.setContext(user, dealer, deck);
        this.user.setView(view);
        this.dealer.setView(view);
        Player.setDeck(this.deck);
    }

    /**
     * Orchestrates the game by calling User's, Dealer's and deck methods, checks for blackjack.
     */
    public void play() {
        this.deck.shuffle();

        this.dealer.start();
        this.user.start();

        this.view.displayStart();

        if (this.user.getValue() == 21) {
            this.view.displayUserBlackJack();
            return;
        }
        if (this.dealer.getValue() == 21) {
            this.view.displayDealerBlackJack();
            return;
        }

        this.user.play();
        if (user.getValue() > 21) {
            this.view.displayDealerVictory();
            return;
        }
        this.dealer.play();

        this.determineVictory();
    }

    /**
     * Contains logic determining User's or Dealer's victory in case if nobody had gotten Black
     * Jack. Does not return anything, only calls IO methods to display game results.
     */
    private void determineVictory() {
        if (this.user.getValue() > 21) {
            this.view.displayDealerVictory();
            return;
        }
        if (this.dealer.getValue() > 21) {
            this.view.displayUserVictory();
            return;
        }

        if (this.user.getValue() > this.dealer.getValue()) {
            this.view.displayUserVictory();
        } else if (this.dealer.getValue() > this.user.getValue()) {
            this.view.displayDealerVictory();
        } else {
            this.view.displayDraw();
        }
    }
}