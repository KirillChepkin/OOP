package org.example;

import java.util.ArrayList;
import java.util.List;
import org.view.View;

/**
 * Implements common methods for User and Dealer.
 */
public abstract class Player<T extends View> {
    protected static Deck deck;

    public static void setDeck(Deck deckParam) {
        deck = deckParam;
    }

    public List<Card> cards = new ArrayList<>();
    /**
     * Value does not include Aces.
     */
    protected int value = 0;
    private int aces = 0;

    protected T view;

    abstract void start();

    abstract void play();

    /**
     * Provides an object belonging to a subclass of View to be used for IO.
     *
     * @param view View object that should be used for IO.
     */
    public void setView(T view) {
        this.view = view;
    }

    /**
     * Adds a card to player's disposal.
     */
    protected void takeCard(Card card, boolean reveal) {
        cards.add(card);
        if (reveal) {
            card.revealed = true;
        }
        if (card.card == CardCode.ACE) {
            this.aces++;
            return;
        }
        this.value += card.getValue();
    }

    /**
     * Getter method for this.aces parameter.
     *
     * @return number of Aces at Player's disposal.
     */
    protected int countAces() {
        return this.aces;
    }

    /**
     * Returns player's total value including Aces.
     * */
    public int getValue() {
        int value = this.value + this.aces * 11;
        if (value > 21) {
            return value - this.aces * 10;
        }
        return value;
    }
}