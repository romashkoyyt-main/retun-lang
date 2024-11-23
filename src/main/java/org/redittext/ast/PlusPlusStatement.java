package org.redittext.ast;

import org.redittext.lib.NumberValue;
import org.redittext.lib.Value;
import org.redittext.lib.Variables;

public class PlusPlusStatement implements Statement {

    private final String name;

    public PlusPlusStatement(String name) {
        this.name = name;
    }

    @Override
    public void execute() {
        if (Variables.isExists(name)) {
            Value value = Variables.get(name);
            Variables.set(name, new NumberValue(value.asNumber() + 1));
        } else {
            throw new RuntimeException(name + " is undefined");
        }
    }
}