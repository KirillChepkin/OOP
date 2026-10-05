package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the Deck class.
 */
public class DeckTest {
    @Test
    void testDeckCreation() {
        Shoe deck = new Shoe(1);
        assertEquals(new Card(SuitCode.SPADES, CardCode.TWO), deck.getCards().get(0));
        assertEquals(new Card(SuitCode.HEARTS, CardCode.FOUR), deck.getCards().get(15));
        assertEquals(new Card(SuitCode.HEARTS, CardCode.QUEEN), deck.getCards().get(23));
        assertEquals(new Card(SuitCode.DIAMONDS, CardCode.NINE), deck.getCards().get(33));
        assertEquals(new Card(SuitCode.CLUBS, CardCode.FOUR), deck.getCards().get(41));
        assertEquals(new Card(SuitCode.CLUBS, CardCode.ACE), deck.getCards().get(51));
    }

    @Test
    void testDeckShuffle() {
        Shoe deck = new Shoe(1);
        int len = deck.getCards().size();
        assertEquals(52, len);
        deck.shuffle();
        deck.drawCard();
        assertEquals(len - 1, deck.getCards().size());
    }
}
