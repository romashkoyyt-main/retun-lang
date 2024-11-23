package org.romashkoyyt.ast;

import org.romashkoyyt.lib.NumberValue;
import org.romashkoyyt.lib.Value;

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
