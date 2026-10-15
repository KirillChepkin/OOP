package org.expression;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.exception.UnassignedVariable;
import org.junit.jupiter.api.Test;

class VariableTest {
    @Test
    void constructorAndGetter() {
        Variable variable = new Variable("x");

        assertEquals("x", variable.getName());
    }

    @Test
    void evalReturnsAssignedValue() {
        Variable variable = new Variable("x");
        Assignment assignment = new Assignment(Map.of("x", 12.5));

        assertEquals(12.5, variable.eval(assignment), 1e-9);
    }

    @Test
    void derivativeWithRespectToSameVariableIsOne() {
        Variable variable = new Variable("x");

        assertEquals(1.0, variable.derivative("x").eval(
                new Assignment(Map.of())), 1e-9);
    }

    @Test
    void derivativeWithRespectToDifferentVariableIsZero() {
        Variable variable = new Variable("x");

        assertEquals(0.0, variable.derivative("y").eval(
                new Assignment(Map.of())), 1e-9);
    }

    @Test
    void evaluatingUnassignedVariableThrowsException() {
        Variable variable = new Variable("x");
        Assignment assignment = new Assignment(Map.of());

        assertThrows(UnassignedVariable.class,
                () -> variable.eval(assignment));
    }

    @Test
    void toStringContainsClassNameAndName() {
        assertEquals("Variable(x)", new Variable("x").toString());
    }
}
