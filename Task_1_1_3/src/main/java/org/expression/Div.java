package org.expression;

import org.exception.ZeroDenominatorInExpression;

/**
 * Objects of this class represent a fraction of two expressions.
 */
public class Div extends Operation {
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * d(u / v) / dx = (v * (du / dx) - u * (dv / dx)) / (v * v).
     *
     * @param variable with respect to which the expression is differentiated
     *
     * @return Derivative expression.
     */
    @Override
    public Expression derivative(String variable) {
        return new Div(
                    new Sub(new Mul(this.getLeft().derivative(variable), this.getRight()),
                            new Mul(this.getLeft(), this.getRight().derivative(variable))),
                    new Mul(this.getRight(), this.getRight()));
    }

    /**
     * The result of evaluation of a left expression divided by the result of evaluation of a
     * right expression.
     *
     * @param assignment Must contain values for all variables inside the expression.
     *
     * @return Value of the expression.
     */
    @Override
    public double eval(Assignment assignment) {
        double denominator = this.getRight().eval(assignment);
        if (denominator == 0) {
            throw new ZeroDenominatorInExpression("Division evaluation failed: denominator "
                    + "evaluated to zero.");
        }
        return this.getLeft().eval(assignment) / denominator;
    }
}
