package org.romashkoyyt.ast;

import org.romashkoyyt.lib.StringValue;
import org.romashkoyyt.lib.Value;

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
