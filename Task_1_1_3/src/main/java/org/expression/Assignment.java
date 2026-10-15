package org.expression;

import java.util.Map;
import org.exception.UnassignedVariable;

/**
 * Class that maps string variable names to their double values.
 */
public class Assignment {
    private final Map<String, Double> variableToValue;

    /**
     * Objects of this class simply encapsulate a map under the hood.
     *
     * @param variableToValue map.
     */
    public Assignment(Map<String, Double> variableToValue) {
        this.variableToValue = variableToValue;
    }

    /**
     * Variable to value getter. Checks if a variable name is in the assignment.
     */
    public Double getValue(String name) {
        if (!this.variableToValue.containsKey(name)) {
            throw new UnassignedVariable("Cannot evaluate expression with unknown variable.");
        }
        return this.variableToValue.get(name);
    }
}