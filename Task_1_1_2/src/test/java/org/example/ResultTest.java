package org.example;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ResultTest {
    @Test
    void containsAllPossibleResults() {
        assertEquals(5, Result.values().length);
        assertArrayEquals(
                new Result[] {
                    Result.USER_BLACK_JACK,
                    Result.USER_VICTORY,
                    Result.DEALER_VICTORY,
                    Result.DRAW,
                    Result.DEALER_BLACK_JACK
                },
                Result.values());
    }
}
