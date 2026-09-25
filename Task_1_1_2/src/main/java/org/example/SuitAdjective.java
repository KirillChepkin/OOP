package org.example;

public enum SuitAdjective {
    SPADES("Пиковый"),
    HEARTS("Червовый"),
    DIAMONDS("Бубновый"),
    CLUBS("Трефовый"),

    SPADES_FEMALE("Пиковая"),
    HEARTS_FEMALE("Червовая"),
    DIAMONDS_FEMALE("Бубновая"),
    CLUBS_FEMALE("Трефовая");

    public final String suit;

    SuitAdjective(String name) {
        this.suit = name;
    }
}