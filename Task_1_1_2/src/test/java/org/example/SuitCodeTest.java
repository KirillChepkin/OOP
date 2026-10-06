package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SuitCodeTest {
    @Test
    void containsFourSuitsInExpectedOrder() {
        assertArrayEquals(
                new SuitCode[] {
                    SuitCode.SPADES, SuitCode.HEARTS,
                    SuitCode.DIAMONDS, SuitCode.CLUBS
                },
                SuitCode.values());
    }

    @Test
    void suitsHaveExpectedCodes() {
        assertEquals(1, SuitCode.SPADES.code);
        assertEquals(2, SuitCode.HEARTS.code);
        assertEquals(3, SuitCode.DIAMONDS.code);
        assertEquals(4, SuitCode.CLUBS.code);
    }
}
