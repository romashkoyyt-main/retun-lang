package org.romashkoyyt.ast;

import org.romashkoyyt.lib.*;

import java.util.ArrayList;

public class FunctionalExpression implements Expression {

    private final String name;
    private final ArrayList<Expression> arguments;

    public FunctionalExpression(String name, ArrayList<Expression> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public FunctionalExpression(String name) {
        this.name = name;
        this.arguments = new ArrayList<>();
    }

    public void addArgument(Expression arg) {
        arguments.add(arg);
    }

    @Override
    public Value eval() {
        final int size = arguments.size();
        final Value[] values = new Value[size];

        for (int i = 0; i < size; i++) {
            values[i] = arguments.get(i).eval();
        }

        final Function function = Functions.get(name);
        if (function instanceof UserDefineFunction) {
            final UserDefineFunction userFunction = (UserDefineFunction) function;
            if (size != userFunction.getArgsCount()) {
                throw new RuntimeException("Args count mismatch");
            }

            Variables.push();
            for (int i = 0; i < size; i++) {
                Variables.set(userFunction.getArgsName(i), values[i]);
            }
            final Value result = userFunction.execute(values);
            Variables.pop();
            return result;
        }
        return function.execute(values);
    }
}
