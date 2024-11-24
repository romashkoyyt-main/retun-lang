package org.romashkoyyt.lib;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Variables {

    private static Map<String, Value> variables = new HashMap<>();
    private static final Stack<Map<String, Value>> stack = new Stack<>();

    static {
        variables.put("PI", new NumberValue(Math.PI));
        variables.put("E", new NumberValue(Math.E));
    }

    public static void push() {
        stack.push(new HashMap<>(variables));
    }

    public static void pop() {
        variables = stack.pop();
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