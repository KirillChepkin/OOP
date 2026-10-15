package org.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.Map;

class SubTest {

    @Test
    void evalSubtractsRightOperandFromLeft() {
        Sub expression = new Sub(new Number(8.0), new Number(3.0));

        assertEquals(5.0,
                expression.eval(new Assignment(Map.of())), 1e-9);
    }

    @Test
    void derivativeOfNumberMinusConstantIsOne() {
        Sub expression = new Sub(
                new Variable("x"), new Number(5.0));

        assertEquals(1.0,
                expression.derivative("x").eval(
                        new Assignment(Map.of("x", 2.0))), 1e-9);
    }

    @Test
    void derivativeOfConstantMinusNumberIsMinusOne() {
        Sub expression = new Sub(
                new Number(5.0), new Variable("x"));

        assertEquals(-1.0,
                expression.derivative("x").eval(
                        new Assignment(Map.of("x", 2.0))), 1e-9);
    }

    @Test
    void toStringShowsBothOperands() {
        Sub expression = new Sub(new Number(8.0), new Number(3.0));

        assertEquals("Sub(Number(8.0), Number(3.0))",
                expression.toString());
    }
}
