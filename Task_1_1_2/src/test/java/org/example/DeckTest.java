package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {
    @Test
    void testDeckCreation() {
        Deck deck = new Deck();
        assertEquals("Двойка Пики", deck.cards.get(0).name);
        assertEquals("Бубновая Дама", deck.cards.get(50).name);
        assertEquals("Тройка Червы", deck.cards.get(10).name);
        assertEquals(5, deck.cards.get(3).value);
        assertEquals(3, deck.cards.get(10).value);
        assertEquals(10, deck.cards.get(50).value);
    }
}
