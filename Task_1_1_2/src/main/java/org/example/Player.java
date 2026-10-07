package org.example;

import java.util.ArrayList;
import java.util.List;
import org.view.View;

/**
 * Implements common methods for User and Dealer.
 */
public abstract class Player<T extends View> {
    private int victories = 0;

    public void addVictory() {
        this.victories++;
    }

    public int getVictories() {
        return this.victories;
    }

    protected Shoe deck;

    public void setShoe(Shoe deckParam) {
        this.deck = deckParam;
    }

    protected List<Card> cards = new ArrayList<>();

    public List<Card> getCards() {
        return this.cards;
    }

    /**
     * Value does not include Aces.
     */
    protected int value = 0;
    private int aces = 0;

    protected T view;

    public abstract void start();

    public abstract void play();

    /**
     * Prepares a player for new round.
     */
    public void resetPlayer() {
        this.cards = new ArrayList<>();
        this.value = 0;
        this.aces = 0;
    }

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
            card.setRevealed(true);
        }
        if (card.getCard() == CardCode.ACE) {
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
        int value = this.value + this.aces;
        if (value <= 11 && this.aces > 0) {
            value += 10;
        }
        return value;
    }
}