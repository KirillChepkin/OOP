package org.example;

public enum Value {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    ELEVEN(11);

    public int code;

    Value(int code) {
        this.code = code;
    }
}