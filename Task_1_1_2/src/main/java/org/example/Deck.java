package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Deck {
    /**
     * A list to store all the cards.
     */
    public List<Card> cards = new ArrayList<>();

    /**
     * Creates cards for the deck.
     */
    public Deck() {
        for (SuitCode suit: SuitCode.values()) {
            for (CardCode card: CardCode.values()) {
                cards.add(new Card(suit, card));
            }
        }
    }

    /** shuffles all the cards in the deck */
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    /** fetches one card from the deck */
    public Card drawCard() {
        return this.cards.removeLast();
    }
}
