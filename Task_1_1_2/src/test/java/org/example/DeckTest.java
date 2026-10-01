package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.Card;
import org.example.CardCode;
import org.example.SuitCode;

public class DeckTest {
    @Test
    void testDeckCreation() {
        Deck deck = new Deck();
        assertEquals(new Card(SuitCode.SPADES, CardCode.TWO), deck.cards.get(0));
        assertEquals(new Card(SuitCode.HEARTS, CardCode.FOUR), deck.cards.get(15));
        assertEquals(new Card(SuitCode.HEARTS, CardCode.QUEEN), deck.cards.get(23));
        assertEquals(new Card(SuitCode.DIAMONDS, CardCode.NINE), deck.cards.get(33));
        assertEquals(new Card(SuitCode.CLUBS, CardCode.FOUR), deck.cards.get(41));
        assertEquals(new Card(SuitCode.CLUBS, CardCode.ACE), deck.cards.get(51));
    }

    @Test
    void testDeckShuffle() {
        Deck deck = new Deck();
        int len = deck.cards.size();
        assertEquals(52, len);
        deck.shuffle();
        assertEquals(len - 1, deck.cards.size());
    }
}
