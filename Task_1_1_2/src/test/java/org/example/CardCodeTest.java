package org.example;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CardCodeTest {
    @Test
    void containsAllThirteenCardCodesInOrder() {
        assertEquals(13, CardCode.values().length);
        assertArrayEquals(
                new CardCode[] {
                    CardCode.TWO, CardCode.THREE, CardCode.FOUR, CardCode.FIVE,
                    CardCode.SIX, CardCode.SEVEN, CardCode.EIGHT, CardCode.NINE,
                    CardCode.TEN, CardCode.JACK, CardCode.QUEEN, CardCode.KING,
                    CardCode.ACE
                },
                CardCode.values());
    }

    @Test
    void codesMatchExpectedValues() {
        assertEquals(2, CardCode.TWO.code);
        assertEquals(10, CardCode.TEN.code);
        assertEquals(11, CardCode.JACK.code);
        assertEquals(12, CardCode.QUEEN.code);
        assertEquals(13, CardCode.KING.code);
        assertEquals(14, CardCode.ACE.code);
    }
}
