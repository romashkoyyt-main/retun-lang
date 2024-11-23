package org.redittext.ast;

import org.redittext.lib.Value;
import org.redittext.lib.Variables;

public class VariableExpression implements Expression {

    private final String key;

    public VariableExpression(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }

    @Override
    public Value eval() {
        if (Variables.isExists(key)) {
            return Variables.get(key);
        } else {
            throw new RuntimeException(key + " is undefined");
        }
    }
}
