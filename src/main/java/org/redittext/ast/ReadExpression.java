package org.redittext.ast;

import org.redittext.lib.StringValue;
import org.redittext.lib.Value;
import java.util.Scanner;

public class ReadExpression implements Expression {

    private final Expression text;

    public ReadExpression(Expression text) {
        this.text = text;
    }

    public ReadExpression() {
        this.text = new ValueExpression(new StringValue(""));
    }

    @Override
    public Value eval() {
        Scanner scanner = new Scanner(System.in);

        System.out.print(text.eval().asString());
        String line = scanner.nextLine();

        return new StringValue(line);
    }
}
