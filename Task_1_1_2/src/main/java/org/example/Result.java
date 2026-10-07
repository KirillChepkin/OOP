package org.example;

/**
 * Enum with all the possible results of the game round.
 */
public enum Result {
    USER_BLACK_JACK(2),
    USER_VICTORY(1),
    DEALER_VICTORY(-1),
    DRAW(0),
    DEALER_BLACK_JACK(-2);

    private final int result;

    Result(int code) {
        this.result = code;
    }
}
