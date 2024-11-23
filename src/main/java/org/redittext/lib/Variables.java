package org.redittext.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Variables {

    private static final Map<String, Value> variables = new HashMap<>();

    static {
        variables.put("PI", new NumberValue(Math.PI));
        variables.put("E", new NumberValue(Math.E));
    }

    public static void set(String key, Value value) {
        variables.put(key, value);
    }

    public static Value get(String key) {
        if (variables.containsKey(key)) {
            return variables.get(key);
        } else {
            throw new RuntimeException("Unknown variable: " + key);
        }
    }

    public static boolean isExists(String key) {
        return variables.containsKey(key);
     }
}