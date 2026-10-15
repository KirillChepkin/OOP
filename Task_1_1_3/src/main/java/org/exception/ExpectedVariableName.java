package org.exception;

/**
 * Exception thrown when a variable name is expected during expression parsing.
 */
public class ExpectedVariableName extends RuntimeException {
    /**
     * Prints the error message with a token.
     *
     * @param message error message.
     *
     * @param token token that appeared instead of expected variable name.
     */
    public ExpectedVariableName(String message, String token) {
        super(message + "\"" + token + "\"");
    }
}
