package org.romashkoyyt;

import org.romashkoyyt.ast.Statement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        final String input = Files.readString(Path.of("path/to/file.ret"));
        Lexer lexer = new Lexer(input);
        ArrayList<Token> tokens = lexer.tokenize();

        boolean debug = false;

        if (debug) {
            for (Token tok : tokens) {
                System.out.println(tok);
            }
        }

        Parser parser = new Parser(tokens);
        Statement program = parser.parse();

        if (debug) {
            System.out.println(program);
        }

        program.execute();
    }
}
