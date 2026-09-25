package org.example;

public enum Person {
    JACK("Валет", 10, false),
    KING("Король", 10, false),
    ACE("Туз", 11, true),
    QUEEN("Дама", 10, false);

    public final String person;
    public final int value;
    public final boolean isAce;

    Person(String name, int value, boolean isAce) {
        this.person = name;
        this.value = value;
        this.isAce = isAce;
    }
}
