package org.romashkoyyt.lib;

public class StringValue implements Value {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String asString() {
        try {
            if (Double.parseDouble(value) == (int)Double.parseDouble(value)) {
                return Integer.toString((int)Double.parseDouble(value));
            }
        } catch (NumberFormatException _) {}
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public double asNumber() {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException _) {
            return 0.0;
        }
    }

    @Override
    public boolean asBoolean() {
        throw new RuntimeException("Can't convert type string to type boolean");
    }
}
