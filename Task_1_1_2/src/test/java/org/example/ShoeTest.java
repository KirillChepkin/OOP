package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ShoeTest {
    @Test
    void constructorCreatesCorrectNumberOfCards() {
        assertEquals(52, new Shoe(1).getCards().size());
        assertEquals(104, new Shoe(2).getCards().size());
        assertEquals(312, new Shoe(6).getCards().size());
    }

    @Test
    void oneDeckContainsEverySuitAndCardCombination() {
        Shoe shoe = new Shoe(1);

        for (SuitCode suit : SuitCode.values()) {
            for (CardCode card : CardCode.values()) {
                assertTrue(
                        shoe.getCards().contains(new Card(suit, card)),
                        "Missing " + card + " of " + suit);
            }
        }
    }

    @Test
    void refillAddsCardsInsteadOfReplacingThem() {
        Shoe shoe = new Shoe(1);

        shoe.refill(1);

        assertEquals(52, shoe.getCards().size());
    }

    @Test
    void drawCardRemovesAndReturnsOneCard() {
        Shoe shoe = new Shoe(1);
        int oldSize = shoe.getCards().size();
        Card expected = shoe.getCards().get(oldSize - 1);

        Card actual = shoe.drawCard();

        assertEquals(expected, actual);
        assertEquals(oldSize - 1, shoe.getCards().size());
    }

    @Test
    void drawCardOnEmptyShoeThrows() {
        Shoe shoe = new Shoe(0);

        assertThrows(IndexOutOfBoundsException.class, shoe::drawCard);
    }
}
