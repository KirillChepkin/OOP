package org.exception;


/**
 * Exception thrown when an equals sign is expected during assignment parsing.
*/
public class ExpectedEqualsSign extends RuntimeException {
    public ExpectedEqualsSign(String message, String token) {
        super(message + "\"" + token + "\"");
    }
}
