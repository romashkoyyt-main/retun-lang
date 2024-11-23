package org.redittext.lib;

public class NumberValue implements Value {
    private final double value;

    public NumberValue(double value) {
        this.value = value;
    }

    @Override
    public String asString() {
        if (value == (int) value) {
            return Integer.toString((int) value);
        }
        return Double.toString(value);
    }

    @Override
    public String toString() {
        return asString();
    }

    @Override
    public double asNumber() {
        return value;
    }
}
