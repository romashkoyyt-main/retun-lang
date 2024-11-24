package org.romashkoyyt;

import java.util.ArrayList;

public class Lexer {
    private final String input;
    private final int length;
    private int pos = 0;

    ArrayList<Token> tokens;

    public Lexer(String input) {
        this.input = input;
        this.length = input.length();
    }

    public ArrayList<Token> tokenize() {
        tokens = new ArrayList<>();

        while (length > pos) {
            char current = peek();

            if (Character.isDigit(current)) {
                if (current == '0') {
                    char c = next();
                    if (c == 'x') {
                        next();
                        tokenizeHexNumber();
                    } else if (c == 'b') {
                        next();
                        tokenizeBinaryNumber();
                    } else {
                        back();
                        tokenizeNumber();
                    }
                } else {
                    tokenizeNumber();
                }
            } else if (Character.isLetterOrDigit(current)) {
                tokenizeWord();
            } else if (current == '"' || current == '\'') {
                tokenizeString();
            } else if (current == '+') {
                tokens.add(new Token(TokenType.PLUS));
                pos++;
            } else if (current == '-') {
                tokens.add(new Token(TokenType.MINUS));
                pos++;
            } else if (current == '*') {
                tokens.add(new Token(TokenType.STAR));
                pos++;
            } else if (current == '/') {
                if (next() == '*') {
                    pos++;
                    tokenizeComment();
                    continue;
                }
                back();
                tokens.add(new Token(TokenType.SLASH));
                pos++;
            } else if (current == '(') {
                tokens.add(new Token(TokenType.LPAREN));
                pos++;
            } else if (current == ')') {
                tokens.add(new Token(TokenType.RPAREN));
                pos++;
            } else if (current == '{') {
                tokens.add(new Token(TokenType.THEN));
                pos++;
            } else if (current == '}') {
                tokens.add(new Token(TokenType.END));
                pos++;
            } else if (current == '=') {
                if (next() == '=') {
                    tokens.add(new Token(TokenType.EQEQ));
                    next();
                    continue;
                }
                back();
                tokens.add(new Token(TokenType.EQ));
                pos++;
            } else if (current == ';') {
                tokens.add(new Token(TokenType.SEMICOLON));
                pos++;
            } else if (current == '&') {
                tokens.add(new Token(TokenType.AND));
                pos++;
            } else if (current == ',') {
                tokens.add(new Token(TokenType.COM));
                pos++;
            } else if (current == '^') {
                tokens.add(new Token(TokenType.POWER));
                pos++;
            } else if (current == '>') {
                if (next() == '=') {
                    tokens.add(new Token(TokenType.GTEQ));
                    next();
                    continue;
                }
                back();
                tokens.add(new Token(TokenType.GT));
                pos++;
            } else if (current == '<') {
                if (next() == '=') {
                    tokens.add(new Token(TokenType.LTEQ));
                    next();
                    continue;
                }
                back();
                tokens.add(new Token(TokenType.LT));
                pos++;
            } else if (current == '!') {
                if (next() == '=') {
                    tokens.add(new Token(TokenType.NEQ));
                    next();
                    continue;
                }
                back();
                tokens.add(new Token(TokenType.NOT));
                pos++;
            } else if (current == '.') {
                tokens.add(new Token(TokenType.DOT));
                pos++;
            } else {
                pos++;
            }
        }

        return tokens;
    }

    private void tokenizeBinaryNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        char current = peek();

        while (true) {
            if (current != '0' && current != '1') break;
            stringBuilder.append(current);
            current = next();
        }

        tokens.add(new Token(TokenType.BIN, stringBuilder.toString()));
    }

    private void tokenizeComment() {
        StringBuilder stringBuilder = new StringBuilder();
        char current = peek();

        while (true) {
            if (current == '*' && next() == '/') {
                next();
                break;
            }
            stringBuilder.append(current);
            current = next();
        }

        tokens.add(new Token(TokenType.COMMENT, stringBuilder.toString()));
    }

    private void tokenizeHexNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        char current = peek();

        while (true) {
            if (!Character.isDigit(current) && "abcdef".indexOf(Character.toLowerCase(current)) == -1) break;
            stringBuilder.append(current);
            current = next();
        }

        tokens.add(new Token(TokenType.HEX, stringBuilder.toString()));
    }

    private void tokenizeString() {
        StringBuilder stringBuilder = new StringBuilder();
        char current = next();

        while (length > pos) {
            if (current == '"' || current == '\'') break;
            if (current == '\\') {
                char next = next();
                if (next == 'n') {
                    stringBuilder.append('\n');
                } else if (next == 'b') {
                    stringBuilder.append('\b');
                } else if (next == 't') {
                    stringBuilder.append('\t');
                } else if (next == '\\') {
                    stringBuilder.append('\\');
                } else if (next == '"') {
                    stringBuilder.append('"');
                } else if (next == '\'') {
                    stringBuilder.append('\'');
                }
                current = next();
                continue;
            }
            stringBuilder.append(current);
            current = next();
        }

        tokens.add(new Token(TokenType.STRING, stringBuilder.toString()));
        pos++;
    }

    private void tokenizeWord() {
        StringBuilder stringBuilder = new StringBuilder();
        char current = peek();

        while (length > pos) {
            if (!Character.isLetterOrDigit(current) && current != '$' && current != '_') break;
            stringBuilder.append(current);
            current = next();
        }

        if (stringBuilder.toString().equals("if")) {
            tokens.add(new Token(TokenType.IF));
            return;
        } else if (stringBuilder.toString().equals("else")) {
            tokens.add(new Token(TokenType.ELSE));
            return;
        } else if (stringBuilder.toString().equals("var")) {
            tokens.add(new Token(TokenType.VAR));
            return;
        } else if (stringBuilder.toString().equals("for")) {
            tokens.add(new Token(TokenType.FOR));
            return;
        } else if (stringBuilder.toString().equals("foreach")) {
            tokens.add(new Token(TokenType.FOREACH));
            return;
        } else if (stringBuilder.toString().equals("func")) {
            tokens.add(new Token(TokenType.FUNC));
            return;
        } else if (stringBuilder.toString().equals("return")) {
            tokens.add(new Token(TokenType.RETURN));
            return;
        } else if (stringBuilder.toString().equals("include")) {
            tokens.add(new Token(TokenType.INCLUDE));
            return;
        } else if (stringBuilder.toString().equals("closed")) {
            tokens.add(new Token(TokenType.CLOSED));
            return;
        } else if (stringBuilder.toString().equals("static")) {
            tokens.add(new Token(TokenType.STATIC));
            return;
        } else if (stringBuilder.toString().equals("class")) {
            tokens.add(new Token(TokenType.CLASS));
            return;
        } else if (stringBuilder.toString().equals("obj")) {
            tokens.add(new Token(TokenType.OBJ));
            return;
        } else if (stringBuilder.toString().equals("tab")) {
            tokens.add(new Token(TokenType.TAB));
            return;
        } else if (stringBuilder.toString().equals("while")) {
            tokens.add(new Token(TokenType.WHILE));
            return;
        } else if (stringBuilder.toString().equals("true")) {
            tokens.add(new Token(TokenType.TRUE));
            return;
        } else if (stringBuilder.toString().equals("false")) {
            tokens.add(new Token(TokenType.FALSE));
            return;
        } else if (stringBuilder.toString().equals("and")) {
            tokens.add(new Token(TokenType.ANDAND));
            return;
        } else if (stringBuilder.toString().equals("or")) {
            tokens.add(new Token(TokenType.OR));
            return;
        } else if (stringBuilder.toString().equals("to")) {
            tokens.add(new Token(TokenType.TO));
            return;
        } else if (stringBuilder.toString().equals("with")) {
            tokens.add(new Token(TokenType.WITH));
            return;
        } else if (stringBuilder.toString().equals("do")) {
            tokens.add(new Token(TokenType.DO));
            return;
        } else if (stringBuilder.toString().equals("break")) {
            tokens.add(new Token(TokenType.BREAK));
            return;
        } else if (stringBuilder.toString().equals("continue")) {
            tokens.add(new Token(TokenType.CONTINUE));
            return;
        }

        tokens.add(new Token(TokenType.WORD, stringBuilder.toString()));
    }

    private void tokenizeNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        char current = peek();

        while (true) {
            if (!Character.isDigit(current) && current != '.') {
                break;
            }
            stringBuilder.append(current);
            current = next();
        }

        tokens.add(new Token(TokenType.NUMBER, stringBuilder.toString()));
    }

    private char next() {
        pos++;
        if (length > pos) return input.charAt(pos);
        else return '\0';
    }

    private char back() {
        pos--;
        if (length > pos) return input.charAt(pos);
        else return '\0';
    }

    private char peek() {
        if (length > pos) return input.charAt(pos);
        else return '\0';
    }
}