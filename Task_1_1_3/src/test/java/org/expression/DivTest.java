package org.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import org.exception.ZeroDenominatorInExpression;
import org.junit.jupiter.api.Test;

class DivTest {

    @Test
    void evalDividesLeftOperandByRight() {
        Div expression = new Div(new Number(12.0), new Number(3.0));

        assertEquals(4.0,
                expression.eval(new Assignment(Map.of())), 1e-9);
    }

    @Test
    void divisionByZeroThrowsException() {
        Div expression = new Div(new Number(12.0), new Number(0.0));

        assertThrows(ZeroDenominatorInExpression.class,
                () -> expression.eval(new Assignment(Map.of())));
    }

    @Test
    void derivativeOfNumberDividedByConstantIsReciprocal() {
        Div expression = new Div(
                new Variable("x"), new Number(2.0));

        assertEquals(0.5,
                expression.derivative("x").eval(
                        new Assignment(Map.of("x", 4.0))), 1e-9);
    }

    @Test
    void derivativeOfConstantDividedByNumberIsNegativeConstantOverNumberSquared() {
        Div expression = new Div(
                new Number(6.0), new Variable("x"));

        assertEquals(-1.5,
                expression.derivative("x").eval(
                        new Assignment(Map.of("x", 2.0))), 1e-9);
    }

    @Test
    void toStringShowsBothOperands() {
        Div expression = new Div(new Number(12.0), new Number(3.0));

        assertEquals("Div(Number(12.0), Number(3.0))",
                expression.toString());
    }
}
