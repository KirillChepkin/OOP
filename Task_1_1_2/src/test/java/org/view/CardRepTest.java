package org.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CardRepTest {
    @Test
    void containsThirteenRepresentations() {
        assertEquals(13, CardRep.values().length);
        assertEquals(2, CardRep.TWO.code);
        assertEquals("Двойка", CardRep.TWO.rep);
        assertEquals(14, CardRep.ACE.code);
        assertEquals("Туз", CardRep.ACE.rep);
    }
}
