package org.expresssion;

import org.exception.UnassignedVariable;

import java.util.HashMap;
import java.util.Map;

public class Assignment {
    private final Map<String, Double> variableToValue;

    public Assignment(Map<String, Double> variableToValue) {
        this.variableToValue = variableToValue;
    }

    public Double getValue(String name) {
        if (!this.variableToValue.containsKey(name)) {
            throw new UnassignedVariable("Cannot evaluate expression with unknown variable.");
        }
        return this.variableToValue.get(name);
    }
}