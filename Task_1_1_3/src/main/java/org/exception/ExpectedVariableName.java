package org.exception;

/**
 * Exception thrown when a variable name is expected during expression parsing.
 */
public class ExpectedVariableName extends RuntimeException {
    public ExpectedVariableName(String message, String token) {
        super(message + "\"" + token + "\"");
    }
}
