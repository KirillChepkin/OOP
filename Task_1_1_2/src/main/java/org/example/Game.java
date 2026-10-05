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
    private Shoe shoe;
    private User<T> user;
    private Dealer<T> dealer;
    private final T view;

    private Result result;

    /**
     * Creates deck, user and dealer objects and configures them.
     *
     * @param view view object to be used for IO.
     */
    public Game(T view) {
        this.view = view;
        this.dealer = new Dealer<>();
        this.user = new User<>();
        this.shoe = new Shoe(6);
        this.user.setView(view);
        this.dealer.setView(view);

        this.view.setContext(this.user, this.dealer, this.shoe);
        this.user.setView(view);
        this.dealer.setView(view);
        this.user.setShoe(this.shoe);
        this.dealer.setShoe(this.shoe);
    }

    public Result getResult() {
        return this.result;
    }

    /**
     * Orchestrates the game by calling User's, Dealer's and shoe methods, checks for blackjack.
     */
    public void playRound() {
        this.shoe.shuffle();

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

    /**
     * Calls view methods to display result of the game appropriately.
     */
    public void declareResult() {
        switch (this.result) {
            case DRAW:
                this.view.displayDraw();
                break;
            case DEALER_VICTORY:
                this.view.displayDealerVictory();
                break;
            case USER_VICTORY:
                this.view.displayUserVictory();
                break;
            case USER_BLACK_JACK:
                this.view.displayUserBlackJack();
                break;
            case DEALER_BLACK_JACK:
                this.view.displayDealerBlackJack();
                break;
            default:
                break;
        }
    }
}