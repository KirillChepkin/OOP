package org.expression;

public class Add extends Operation {
    public Add(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Derivative of sum is a sum of derivatives.
     *
     * @param variable with respect to which the expression is differentiated
     *
     * @return Derivative expression.
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(this.getLeft().derivative(variable), this.getRight().derivative(variable));
    }

    /**
     * The result of evaluation of a left expression multiplied by the result of evaluation of a
     * right expression.
     *
     * @param assignment Must contain values for all variables inside the expression.
     *
     * @return Derivative expression.
     */
    @Override
    public double eval(Assignment assignment) {
        return this.getLeft().eval(assignment) + this.getRight().eval(assignment);
    }
}