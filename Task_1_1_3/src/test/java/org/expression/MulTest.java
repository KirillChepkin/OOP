package org.expression;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MulTest {

    @Test
    void evalMultipliesOperands() {
        Mul expression = new Mul(new Number(4.0), new Number(3.0));

        assertEquals(12.0,
                expression.eval(new Assignment(Map.of())), 1e-9);
    }

    @Test
    void derivativeOfXTimesConstantIsConstant() {
        Mul expression = new Mul(
                new Variable("x"), new Number(4.0));

        assertEquals(4.0,
                expression.derivative("x").eval(
                        new Assignment(Map.of("x", 3.0))), 1e-9);
    }

    @Test
    void derivativeOfXTimesXIsTwoX() {
        Mul expression = new Mul(
                new Variable("x"), new Variable("x"));

        assertEquals(6.0,
                expression.derivative("x").eval(
                        new Assignment(Map.of("x", 3.0))), 1e-9);
    }

    @Test
    void derivativeWithRespectToUnrelatedVariableIsZero() {
        Mul expression = new Mul(
                new Variable("x"), new Variable("y"));

        assertEquals(0.0,
                expression.derivative("z").eval(
                        new Assignment(Map.of("x", 2.0, "y", 3.0))),
                1e-9);
    }

    @Test
    void toStringShowsBothOperands() {
        Mul expression = new Mul(new Number(4.0), new Number(3.0));

        assertEquals("Mul(Number(4.0), Number(3.0))",
                expression.toString());
    }
}
