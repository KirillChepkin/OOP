package org.example;

public enum Suit {
    SPADES("Пики"),
    HEARTS("Червы"),
    DIAMONDS("Бубны"),
    CLUBS("Трефы");

    public final String suit;

    Suit(String name) {
        this.suit = name;
    }
}
