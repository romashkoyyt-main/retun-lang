package org.redittext.ast;

public class IfStatement implements Statement {

    private final Expression expr1;
    private final Expression expr2;
    private final Statement statement;
    private final String operation;

    public IfStatement(Expression expr1, Expression expr2, Statement expr3, String operation) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.statement = expr3;
        this.operation = operation;
    }

    @Override
    public void execute() {
        if (operation.equals("==")) {
            if (expr1.eval().asString().equals(expr2.eval().asString())) {
                statement.execute();
            }
        } else if (operation.equals(">")) {
            if (expr1.eval().asNumber() > expr2.eval().asNumber()) {
                statement.execute();
            }
        } else if (operation.equals("<")) {
            if (expr1.eval().asNumber() < expr2.eval().asNumber()) {
                statement.execute();
            }
        } else if (operation.equals("<=")) {
            if (expr1.eval().asNumber() <= expr2.eval().asNumber()) {
                statement.execute();
            }
        } else if (operation.equals(">=")) {
            if (expr1.eval().asNumber() >= expr2.eval().asNumber()) {
                statement.execute();
            }
        } else if (operation.equals("!=")) {
            if (expr1.eval().asNumber() != expr2.eval().asNumber()) {
                statement.execute();
            }
        }
    }
}
