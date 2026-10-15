package org.exception;

/**
 * Exception thrown when a numeric value is expected during expression parsing.
 * The provided token is appended to the exception message.
 */
public class ExpectedDoubleValue extends RuntimeException {
    public ExpectedDoubleValue(String message, String token) {
        super(message + "\"" + token + "\"");
    }
}
