package org.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.exception.ExpectedDoubleValue;
import org.exception.ExpectedEqualsSign;
import org.exception.ExpectedVariableName;
import org.exception.InvalidArithmeticExpression;
import org.exception.InvalidPieceOfExpression;
import org.expression.Add;
import org.expression.Assignment;
import org.expression.Div;
import org.expression.Expression;
import org.expression.Mul;
import org.expression.Number;
import org.expression.Sub;
import org.expression.Variable;

/**
 * This class provides static and dynamic methods for parsing string expressions of two types:
 * 1) Assignments (e.g. x = 1; y = 2; z3 = 5.009)
 * 2) Arithmetic expressions
 */
public class Parser {
    List<String> tokens;

    public List<String> getTokens() {
        return this.tokens;
    }

    private List<String> operatorStack;

    public List<String> getOperatorStack() {
        return this.operatorStack;
    }

    private List<Expression> operandStack;

    public List<Expression> getOperandStack() {
        return operandStack;
    }

    private static List<String> parseAssignmentTokens(java.lang.String string) {
        Pattern pattern = Pattern.compile(
                "(-?[0-9]+\\.?[0-9]+)|=|(-?[0-9]+)|([a-zA-Z0-9]+)");
        Matcher matcher = pattern.matcher(string);
        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    public static Assignment getAssignment(String string) {
        List<String> tokens = parseAssignmentTokens(string);
        System.out.println(tokens);
        Map<String, Double> variableToValue = new HashMap<>();
        String name = null;
        double value = 0;
        int state = 0; // 0 - expecting variable, 1 - expecting "=", 2 - expecting double.
        for (String token: tokens) {
            if (state == 0 && representsVariable(token)) {
                name = token;
                state = 1;
                continue;
            } else if (state == 0){
                throw new ExpectedVariableName("Expected variable name starting with"
                        + " a letter in assigment string, found: ", token);
            }

            if (state == 1 && token.equals("=")) {
                state = 2;
                continue;
            } else if (state == 1) {
                throw new ExpectedEqualsSign(
                        "Expected equals sign after variable name"
                                + "in assignment string, found: ", token);
            }

            if (representsDouble(token)) {
                value = Double.parseDouble(token);
                state = 0;
                variableToValue.put(name, value);
            } else {
                throw new ExpectedDoubleValue("Expected a double value after"
                        + " equal sign in assignment string, found: ", token);
            }
        }
        return new Assignment(variableToValue);
    }

    public static List<String> parseExpressionTokens(String string) {
        Pattern pattern = Pattern.compile(
                "[0-9]*\\.?[0-9]+|[a-zA-Z][a-zA-Z0-9]*|[-+*/()]");
        Matcher matcher = pattern.matcher(string);
        List<String> tokens = new ArrayList<>();
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    public Expression getExpressionFromString(String string) {
        if (!string.matches("([a-zA-Z0-9+/*\\-(). \t\n\r])+")) {
            throw new InvalidArithmeticExpression("The expression contains incorrect symbols.");
        }
        this.tokens = parseExpressionTokens(string);
        this.operatorStack = new ArrayList<>();
        this.operandStack = new ArrayList<>();
        for (String token: tokens) {
            if (token.equals("(")) {
                this.operatorStack.add(token);
            } else if (representsDouble(token)) {
                this.operandStack.add(new Number(Double.parseDouble(token)));
            } else if (representsVariable(token)) {
                this.operandStack.add(new Variable(token));
            } else if (representsOperator(token)) {
                // expression like (a / b * c) will be parsed as (a / b) * c.
                this.processOperator(token);
            } else if (token.equals(")")) {
                this.clearStackDownToBracket();
            } else {
                throw new InvalidPieceOfExpression("Detected an invalid piece of expression: "
                        + token);
            }
        }
        clearStackDownToBracket();
        if (operatorStack.size() != 0 || operandStack.size() != 1) {
            throw new InvalidArithmeticExpression("Unable to process the expression");
        }
        return this.operandStack.remove(this.operandStack.size() - 1);
    }

    private void clearStackDownToBracket() {
        if (this.operatorStack.size() == 0) {
            return;
        }
        String operator = popPreviousOperator();
        Expression a, b;
        while (!operator.equals("(")
                && this.operandStack.size() >= 2) {
            b = this.operandStack.remove(this.operandStack.size() - 1);
            a = this.operandStack.remove(this.operandStack.size() - 1);
            this.operandStack.add(createOperation(a, b, operator));
            if (operatorStack.size() == 0) {
                break;
            }
            operator = popPreviousOperator();
        }
    }

    private void processOperator(String token) {
        if (this.operatorStack.size() == 0) {
            this.operatorStack.add(token);
            return;
        }
        String previous;
        Expression a, b;
        while (this.operatorStack.size() > 0
                && !operatorStack.get(operatorStack.size() - 1).equals("(")
                && getOperatorPriority(operatorStack.get(operatorStack.size() - 1))
                >= getOperatorPriority(token)
                && this.operandStack.size() >= 2) {
            previous = popPreviousOperator();
            b = this.operandStack.remove(this.operandStack.size() - 1);
            a = this.operandStack.remove(this.operandStack.size() - 1);
            this.operandStack.add(createOperation(a, b, previous));
        }
        this.operatorStack.add(token);
    }

    private static Expression createOperation(Expression a, Expression b, String operator) {
        switch (operator) {
            case "+":
                return new Add(a, b);
            case "-":
                return new Sub(a, b);
            case "*":
                return new Mul(a, b);
            case "/":
                return new Div(a, b);
            default:
                throw new InvalidPieceOfExpression("Invalid operator found while parsing the "
                        + "expression: " + operator);
        }
    }

    private String popPreviousOperator() {
        return this.operatorStack.remove(this.operatorStack.size() - 1);
    }

    private static int getOperatorPriority(String operator) {
        if (operator.equals("+") || operator.equals("-")) {
            return 1;
        } else {
            return 2;
        }
    }

    private static Boolean representsOperator(String string) {
        return string.matches("[+\\-*/]");
    }

    private static Boolean representsDouble(String string) {
        return string.matches("(-?[0-9]*(\\.)?[0-9]+)");
    }

    private static Boolean representsVariable(String string) {
        return string.matches("[a-zA-Z][a-zA-Z0-9]*");
    }

    private void logStacks() {
        System.out.println(this.operatorStack);
        System.out.println(this.operandStack);
    }
}