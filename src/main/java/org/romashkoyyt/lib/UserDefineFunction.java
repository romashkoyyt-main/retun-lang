package org.romashkoyyt.lib;

import org.romashkoyyt.ast.ReturnStatement;
import org.romashkoyyt.ast.Statement;

import java.util.ArrayList;

public class UserDefineFunction implements Function {

    private final ArrayList<String> argNames;
    private final Statement body;

    public UserDefineFunction(ArrayList<String> argNames, Statement body) {
        this.argNames = argNames;
        this.body = body;
    }

    public int getArgsCount() {
        return argNames.size();
    }

    public String getArgsName(int index) {
        if (index < 0 || index >= getArgsCount()) return "";
        return argNames.get(index);
    }

    @Override
    public Value execute(Value... args) {
        try {
            body.execute();
            return NumberValue.ZERO;
        } catch (ReturnStatement rt) {
            return rt.getResult();
        }
    }
}
