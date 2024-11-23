package org.romashkoyyt.ast;

import org.romashkoyyt.lib.NumberValue;
import org.romashkoyyt.lib.Value;

public class UnaryExpression implements Expression {
    private final Expression expr;
    private final char operation;

    public UnaryExpression(Expression expr, char operation) {
        this.expr = expr;
        this.operation = operation;
    }

    @Override
    public Value eval() {
        switch (operation) {
            case '-':
                return new NumberValue(-expr.eval().asNumber());
            case '+':
            default:
                return new NumberValue(expr.eval().asNumber());
        }
    }

    @Override
    public String toString() {
        return String.format("%c%s", operation, expr);
    }
}
