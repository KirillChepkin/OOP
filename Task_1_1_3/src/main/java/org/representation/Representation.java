package org.representation;

import org.expression.*;
import org.expression.Number;

public class Representation {
    public static String translate(Expression exp) {
        switch (exp) {
            case Add e -> System.out.println(translate(e.getLeft()) + " + "
                    + translate(e.getRight()));
            case Sub e -> System.out.println(translate(e.getLeft()) + " - "
                    + translate(e.getRight()));
            case Mul e -> System.out.println(translate(e.getLeft()) + " * "
                    + translate(e.getRight()));
            case Div e -> System.out.println(translate(e.getLeft()) + " + "
                    + translate(e.getRight()));
            case Variable e -> System.out.println(e.getName());
            case Number e -> System.out.println(e.getNumber());
            default:
                return null
        }
    }
}
