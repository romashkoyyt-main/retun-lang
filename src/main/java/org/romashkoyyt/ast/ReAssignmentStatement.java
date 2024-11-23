package org.romashkoyyt.ast;

import org.romashkoyyt.lib.Variables;

public class ReAssignmentStatement implements Statement {

    private final String key;
    private final Expression value;

    public ReAssignmentStatement(String key, Expression value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s = %s", key, value);
    }

    @Override
    public void execute() {
        if (Variables.isExists(key)) {
            Variables.set(key, value.eval());
        } else {
            throw new RuntimeException(key + " is undefined");
        }
    }
}
