package org.romashkoyyt.ast;

public class IfElseStatement implements Statement {

    private final Expression expr;
    private final Statement statement;
    private Statement statement2;

    public IfElseStatement(Expression expr, Statement statement, Statement statement2) {
        this.expr = expr;
        this.statement = statement;
        this.statement2 = statement2;
    }

    public IfElseStatement(Expression expr, Statement statement) {
        this.expr = expr;
        this.statement = statement;
    }

    @Override
    public void execute() {
        if (expr.eval().asNumber() == 1 && expr.eval().asString().equals("true")) {
            statement.execute();
        } else {
            if (statement2 != null) {
                statement2.execute();
            }
        }
    }
}
