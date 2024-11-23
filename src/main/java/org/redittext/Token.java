package org.redittext;

public class Token {

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    private final TokenType type;
    private final String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public Token(TokenType type) {
        this.type = type;
        this.value = "";
    }

    @Override
    public String toString() {
        if (this.value.equals(""))
            return type + "";
        return type + " '" + value + "'";
    }
}
