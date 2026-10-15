package org.expression;

import org.exception.UnassignedVariable;

/**
 * Objects of this class store variable names.
 */
public class Variable extends Expression {
    private final String name;

    /**
     * returns a name of the variable.
     *
     * @return string with a name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * class constructor.
     *
     * @param name name of the variable.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Derivative of a variable is always constant. 1 if a variable is the same as
     * differentiation variable, 0 otherwise.
     *
     * @param variable with respect to which the expression is differentiated
     *
     * @return derivative expression of the variable (either Number(1) or Number(0)).
     */
    @Override
    public Expression derivative(String variable) {
        if (this.name.equals(variable)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    /**
     * Fetches a value of its variable and returns it. The case when a variable is not in an
     * assignment is handled by the method call.
     *
     * @param assignment map from variables to their values.
     *
     * @return Value of the expression.
     */
    @Override
    public double eval(Assignment assignment) throws UnassignedVariable {
        return assignment.getValue(this.name);
    }

    /**
     * a variable is represented by "Variable(this.name)".
     *
     * @return string representation.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.name + ")";
    }
}