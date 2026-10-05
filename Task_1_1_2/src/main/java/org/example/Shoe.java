package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Represents a Shoe which contains cards from several decks.
 */
public class Shoe {
    /**
     * A list to store all the cards.
     */
    private List<Card> cards = new ArrayList<>();

    public List<Card> getCards() {
        return this.cards;
    }

    /**
     * Fills the deck with cards.
     *
     * @param decks number of decks to put to the shoe.
     */
    public Shoe(int decks) {
        for (int i = 0; i < decks; i++) {
            for (SuitCode suit : SuitCode.values()) {
                for (CardCode card : CardCode.values()) {
                    cards.add(new Card(suit, card));
                }
            }
        }
    }

    /** shuffles all the cards in the deck. */
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    /** fetches one card from the deck. */
    public Card drawCard() {
        return this.cards.remove(this.cards.size() - 1);
    }
}
