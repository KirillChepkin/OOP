package org.exception;

public class ExpectedDoubleValue extends RuntimeException {
    public ExpectedDoubleValue(String message, String token) {
        super(message + "\"" + token + "\"");
    }
}
