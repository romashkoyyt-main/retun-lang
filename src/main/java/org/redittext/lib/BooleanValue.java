package org.redittext.lib;

public class BooleanValue implements Value {

    private final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return asString();
    }

    @Override
    public String asString() {
        return Boolean.toString(value);
    }

    @Override
    public double asNumber() {
        if (value) return 1;
        else return 0;
    }
}
