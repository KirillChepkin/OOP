package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testCardToString() {
        Card card = new Card(SuitCode.HEARTS, CardCode.ACE);
        assertEquals("Card(ACE of HEARTS, Value: 11, Revealed: false)", card.toString());
    }

    @Test
    public void testCardEquals() {
        Card card1 = new Card(SuitCode.CLUBS, CardCode.EIGHT);
        Card card2 = new Card(SuitCode.CLUBS, CardCode.EIGHT);
        Card card3 = new Card(SuitCode.HEARTS, CardCode.EIGHT);
        Card card4 = new Card(SuitCode.CLUBS, CardCode.FIVE);
        assertTrue(card1.equals(card2));
        assertFalse(card1.equals(card3));
        assertFalse(card1.equals(card3));
        card1.setRevealed(true);
        assertFalse(card1.equals(card2));
    }

    @Test
    public void testHashCode() {
        Card card1 = new Card(SuitCode.CLUBS, CardCode.EIGHT);
        Card card2 = new Card(SuitCode.CLUBS, CardCode.EIGHT);
        Card card3 = new Card(SuitCode.HEARTS, CardCode.EIGHT);
        Card card4 = new Card(SuitCode.CLUBS, CardCode.FIVE);
        assertTrue(card1.hashCode() == card2.hashCode());
        assertFalse(card1.hashCode() == card3.hashCode());
        assertFalse(card1.hashCode() == card4.hashCode());
        card1.setRevealed(true);
        assertFalse(card1.hashCode() == card2.hashCode());
    }
}
