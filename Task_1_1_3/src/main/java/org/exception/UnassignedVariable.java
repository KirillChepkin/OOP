package org.exception;

/**
 * Exception thrown when an unassigned variable is encountered during expression evaluation.
 */
public class UnassignedVariable extends RuntimeException {
    public UnassignedVariable(String message) {
        super(message);
    }
}
