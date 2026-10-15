package org.exception;


/**
 * Exception thrown when an equals sign is expected during assignment parsing.
*/
public class ExpectedEqualsSign extends RuntimeException {
    /**
     * Exception thrown when an equals sign is expected during assignment parsing.
     *
     * @param message the error message describing the problem
     *
     * @param token the token that caused the exception
     */
    public ExpectedEqualsSign(String message, String token) {
        super(message + "\"" + token + "\"");
    }
}
