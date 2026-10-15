package org.exception;

/**
 *vException thrown when an invalid piece of an arithmetic expression is encountered.
 */
public class InvalidPieceOfExpression extends RuntimeException {
    public InvalidPieceOfExpression(String message) {
        super(message);
    }
}