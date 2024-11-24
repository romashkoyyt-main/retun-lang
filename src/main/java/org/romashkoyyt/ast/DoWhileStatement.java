package org.romashkoyyt.ast;

public class DoWhileStatement implements Statement {

    private final Expression condition;
    private final Statement statement;

    public DoWhileStatement(Expression condition, Statement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public void execute() {

        do {
            try {
                statement.execute();
            } catch (BreakStatement bs) {
                break;
            } catch (ContinueStatement _) {}
        }
        while (condition.eval().asBoolean());
    }
}
