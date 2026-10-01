package org.view;

/**
 * Contains string representations for all possible card names.
 */
public enum CardRep {
    TWO(2, "Двойка"),
    THREE(3, "Тройка"),
    FOUR(4, "Четверка"),
    FIVE(5, "Пятерка"),
    SIX(6, "Шестерка"),
    SEVEN(7, "Семерка"),
    EIGHT(8, "Восьмерка"),
    NINE(9, "Девятка"),
    TEN(10, "Десятка"),
    JACK(11, "Валет"),
    QUEEN(12, "Дама"),
    KING(13, "Король"),
    ACE(14, "Туз");

    public final int code;
    public final String rep;

    CardRep(int code, String rep) {
        this.code = code;
        this.rep = rep;
    }
}
