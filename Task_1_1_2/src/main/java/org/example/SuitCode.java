package org.example;

/**
 * Elements of this enum class represent all possible card suits.
 */
public enum SuitCode {
    SPADES(1),
    HEARTS(2),
    DIAMONDS(3),
    CLUBS(4);

    public final int code;

    SuitCode(int code) {
        this.code = code;
    }
}