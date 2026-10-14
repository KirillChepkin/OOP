package org.expression;

public abstract class Operation extends Expression {
    private final Expression left;

    public Expression getLeft() {
        return this.left;
    }

    private final Expression right;

    public Expression getRight() {
        return this.right;
    }

    public Operation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.getLeft() + ", " + this.getRight() +
                ")";
    }
}
