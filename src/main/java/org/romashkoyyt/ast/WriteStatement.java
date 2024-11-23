package org.romashkoyyt.ast;

public class WriteStatement implements Statement {

    private final Expression text;

    public WriteStatement(Expression text) {
        this.text = text;
    }

    @Override
    public void execute() {
        System.out.print(text.eval());
    }

    @Override
    public String toString() {
        return "write(" + text + ");";
    }
}
