package org.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SuitRepTest {
    @Test
    void containsFourRepresentations() {
        assertEquals(4, SuitRep.values().length);
        assertEquals("Пики", SuitRep.SPADES.rep);
        assertEquals("Червы", SuitRep.HEARTS.rep);
        assertEquals("Бубны", SuitRep.DIAMONDS.rep);
        assertEquals("Трефы", SuitRep.CLUBS.rep);
    }
}
