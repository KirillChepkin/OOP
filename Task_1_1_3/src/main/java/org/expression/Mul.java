package org.expression;

public class Mul extends Operation {
    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    /**
     * d(u * v) / dx = (du / dx) * v + (dv / dx) * u.
     *
     * @param variable with respect to which the expression is differentiated
     *
     * @return Derivative expression.
     */
    @Override
    public Expression derivative(String variable) {
        return new Add(
                new Mul(this.getLeft().derivative(variable), this.getRight()),
                new Mul(this.getRight().derivative(variable), this.getLeft()));
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
        return this.getLeft().eval(assignment) * this.getRight().eval(assignment);
    }
}
