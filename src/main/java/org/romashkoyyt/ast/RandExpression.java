package org.romashkoyyt.ast;

import org.romashkoyyt.lib.NumberValue;
import org.romashkoyyt.lib.Value;
import java.util.Random;

public class RandExpression implements Expression {

    private final Expression expr1;
    private final Expression expr2;

    public RandExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public Value eval() {
        Random random = new Random();
        int num = random.nextInt((int)expr1.eval().asNumber(), (int)expr2.eval().asNumber() + 1);

        return new NumberValue(num);
    }
}
