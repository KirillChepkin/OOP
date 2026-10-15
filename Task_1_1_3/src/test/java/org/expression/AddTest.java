package org.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

class AddTest {

    @Test
    void evalAddsOperands() {
        Add expression = new Add(new Number(2.0), new Number(3.0));

        assertEquals(5.0,
                expression.eval(new Assignment(Map.of())), 1e-9);
    }

    @Test
    void derivativeOfNumberPlusConstantIsOne() {
        Add expression = new Add(
                new Variable("x"), new Number(5.0));

        assertEquals(1.0,
                expression.derivative("x").eval(
                        new Assignment(Map.of("x", 2.0))), 1e-9);
    }

    @Test
    void derivativeOfTwoConstantsIsZero() {
        Add expression = new Add(new Number(2.0), new Number(3.0));

        assertEquals(0.0,
                expression.derivative("x").eval(
                        new Assignment(Map.of())), 1e-9);
    }

    @Test
    void toStringShowsBothOperands() {
        Add expression = new Add(new Number(2.0), new Number(3.0));

        assertEquals("Add(Number(2.0), Number(3.0))",
                expression.toString());
    }
}
