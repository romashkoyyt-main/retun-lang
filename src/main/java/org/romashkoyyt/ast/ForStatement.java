package org.romashkoyyt.ast;

import org.romashkoyyt.lib.Variables;

public class ForStatement implements Statement {

    private final String name;
    private final Expression value1;
    private final Expression value2;
    private final Statement incr;
    private final Statement statement;

    public ForStatement(String name, Expression value1, Expression value2, Statement incr, Statement statement) {
        this.name = name;
        this.value1 = value1;
        this.value2 = value2;
        this.incr = incr;
        this.statement = statement;
    }

    @Override
    public void execute() {
        Statement assign = new AssignStatement(name, value1);

        if (incr == null) {
            Statement plus = new PlusPlusStatement(name);
            for (assign.execute(); Variables.get(name).asNumber() < value2.eval().asNumber() + 1; plus.execute()) {
                try {
                    statement.execute();
                } catch (BreakStatement bs) {
                    break;
                } catch (ContinueStatement _) {}
            }
        } else {
            if (value1.eval().asNumber() < value2.eval().asNumber()) {
                for (assign.execute(); Variables.get(name).asNumber() < value2.eval().asNumber() + 1; incr.execute()) {
                    try {
                        statement.execute();
                    } catch (BreakStatement bs) {
                        break;
                    } catch (ContinueStatement _) {}
                }
            } else {
                for (assign.execute(); Variables.get(name).asNumber() > value2.eval().asNumber() - 1; incr.execute()) {
                    try {
                        statement.execute();
                    } catch (BreakStatement bs) {
                        break;
                    } catch (ContinueStatement _) {}
                }
            }
        }
    }
}
