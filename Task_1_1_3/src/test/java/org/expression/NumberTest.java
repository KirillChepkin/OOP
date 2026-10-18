package org.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

class NumberTest {

    @Test
    void constructorAndGetter() {
        Number number = new Number(5.5);

        assertEquals(5.5, number.getNumber(), 1e-9);
    }

    @Test
    void evalReturnsItsValue() {
        Number number = new Number(42.0);
        Assignment assignment = new Assignment(Map.of("x", 10.0));

        assertEquals(42.0, number.eval(assignment), 1e-9);
    }

    @Test
    void derivativeIsZero() {
        Number number = new Number(7.0);

        assertEquals(0.0, number.derivative("x").eval(
                new Assignment(Map.of())), 1e-9);
    }

    @Test
    void toStringContainsClassNameAndValue() {
        assertEquals("Number(3.5)", new Number(3.5).toString());
    }
}
