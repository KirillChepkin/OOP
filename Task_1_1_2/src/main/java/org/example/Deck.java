package org.example;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    public List<Card> cards = new ArrayList<>();

    /**
     * Constructor first creates all Number card objects iterating through suits and numbers.
     * Then it creates Jack, King and Ace cards (they have male adjectives) for all suits.
     * Finally, it creates all four Queen cards (they have female adjectives).
     */
    public Deck() {
        for (Suit suit : Suit.values()) {
            for (Number number : Number.values()) {
                this.cards.add(new Card(number.number + " " + suit.suit, number.value,
                        false));
            }
        }

        SuitAdjective[] suits = SuitAdjective.values();
        Person[] persons = Person.values();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                this.cards.add(new Card(suits[i].suit + " " + persons[j].person,
                        persons[j].value, persons[j].isAce));
            }
        }
        for (int i = 4; i < 8; i++) {
            this.cards.add(new Card(suits[i].suit + " " + Person.QUEEN.person,
                    Person.QUEEN.value, Person.QUEEN.isAce));
        }
    }

    public void shuffle() {

    }
}
