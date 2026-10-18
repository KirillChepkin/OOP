package org.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.exception.ExpectedDoubleValue;
import org.exception.ExpectedEqualsSign;
import org.exception.ExpectedVariableName;
import org.exception.InvalidArithmeticExpression;
import org.expression.Add;
import org.expression.Assignment;
import org.expression.Div;
import org.expression.Expression;
import org.expression.Mul;
import org.expression.Number;
import org.expression.Sub;
import org.expression.Variable;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void parseExpressionTokensSeparatesNumbersVariablesAndOperators() {
        List<String> tokens =
                Parser.parseExpressionTokens("x + 12.5 * y");

        assertEquals(
                List.of("x", "+", "12.5", "*", "y"),
                tokens
        );
    }

    @Test
    void parsesNumber() {
        Parser parser = new Parser();

        Expression expression = parser.getExpressionFromString("42.5");

        assertInstanceOf(Number.class, expression);
        assertEquals(42.5,
                expression.eval(new Assignment(Map.of())), 1e-9);
    }

    @Test
    void parsesVariable() {
        Parser parser = new Parser();

        Expression expression = parser.getExpressionFromString("x");

        assertInstanceOf(Variable.class, expression);
        assertEquals(10.0,
                expression.eval(new Assignment(Map.of("x", 10.0))),
                1e-9);
    }

    @Test
    void parsesAddition() {
        Parser parser = new Parser();

        Expression expression = parser.getExpressionFromString("2 + 3");

        assertInstanceOf(Add.class, expression);
        assertEquals(5.0,
                expression.eval(new Assignment(Map.of())), 1e-9);
    }

    @Test
    void parsesSubtraction() {
        Parser parser = new Parser();

        Expression expression = parser.getExpressionFromString("8 - 3");

        assertInstanceOf(Sub.class, expression);
        assertEquals(5.0,
                expression.eval(new Assignment(Map.of())), 1e-9);
    }

    @Test
    void parsesMultiplication() {
        Parser parser = new Parser();

        Expression expression = parser.getExpressionFromString("4 * 3");

        assertInstanceOf(Mul.class, expression);
        assertEquals(12.0,
                expression.eval(new Assignment(Map.of())), 1e-9);
    }

    @Test
    void parsesDivision() {
        Parser parser = new Parser();

        Expression expression = parser.getExpressionFromString("12 / 3");

        assertInstanceOf(Div.class, expression);
        assertEquals(4.0,
                expression.eval(new Assignment(Map.of())), 1e-9);
    }

    @Test
    void multiplicationHasHigherPrecedenceThanAddition() {
        Parser parser = new Parser();

        Expression expression =
                parser.getExpressionFromString("2 + 3 * 4");

        assertEquals(14.0,
                expression.eval(new Assignment(Map.of())), 1e-9);
    }

    @Test
    void parenthesesOverridePrecedence() {
        Parser parser = new Parser();

        Expression expression =
                parser.getExpressionFromString("(2 + 3) * 4");

        assertEquals(20.0,
                expression.eval(new Assignment(Map.of())), 1e-9);
    }

    @Test
    void parsesNestedParentheses() {
        Parser parser = new Parser();

        Expression expression =
                parser.getExpressionFromString("((2 + 3) * (4 - 1))");

        assertEquals(15.0,
                expression.eval(new Assignment(Map.of())), 1e-9);
    }

    @Test
    void parsesExpressionWithVariables() {
        Parser parser = new Parser();

        Expression expression =
                parser.getExpressionFromString("x * x + y");

        Assignment assignment =
                new Assignment(Map.of("x", 3.0, "y", 4.0));

        assertEquals(13.0, expression.eval(assignment), 1e-9);
    }

    @Test
    void parsedExpressionCanBeDifferentiated() {
        Parser parser = new Parser();

        Expression expression =
                parser.getExpressionFromString("x * x + 3 * x");

        Expression derivative = expression.derivative("x");

        assertEquals(11.0,
                derivative.eval(new Assignment(Map.of("x", 4.0))),
                1e-9);
    }

    @Test
    void storesTokensAfterParsing() {
        Parser parser = new Parser();

        parser.getExpressionFromString("x + 2");

        assertEquals(List.of("x", "+", "2"), parser.getTokens());
    }

    @Test
    void operatorAndOperandStacksAreEmptyAfterSuccessfulParsing() {
        Parser parser = new Parser();

        parser.getExpressionFromString("2 + 3 * 4");

        assertTrue(parser.getOperatorStack().isEmpty());
        assertTrue(parser.getOperandStack().isEmpty());
    }

    @Test
    void rejectsExpressionContainingInvalidCharacters() {
        Parser parser = new Parser();

        assertThrows(InvalidArithmeticExpression.class,
                () -> parser.getExpressionFromString("2 + @ 3"));
    }

//    @Test
//    void rejectsExpressionWithMissingOperand() {
//        Parser parser = new Parser();
//
//        assertThrows(InvalidArithmeticExpression.class,
//                () -> parser.getExpressionFromString("2 +"));
//    }

    @Test
    void rejectsExpressionWithExtraOperand() {
        Parser parser = new Parser();

        assertThrows(InvalidArithmeticExpression.class,
                () -> parser.getExpressionFromString("2 3"));
    }

    @Test
    void parsesAssignmentWithMultipleVariables() {
        Assignment assignment =
                Parser.getAssignment("x = 2; y = 3.5; z1 = 10");

        assertEquals(2.0, assignment.getValue("x"), 1e-9);
        assertEquals(3.5, assignment.getValue("y"), 1e-9);
        assertEquals(10.0, assignment.getValue("z1"), 1e-9);
    }

    @Test
    void parsesIntegerAndDecimalAssignmentValues() {
        Assignment assignment =
                Parser.getAssignment("x = 10; y = 0.125");

        assertEquals(10.0, assignment.getValue("x"), 1e-9);
        assertEquals(0.125, assignment.getValue("y"), 1e-9);
    }

    @Test
    void parsesAssignmentWithWhitespace() {
        Assignment assignment =
                Parser.getAssignment("x = 5 ; y = 7");

        assertEquals(5.0, assignment.getValue("x"), 1e-9);
        assertEquals(7.0, assignment.getValue("y"), 1e-9);
    }

    @Test
    void assignmentWithoutVariableNameThrowsException() {
        assertThrows(ExpectedVariableName.class,
                () -> Parser.getAssignment("1 = 2"));
    }

    @Test
    void assignmentWithoutEqualsSignThrowsException() {
        assertThrows(ExpectedEqualsSign.class,
                () -> Parser.getAssignment("x 2"));
    }

    @Test
    void assignmentWithoutNumericValueThrowsException() {
        assertThrows(ExpectedDoubleValue.class,
                () -> Parser.getAssignment("x = y"));
    }
}