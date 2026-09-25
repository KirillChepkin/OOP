package org.example;

/*
 * The algorithm is:
 * 1) create a deck
 * 2) shuffle the deck
 * 3) create each side (player and dealer).
 * 4) hand each one two cards
 * 5) reveal appropriate cards
 */
public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck.cards);
    }
}
