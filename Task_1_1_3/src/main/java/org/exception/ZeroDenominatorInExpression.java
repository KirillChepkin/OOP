package org.exception;

/**
 * Exception thrown when an arithmetic expression contains a zero denominator.
 */
public class ZeroDenominatorInExpression extends RuntimeException {
    /**
     * Prints a message.
     *
     * @param message a message to print when thrown.
     */
    public ZeroDenominatorInExpression(String message) {
        super(message);
    }
}