package org.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExceptionTest {

    @Test
    void expectedDoubleValueIncludesToken() {
        ExpectedDoubleValue exception =
                new ExpectedDoubleValue("Expected a number, found: ", "abc");

        assertEquals("Expected a number, found: \"abc\"",
                exception.getMessage());
    }

    @Test
    void expectedEqualsSignIncludesToken() {
        ExpectedEqualsSign exception =
                new ExpectedEqualsSign("Expected equals sign, found: ", "+");

        assertEquals("Expected equals sign, found: \"+\"",
                exception.getMessage());
    }

    @Test
    void expectedVariableNameIncludesToken() {
        ExpectedVariableName exception =
                new ExpectedVariableName("Expected variable, found: ", "123");

        assertEquals("Expected variable, found: \"123\"",
                exception.getMessage());
    }

    @Test
    void invalidArithmeticExpressionPreservesMessage() {
        InvalidArithmeticExpression exception =
                new InvalidArithmeticExpression("Invalid expression");

        assertEquals("Invalid expression", exception.getMessage());
    }

    @Test
    void invalidPieceOfExpressionPreservesMessage() {
        InvalidPieceOfExpression exception =
                new InvalidPieceOfExpression("Invalid token");

        assertEquals("Invalid token", exception.getMessage());
    }

    @Test
    void unassignedVariablePreservesMessage() {
        UnassignedVariable exception =
                new UnassignedVariable("Unknown variable");

        assertEquals("Unknown variable", exception.getMessage());
    }

    @Test
    void zeroDenominatorPreservesMessage() {
        ZeroDenominatorInExpression exception =
                new ZeroDenominatorInExpression("Division by zero");

        assertEquals("Division by zero", exception.getMessage());
    }
}
