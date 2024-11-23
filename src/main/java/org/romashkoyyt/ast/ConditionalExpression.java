package org.romashkoyyt.ast;

import org.romashkoyyt.lib.BooleanValue;
import org.romashkoyyt.lib.NumberValue;
import org.romashkoyyt.lib.StringValue;
import org.romashkoyyt.lib.Value;

public class ConditionalExpression implements Expression {
    private final Expression number1,  number2;
    private final String operation;

    public ConditionalExpression(Expression number1, Expression number2, String operation) {
        this.number1 = number1;
        this.number2 = number2;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", number1, operation, number2);
    }

    @Override
    public Value eval() {
        Value res;
        Value value1 = number1.eval();
        Value value2 = number2.eval();

        if (value1 instanceof StringValue) {
            switch (operation) {
                case "==": {
                    return new BooleanValue(value1.asString().equals(value2.asString()));
                }
                case "!=": {
                    return new BooleanValue(!value1.asString().equals(value2.asString()));
                }
            }
        }

        if (value2 instanceof StringValue) {
            switch (operation) {
                case "==": {
                    return new BooleanValue(value1.asString().equals(value2.asString()));
                }
                case "!=": {
                    return new BooleanValue(!value1.asString().equals(value2.asString()));
                }
            }
        }

        switch (operation) {
            case ">":
                res = new BooleanValue(value1.asNumber() > value2.asNumber());
                break;
            case ">=":
                res = new BooleanValue(value1.asNumber() >= value2.asNumber());
                break;
            case "==":
                res = new BooleanValue(value1.asNumber() == value2.asNumber());
                break;
            case "<=":
                res = new BooleanValue(value1.asNumber() <= value2.asNumber());
                break;
            case "<":
                res = new BooleanValue(value1.asNumber() < value2.asNumber());
                break;
            case "!=":
                res = new BooleanValue(value1.asNumber() != value2.asNumber());
                break;
            default: res = new BooleanValue(value1.asNumber() == value2.asNumber());
        }

        return res;
    }
}
