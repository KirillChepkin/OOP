package org.example;

/**
 * This class represents a card in the game.
 * A card is hidden by default and can be revealed via the method.
 * A card has a default value according to its name.
 * But this value can be reset in case of an ace (this.value == 11).
 */
public class Card {
    public static String defaultName;
    public static int defaultValue;

    public String name;
    public int value = 0;
    public boolean isAce;
    private boolean revealed = false;

    public static void setDefault(String name, int value) {
        defaultName = name;
        defaultValue = value;
    }

    public Card(String name, int value, boolean isAce) {
        this.name = name;
        this.value = value;
        this.isAce = isAce;
    }

    public void reveal() {
        this.revealed = true;
    }

    /**
     * sets card's value to 1 if it is an ace and currently revealed.
     *
     */
    public void resetAceValue() {
        if (this.revealed && this.isAce) {
            this.value = 1;
        }
    }

    public String getName() {
        if (!this.revealed) {
            return defaultName;
        }
        return this.name;
    }

    public int getValue() {
        if (!this.revealed) {
            return defaultValue;
        }
        return this.value;
    }

    @Override
    public String toString() {
        return "Card(" + this.name + ")";
    }
}
