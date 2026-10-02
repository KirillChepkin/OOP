package org.example;

import org.view.View;

import java.util.Objects;

/**
 * This class contains all game logic: dealing cards, checking Black Jacks, playing, checking
 * victory conditions and displaying game state in view.
 *
 * @param <T> type of the view object that should be used for IO. Object must belong to a subclass
 *          of View and support its abstract methods.
 */
public class Game<T extends View> {
    private Deck deck;
    private User<T> user;
    private Dealer<T> dealer;
    private final T view;

    private Result result;

    /**
     * Creates deck, user and dealer objects and configures them.
     *
     * @param view view object to be used for IO.
     */
    Game(T view) {
//        System.out.println(this.deck);

        this.view = view;
        this.dealer = new Dealer<>();
        this.user = new User<>();
        this.deck = new Deck();
        this.user.setView(view);
        this.dealer.setView(view);

        this.view.setContext(this.user, this.dealer, this.deck);
        this.user.setView(view);
        this.dealer.setView(view);
        this.user.setDeck(this.deck);
        this.dealer.setDeck(this.deck);
    }

    public Result getResult() {
        return this.result;
    }

    /**
     * Orchestrates the game by calling User's, Dealer's and deck methods, checks for blackjack.
     */
    public void play() {
        this.deck.shuffle();

        this.user.start();
        this.dealer.start();

        this.view.displayStart();

        if (this.user.getValue() == 21) {
            this.result = Result.USER_BLACK_JACK;
            return;
        }
        if (this.dealer.getValue() == 21) {
            this.result = Result.DEALER_BLACK_JACK;
            return;
        }

        this.user.play();
        if (user.getValue() > 21) {
            this.result = Result.DEALER_VICTORY;
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
            this.result = Result.DEALER_VICTORY;
            return;
        }
        if (this.dealer.getValue() > 21) {
            this.result = Result.USER_VICTORY;
            return;
        }

        if (this.user.getValue() > this.dealer.getValue()) {
            this.result = Result.USER_VICTORY;
        } else if (this.dealer.getValue() > this.user.getValue()) {
            this.result = Result.DEALER_VICTORY;
        } else {
            this.result = Result.DRAW;
        }
    }

    public void declareResult() {
        switch (this.result) {
            case Result.DRAW:
                this.view.displayDraw();
                break;
            case Result.DEALER_VICTORY:
                this.view.displayDealerVictory();
                break;
            case Result.USER_VICTORY:
                this.view.displayUserVictory();
                break;
            case Result.USER_BLACK_JACK:
                this.view.displayUserBlackJack();
                break;
            case Result.DEALER_BLACK_JACK:
                this.view.displayDealerBlackJack();
                break;
        }
    }
}