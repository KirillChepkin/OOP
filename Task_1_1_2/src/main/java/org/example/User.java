package org.example;

import org.view.View;

/**
 * Unlike Dealer, this class waits for user to make a decision when playing.
 *
 * @param <T> IO type.
 */
public class User<T extends View> extends Player<T> {
    /**
     * Asks for user input and, according to it, draws or stops drawing cards from a deck. Calls
     * IO methods to display drawn cards.
     */
    public void play() {
        Card card;
        while (this.getValue() < 21 && this.view.getUserDecision()) {
            card = deck.drawCard();
            this.takeCard(card, true);
            this.view.displayUserDraw();
        }
    }

    /**
     * Draws two initial cards from a deck and, unlike dealer's implementation, reveales both of
     * them.
     */
    public void start() {
        this.takeCard(deck.drawCard(), true);
        this.takeCard(deck.drawCard(), true);
    }
}