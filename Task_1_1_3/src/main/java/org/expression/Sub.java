package org.expression;

public class Sub extends Operation {
    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * Derivative of subtraction is a subtraction of derivatives
     *
     * @param variable with respect to which the expression is differentiated
     *
     * @return Derivative expression.
     */
    @Override
    public Expression derivative(String variable) {
        return new Sub(this.getLeft().derivative(variable), this.getRight().derivative(variable));
    }

    /**
     * The result of evaluation of a left expression multiplied by the result of evaluation of a
     * right expression.
     *
     * @param assignment Must contain values for all variables inside the expression.
     *
     * @return Value of the expression.
     */
    @Override
    public double eval(Assignment assignment) {
        return this.getLeft().eval(assignment) - this.getRight().eval(assignment);
    }
}
