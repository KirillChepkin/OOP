package org.exception;

public class UnassignedVariable extends RuntimeException {
    public UnassignedVariable(String message) {
        super(message);
    }
}
