package org.romashkoyyt.lib;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Functions {

    private static final NumberValue ZERO = new NumberValue(0);
    private static final Map<String, Function> functions = new HashMap<>();

    static {
        functions.put("sin", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("One argument expected");
                return new NumberValue(Math.sin(args[0].asNumber()));
            }
        });
        functions.put("cos", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("One argument expected");
                return new NumberValue(Math.cos(args[0].asNumber()));
            }
        });
        functions.put("toNumber", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("One argument expected");
                return new NumberValue(args[0].asNumber());
            }
        });
        functions.put("toString", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("One argument expected");
                return new StringValue(args[0].asString());
            }
        });
        functions.put("rand", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 2) throw new RuntimeException("Two argument expected");
                Random random = new Random();
                return new NumberValue(random.nextInt((int) args[0].asNumber(), (int) args[1].asNumber() + 1));
            }
        });
        functions.put("format", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length < 1) throw new RuntimeException("One or more argument expected");
                String[] array = new String[args.length];

                for (int i = 1; i < args.length; i++) {
                    array[i - 1] = args[i].asString();
                }

                String modified = args[0].asString().replace("%f", "%s");
                modified = modified.replace("%b", "%s");
                modified = modified.replace("%f", "%s");
                modified = modified.replace("%d", "%s");

                return new StringValue(String.format(modified, (Object[]) array));
            }
        });
        functions.put("read", new Function() {
            @Override
            public Value execute(Value... args) {
                if (args.length != 1) throw new RuntimeException("One argument expected");
                Scanner scanner = new Scanner(System.in);
                try {
                    System.out.print(args[0].asString());
                } catch (Exception _) {}
                return new StringValue(scanner.nextLine());
            }
        });
        functions.put("print", new Function() {
            @Override
            public Value execute(Value... args) {
                for (Value value : args) {
                    System.out.println(value.asString());
                }
                return ZERO;
            }
        });
        functions.put("write", new Function() {
            @Override
            public Value execute(Value... args) {
                for (Value value : args) {
                    System.out.print(value.asString());
                }
                return ZERO;
            }
        });
    }

    public static void set(String key, Function value) {
        functions.put(key, value);
    }

    public static Function get(String key) {
        if (functions.containsKey(key)) {
            return functions.get(key);
        } else {
            throw new RuntimeException("Unknown function: " + key);
        }
    }

    public static boolean isExists(String key) {
        return functions.containsKey(key);
     }
}