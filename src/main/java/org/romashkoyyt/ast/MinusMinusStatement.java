package org.romashkoyyt.ast;

import org.romashkoyyt.lib.NumberValue;
import org.romashkoyyt.lib.Value;
import org.romashkoyyt.lib.Variables;

public class MinusMinusStatement implements Statement {

    private final String name;

    public MinusMinusStatement(String name) {
        this.name = name;
    }

    @Override
    public void execute() {
        if (Variables.isExists(name)) {
            Value value = Variables.get(name);
            Variables.set(name, new NumberValue(value.asNumber() - 1));
        } else {
            throw new RuntimeException(name + " is undefined");
        }
    }
}
