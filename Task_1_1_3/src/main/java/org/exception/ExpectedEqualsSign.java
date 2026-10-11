package org.exception;

public class ExpectedEqualsSign extends RuntimeException {
    public ExpectedEqualsSign(String message, String token) {
        super(message + "\"" + token + "\"");
    }
}
