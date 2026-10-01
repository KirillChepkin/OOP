package org.view;

/**
 * Contains string representations for all possible card suits
 */
public enum SuitRep {
    SPADES(1, "Пики"),
    HEARTS(2, "Червы"),
    DIAMONDS(3, "Бубны"),
    CLUBS(4, "Трефы");

    public final int code;
    public final String rep;

    SuitRep(int code, String rep) {
        this.code = code;
        this.rep = rep;
    }
}
