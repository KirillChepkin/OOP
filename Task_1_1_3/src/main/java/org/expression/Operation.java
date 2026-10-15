package org.expression;

/**
 * This abstract class provides common attributes and methods that are used in all binary
 * operations implemented in the package.
 */
public abstract class Operation extends Expression {
    private final Expression left;

    /**
     * getter for a left operand.
     *
     * @return an operand.
     */
    public Expression getLeft() {
        return this.left;
    }

    private final Expression right;

    /**
     * getter for a right operand.
     *
     * @return an operand.
     */
    public Expression getRight() {
        return this.right;
    }

    /**
     * Common object constructor for operations.
     *
     * @param left left operand of type Expression.
     *
     * @param right right operand of type Expression.
     */
    public Operation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.getLeft() + ", " + this.getRight()
                + ")";
    }
}
