package org.expresssion;

public abstract class Expression {
    /**
     * Returns An expression which is equal to the derivative of the given expression.
     *
     * @param variable with respect to which the expression is differentiated
     *
     * @return An expression equal to the derivative.
     */
    public abstract Expression derivative(String variable);

    /**
     *
     * @param assignment Must contain values for all variables inside the expression.
     *
     * @return Value of the expression.
     */
    public abstract double eval(Assignment assignment);
}