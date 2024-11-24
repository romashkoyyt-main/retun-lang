package org.romashkoyyt.ast;

import org.romashkoyyt.lib.Functions;
import org.romashkoyyt.lib.UserDefineFunction;

import java.util.ArrayList;

public class FunctionDefine implements Statement {

    private final String name;
    private final ArrayList<String> argNames;
    private final Statement statement;

    public FunctionDefine(String name, ArrayList<String> argNames, Statement statement) {
        this.name = name;
        this.argNames = argNames;
        this.statement = statement;
    }

    @Override
    public void execute() {
        Functions.set(name, new UserDefineFunction(argNames, statement));
    }
}
