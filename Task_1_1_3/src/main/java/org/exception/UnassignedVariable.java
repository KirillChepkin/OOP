package org.exception;

/**
 * Exception thrown when an unassigned variable is encountered during expression evaluation.
 */
public class UnassignedVariable extends RuntimeException {
    /**
     * prints a message.
     *
     * @param message message to print.
     */
    public UnassignedVariable(String message) {
        super(message);
    }
}
