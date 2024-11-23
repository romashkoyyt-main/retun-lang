package org.romashkoyyt;

public enum TokenType {
    NUMBER,
    HEX,
    WORD,
    STRING,
    TRUE,
    FALSE,

    PRINT,
    READ,
    WRITE,
    TONUM,
    TOSTR,
    FORMAT,
    RAND,

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
    TO,
    DOT,

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
