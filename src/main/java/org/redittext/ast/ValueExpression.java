package org.redittext.ast;

import org.redittext.lib.NumberValue;
import org.redittext.lib.Value;

public class ValueExpression implements Expression {
    private final Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.asString();
    }

    @Override
    public Value eval() {
        return value;
    }
}
