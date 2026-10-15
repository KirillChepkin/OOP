package org.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.exception.UnassignedVariable;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

class AssignmentTest {
    @Test
    void retrievesAssignedValue() {
        Assignment assignment = new Assignment(
                Map.of("x", 2.5, "y", -4.0));

        assertEquals(2.5, assignment.getValue("x"), 1e-9);
        assertEquals(-4.0, assignment.getValue("y"), 1e-9);
    }

    @Test
    void missingVariableThrowsException() {
        Assignment assignment = new Assignment(Map.of("x", 1.0));

        assertThrows(UnassignedVariable.class,
                () -> assignment.getValue("y"));
    }

    @Test
    void emptyAssignmentCanBeCreated() {
        Assignment assignment = new Assignment(Map.of());

        assertThrows(UnassignedVariable.class,
                () -> assignment.getValue("x"));
    }

    @Test
    void reflectsChangesToProvidedMap() {
        Map<String, Double> values = new HashMap<>();
        values.put("x", 1.0);

        Assignment assignment = new Assignment(values);
        values.put("x", 9.0);

        assertEquals(9.0, assignment.getValue("x"), 1e-9);
    }
}
