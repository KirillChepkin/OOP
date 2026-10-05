package org.example;

import java.util.Objects;

/**
 * Represents a single card with some value.
 */
public class Card {
    private final SuitCode suit;
    private final CardCode card;
    private final int value;
    private boolean revealed = false;

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isRevealed() {
        return this.revealed;
    }

    public SuitCode getSuit() {
        return this.suit;
    }

    public CardCode getCard() {
        return this.card;
    }

    /**
     * Sets card value according to its suit and its name.
     *
     * @param suit of the card.
     *
     * @param card name of the card.
     */
    public Card(SuitCode suit, CardCode card) {
        this.suit = suit;
        this.card = card;
        if (this.card.code <= 10) {
            this.value = this.card.code;
        } else if (this.card == CardCode.ACE) {
            this.value = 11;
        } else {
            this.value = 10;
        }
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Card(" + this.card.toString() + " of " + this.suit.toString() + ", Value: "
                + this.value + ", Revealed: " + this.revealed + ")";
    }

    /**
     * Method for testing.
     *
     * @param obj   the reference object with which to compare.
     *
     * @return whether card, suit and revealed attributes are identical.
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Card card) && (card.card.equals(this.card))
                && (card.suit.equals(this.suit)) && (card.revealed == this.revealed);
    }

    /**
     * Method for testing.
     *
     * @return integer hash of card, suit and revealed attributes.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.card, this.suit, this.revealed);
    }
}