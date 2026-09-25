package org.example;

public enum Number {
    TWO("Двойка", 2),
    THREE("Тройка", 3),
    FOUR("Четверка", 4),
    FIVE("Пятерка", 5),
    SIX("Шестерка", 6),
    SEVEN("Семерка", 7),
    EIGHT("Восьмерка", 8),
    NINE("Девятка", 9),
    TEN("Десятка", 10);

    public final String number;
    public final int value;

    Number(String name, int value) {
        this.number = name;
        this.value = value;
    }
}
