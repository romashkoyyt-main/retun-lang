package org.redittext.ast;

import org.redittext.lib.NumberValue;
import org.redittext.lib.StringValue;
import org.redittext.lib.Value;

public class BinaryExpression implements Expression {
    private final Expression number1,  number2;
    private final char operation;

    public BinaryExpression(Expression number1, Expression number2, char operation) {
        this.number1 = number1;
        this.number2 = number2;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return String.format("(%s %c %s)", number1, operation, number2);
    }

    @Override
    public Value eval() {
        Value res;
        Value value1 = number1.eval();
        Value value2 = number2.eval();

        if (value1 instanceof StringValue) {
            switch (operation) {
                case '*': {
                    StringBuilder output = new StringBuilder();

                    final int iterations = (int)value2.asNumber();
                    for (int i = 0; i < iterations; i++) {
                        output.append(value1.asString());
                    }

                    return new StringValue(output.toString());
                }
                case ',':
                    return new StringValue(value1.asString() + '\n' + value2.asString());
                case '+':
                default:
                    return new StringValue(value1.asString() + value2.asString());
            }
        }

        if (value2 instanceof StringValue) {
            switch (operation) {
                case '*': {
                    StringBuilder output = new StringBuilder();

                    final int iterations = (int)value1.asNumber();
                    for (int i = 0; i < iterations; i++) {
                        output.append(value2.asString());
                    }

                    return new StringValue(output.toString());
                }
                case ',':
                    return new StringValue(value1.asString() + '\n' + value2.asString());
                case '+':
                default:
                    return new StringValue(value1.asString() + value2.asString());
            }
        }

        switch (operation) {
            case '+':
                res = new NumberValue(value1.asNumber() + value2.asNumber());
                break;
            case '-':
                res = new NumberValue(value1.asNumber() - value2.asNumber());
                break;
            case '*':
                res = new NumberValue(value1.asNumber() * value2.asNumber());
                break;
            case '/':
                res = new NumberValue(value1.asNumber() / value2.asNumber());
                break;
            case '^':
                res = new NumberValue(Math.pow(value1.asNumber(), value2.asNumber()));
                break;
            case ',':
                res = new StringValue(value1.asString() + '\n' + value2.asString());
                break;
            default: res = new StringValue(value1.asString() + value2.asString());
        }

        return res;
    }
}
