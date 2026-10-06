package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardTest {
    @Test
    void numericCardsHaveTheirNumericValue() {
        assertEquals(2, new Card(SuitCode.SPADES, CardCode.TWO).getValue());
        assertEquals(10, new Card(SuitCode.HEARTS, CardCode.TEN).getValue());
    }

    @Test
    void faceCardsHaveValueTen() {
        assertEquals(10, new Card(SuitCode.SPADES, CardCode.JACK).getValue());
        assertEquals(10, new Card(SuitCode.HEARTS, CardCode.QUEEN).getValue());
        assertEquals(10, new Card(SuitCode.DIAMONDS, CardCode.KING).getValue());
    }

    @Test
    void aceHasValueEleven() {
        assertEquals(11, new Card(SuitCode.CLUBS, CardCode.ACE).getValue());
    }

    @Test
    void cardIsHiddenByDefaultAndCanBeRevealed() {
        Card card = new Card(SuitCode.SPADES, CardCode.ACE);

        assertFalse(card.isRevealed());
        card.setRevealed(true);
        assertTrue(card.isRevealed());
    }

    @Test
    void gettersReturnConstructorValues() {
        Card card = new Card(SuitCode.DIAMONDS, CardCode.QUEEN);

        assertEquals(SuitCode.DIAMONDS, card.getSuit());
        assertEquals(CardCode.QUEEN, card.getCard());
    }

    @Test
    void equalCardsHaveEqualHashCodes() {
        Card first = new Card(SuitCode.CLUBS, CardCode.TEN);
        Card second = new Card(SuitCode.CLUBS, CardCode.TEN);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void revealedStateParticipatesInEquality() {
        Card first = new Card(SuitCode.CLUBS, CardCode.TEN);
        Card second = new Card(SuitCode.CLUBS, CardCode.TEN);

        second.setRevealed(true);

        assertNotEquals(first, second);
    }

    @Test
    void differentSuitOrCardMakesCardsUnequal() {
        Card card = new Card(SuitCode.CLUBS, CardCode.TEN);

        assertNotEquals(card, new Card(SuitCode.HEARTS, CardCode.TEN));
        assertNotEquals(card, new Card(SuitCode.CLUBS, CardCode.JACK));
        assertNotEquals(card, null);
        assertNotEquals(card, "card");
    }

    @Test
    void toStringContainsCardInformation() {
        Card card = new Card(SuitCode.HEARTS, CardCode.ACE);

        assertEquals("Card(ACE of HEARTS, Value: 11, Revealed: false)", card.toString());
    }
}
