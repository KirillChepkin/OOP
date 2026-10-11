package org.expresssion;

public class Number extends Expression {
    private final double number;

    public double getNumber() {
        return this.number;
    }

    public Number(double number) {
        this.number = number;
    }

    /**
     * The derivative of a constant number is always 0.
     */
    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    /**
     * @param assignment Must contain values for all variables inside the expression.
     *
     * @return Value of the expression.
     */
    @Override
    public double eval(Assignment assignment) {
        return this.number;
    }
}
