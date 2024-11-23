package org.redittext.ast;

import org.redittext.lib.NumberValue;
import org.redittext.lib.Value;

public class ToNumberExpression implements Expression {

    private final Expression value;

    public ToNumberExpression(Expression value) {
        this.value = value;
    }

    @Override
    public Value eval() {
        return new NumberValue(value.eval().asNumber());
    }
}
