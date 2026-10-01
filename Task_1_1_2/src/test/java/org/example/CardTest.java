package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the Card class.
 */
public class CardTest {
    @Test
    public void testCardValue() {
        Card card = new Card(SuitCode.HEARTS, CardCode.ACE);
        assertEquals(11, card.getValue());
        card = new Card(SuitCode.CLUBS, CardCode.TEN);
        assertEquals(10, card.getValue());
        card = new Card(SuitCode.SPADES, CardCode.QUEEN);
        assertEquals(10, card.getValue());
        card = new Card(SuitCode.DIAMONDS, CardCode.SEVEN);
        assertEquals(7, card.getValue());
    }
}
