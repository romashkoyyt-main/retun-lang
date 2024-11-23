package org.redittext.ast;

import org.redittext.lib.Value;

public class PrintStatement implements Statement {

    private final Expression text;

    public PrintStatement(Expression text) {
        this.text = text;
    }

    @Override
    public void execute() {
        System.out.println(text.eval());
    }

    @Override
    public String toString() {
        return "print(" + text + ");";
    }
}
