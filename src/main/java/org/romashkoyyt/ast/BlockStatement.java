package org.romashkoyyt.ast;

import java.util.ArrayList;

public class BlockStatement implements Statement {

    private final ArrayList<Statement> statements;

    public BlockStatement() {
        statements = new ArrayList<>();
    }

    public void add(Statement statement) {
        statements.add(statement);
    }

    @Override
    public void execute() {
        for (Statement statement : statements) {
            statement.execute();
        }
    }
}
