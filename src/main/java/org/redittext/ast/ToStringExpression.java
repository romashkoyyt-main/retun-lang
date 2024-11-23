package org.redittext.ast;

import org.redittext.lib.StringValue;
import org.redittext.lib.Value;

public class ToStringExpression implements Expression {

    private final Expression value;

    public ToStringExpression(Expression value) {
        this.value = value;
    }

    @Override
    public Value eval() {
        return new StringValue(value.eval().asString());
    }
}
