package org.example;

import org.view.View;

/**
 * Class that represents dealer throughout the game.
 *
 * @param <T> type of the view object used for IO.
 */
public class Dealer<T extends View> extends Player<T> {
    /**
     * At the beginning Dealer reveals their hidden card. Then they take card after card until the
     * total sum is >= 17. Method must be called only if no player had a blackjack combination.
     */
    public void play() {
        this.cards.get(1).setRevealed(true);
        Card card;
        this.view.displayDealerReveal();
        while (this.getValue() < 17) {
            card = deck.drawCard();
            this.takeCard(card, true);
            this.view.displayDealerDraw();
        }
    }

    /**
     * The only distinction from User's implementation is that this method does not reveal second
     * card.
     */
    public void start() {
        this.takeCard(deck.drawCard(), true);
        this.takeCard(deck.drawCard(), false);
    }
}