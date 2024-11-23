package org.redittext.ast;

import org.redittext.lib.StringValue;
import org.redittext.lib.Value;

import java.util.ArrayList;

public class FormatExpression implements Expression {

    private final Expression text;
    private final ArrayList<Expression> values;

    public FormatExpression(Expression text, ArrayList<Expression> values) {
        this.text = text;
        this.values = values;
    }

    @Override
    public Value eval() {
        ArrayList<String> values2 = new ArrayList<>();

        for (Expression expr : values) {
            values2.add(expr.eval().asString());
        }

        String[] array = values2.toArray(new String[0]);

        String arg1 = text.eval().asString().replace("%f", "%s");
        arg1 = arg1.replace("%d", "%s");
        arg1 = arg1.replace("%b", "%s");
        arg1 = arg1.replace("%c", "%s");

        return new StringValue(String.format(arg1, (Object[]) array));
    }
}
