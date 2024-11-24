package org.romashkoyyt;

public enum TokenType {
    NUMBER,
    HEX,
    BIN,
    WORD,
    STRING,
    TRUE,
    FALSE,

    /*
    RAND,*/

    PLUS,
    MINUS,
    SLASH,
    STAR,

    EQ,
    EQEQ,
    GT,
    LT,
    GTEQ,
    LTEQ,
    NEQ,
    NOT,

    LPAREN,
    RPAREN,

    VAR,
    SEMICOLON,

    IF,
    ELSE,
    THEN,
    END,

    WHILE,
    FOR,
    FOREACH,
    COM,
    AND,
    ANDAND,
    OR,
    TO,
    WITH,
    DOT,

    DO,
    BREAK,
    CONTINUE,

    POWER,

    FUNC,
    RETURN,
    INCLUDE,

    CLASS,
    CLOSED,
    STATIC,

    OBJ,
    TAB,
    COMMENT,

    EOF
}
