package org.exception;

/**
 * Exception thrown when an arithmetic expression contains a zero denominator.
 */
public class ZeroDenominatorInExpression extends RuntimeException {
    public ZeroDenominatorInExpression(String message) {
        super(message);
    }
}