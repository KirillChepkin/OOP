package org.expresssion;

import org.exception.UnassignedVariable;

public class Variable extends Expression {
    private final String name;

    public String getName() {
        return this.name;
    }

    public Variable(String name) {
        this.name = name;
    }

    /**
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
     * @param assignment map from variables to their values.
     *
     * @return Value of the expression.
     */
    @Override
    public double eval(Assignment assignment) throws UnassignedVariable {
        return assignment.getValue(this.name);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + this.name + ")";
    }
}