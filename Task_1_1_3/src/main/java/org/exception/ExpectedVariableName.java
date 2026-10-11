package org.exception;

public class ExpectedVariableName extends RuntimeException {
    public ExpectedVariableName(String message, String token) {
        super(message + "\"" + token + "\"");
    }
}
