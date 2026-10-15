package org.exception;

/**
 * Exception thrown when an arithmetic expression is invalid.
 */
public class InvalidArithmeticExpression extends RuntimeException {
    public InvalidArithmeticExpression(String message) {
        super(message);
    }
}
