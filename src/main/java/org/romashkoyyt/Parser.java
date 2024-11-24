package org.romashkoyyt;

import org.romashkoyyt.ast.*;
import org.romashkoyyt.lib.*;

import java.util.ArrayList;

public class Parser {

    private final ArrayList<Token> tokens;
    private int pos = 0;
    private final int length;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.length = tokens.size();
    }

    public Statement parse() {
        BlockStatement statements = new BlockStatement();

        while (!match(TokenType.EOF)) {
            statements.add(statement());
        }

        return statements;
    }

    private FunctionDefine functionDefine() {
        final String name = peek().getValue();
        consume(TokenType.WORD);
        final FunctionalExpression function = new FunctionalExpression(name);
        final ArrayList<String> argNames = new ArrayList<>();
        consume(TokenType.LPAREN);

        while (!match(TokenType.RPAREN)) {
            consume(TokenType.VAR);
            String arg = peek().getValue();
            consume(TokenType.WORD);
            argNames.add(arg);
            match(TokenType.COM);
        }
        Statement statement = statementOrBlock();
        return new FunctionDefine(name, argNames, statement);
    }

    private FunctionalExpression function(String name) {
        final FunctionalExpression function = new FunctionalExpression(name);

        while (!match(TokenType.RPAREN)) {
            function.addArgument(expression());
            match(TokenType.COM);
        }

        return function;
    }

    private Statement statement() {
        if (match(TokenType.IF)) {
            return ifStatement();
        }
        if (match(TokenType.COMMENT)) {
            return new CommentStatement();
        }
        if (match(TokenType.WHILE)) {
            return whileStatement();
        }
        if (match(TokenType.DO)) {
            return doWhileStatement();
        }
        if (match(TokenType.FOR)) {
            return forStatement();
        }
        if (match(TokenType.BREAK)) {
            consume(TokenType.SEMICOLON);
            return new BreakStatement();
        }
        if (match(TokenType.CONTINUE)) {
            consume(TokenType.SEMICOLON);
            return new ContinueStatement();
        }
        if (match(TokenType.RETURN)) {
            Expression expr = expression();
            consume(TokenType.SEMICOLON);
            return new ReturnStatement(expr);
        }
        if (match(TokenType.FUNC)) {
            return functionDefine();
        }
        if (peek().getType() == TokenType.WORD) {
            final String name = peek().getValue();
            consume(TokenType.WORD);
            if (match(TokenType.LPAREN)) {
                FunctionalExpression expr = function(name);
                consume(TokenType.SEMICOLON);
                return new FunctionStatement(expr);
            }
            return reassigmentStatement(name);
        }
        return assignmentStatement();
    }

    private Statement doWhileStatement() {
        Statement statement = statementOrBlock();
        consume(TokenType.WHILE);
        Expression expr = expression();
        consume(TokenType.SEMICOLON);
        return new DoWhileStatement(expr, statement);
    }

    private Statement whileStatement() {
        Expression expr = expression();
        Statement statement = statementOrBlock();
        return new WhileStatement(expr, statement);
    }

    private Statement forStatement() {
        match(TokenType.LPAREN);
        consume(TokenType.VAR);
        String name = peek().getValue();
        consume(TokenType.WORD);
        consume(TokenType.EQ);
        Expression expr1 = expression();
        consume(TokenType.TO);
        Expression expr2 = expression();
        Statement incr = null;

        if (match(TokenType.WITH)) {
            incr = statement();
        }

        match(TokenType.RPAREN);

        Statement statement = statementOrBlock();
        return new ForStatement(name, expr1, expr2, incr, statement);
    }

    private Statement block() {
        BlockStatement statements = new BlockStatement();

        while (!match(TokenType.END)) {
            statements.add(statement());
        }

        return statements;
    }

    private Statement statementOrBlock() {
        if (match(TokenType.THEN)) return block();
        else return statement();
    }

    private Statement ifStatement() {
        Expression expr = expression();
        Statement statement = statementOrBlock();
        if (match(TokenType.ELSE)) {
            Statement statement2 = statementOrBlock();
            return new IfElseStatement(expr, statement, statement2);
        }

        return new IfElseStatement(expr, statement);
    }

    private Statement reassigmentStatement(String name) {
        if (match(TokenType.PLUS)) {
            consume(TokenType.PLUS);
            consume(TokenType.SEMICOLON);
            return new PlusPlusStatement(name);
        }
        if (match(TokenType.MINUS)) {
            consume(TokenType.MINUS);
            consume(TokenType.SEMICOLON);
            return new MinusMinusStatement(name);
        }
        consume(TokenType.EQ);
        Expression expr = expression();
        consume(TokenType.SEMICOLON);
        return new ReAssignmentStatement(name, expr);
    }

    private Statement assignmentStatement() {
        if (match(TokenType.VAR)) {
            String name = peek().getValue();
            consume(TokenType.WORD);
            consume(TokenType.EQ);
            Expression expr = expression();
            consume(TokenType.SEMICOLON);
            return new AssignStatement(name, expr);
        }

        throw new RuntimeException("Unknown statement: " + peek().getType());
    }

    private Expression expression() {
        return logicalOr();
    }

    private Expression logicalOr() {
        Expression expr = logicalAnd();

        while (true) {
            if (match(TokenType.OR)) {
                expr = new ConditionalExpression(expr, logicalAnd(), "|");
                continue;
            }
            break;
        }

        return expr;
    }

    private Expression logicalAnd() {
        Expression expr = conditional();

        while (true) {
            if (match(TokenType.ANDAND)) {
                expr = new ConditionalExpression(expr, conditional(), "&");
                continue;
            }
            break;
        }

        return expr;
    }

    private Expression conditional() {

        Expression expr = concatenation();

        if (match(TokenType.EQEQ)) {
            return new ConditionalExpression(expr, concatenation(), "==");
        }
        if (match(TokenType.GTEQ)) {
            return new ConditionalExpression(expr, concatenation(), ">=");
        }
        if (match(TokenType.GT)) {
            return new ConditionalExpression(expr, concatenation(), ">");
        }
        if (match(TokenType.LT)) {
            return new ConditionalExpression(expr, concatenation(), "<");
        }
        if (match(TokenType.LTEQ)) {
            return new ConditionalExpression(expr, concatenation(), "<=");
        }
        if (match(TokenType.NEQ)) {
            return new ConditionalExpression(expr, concatenation(), "!=");
        }
        if (match(TokenType.NOT)) {
            return new ConditionalExpression(expr, concatenation(), "!");
        }

        return expr;
    }

    private Expression concatenation() {
        Expression expr = additive();

        while (true) {
            if (match(TokenType.AND)) {
                expr = new BinaryExpression(expr, additive(), '&');
                continue;
            }
            break;
        }

        return expr;
    }

    private Expression additive() {
        Expression expr = multiplicative();

        while (true) {
            if (match(TokenType.PLUS)) {
                expr = new BinaryExpression(expr, multiplicative(), '+');
                continue;
            }
            if (match(TokenType.MINUS)) {
                expr = new BinaryExpression(expr, multiplicative(), '-');
                continue;
            }
            break;
        }

        return expr;
    }

    private Expression multiplicative() {
        Expression expr = power();

        while (true) {
            if (match(TokenType.STAR)) {
                expr = new BinaryExpression(expr, power(), '*');
                continue;
            }
            if (match(TokenType.SLASH)) {
                expr = new BinaryExpression(expr, power(), '/');
                continue;
            }
            break;
        }

        return expr;
    }

    private Expression power() {
        Expression expr = unary();

        while (true) {
            if (match(TokenType.POWER)) {
                expr = new BinaryExpression(expr, unary(), '^');
            }
            break;
        }

        return expr;
    }

    private Expression unary() {

        if (match(TokenType.MINUS)) {
            return new UnaryExpression(primary(), '-');
        }
        if (match(TokenType.PLUS)) {
            return primary();
        }
        return primary();
    }

    private Expression primary() {
        Token current = peek();
        // System.out.println("Current TokenType: " + current.getType());

        if (match(TokenType.NUMBER)) {
            return new ValueExpression(new NumberValue(Double.parseDouble(current.getValue())));
        }
        if (match(TokenType.HEX)) {
            return new ValueExpression(new NumberValue(Long.parseLong(current.getValue(), 16)));
        }
        if (match(TokenType.BIN)) {
            return new ValueExpression(new NumberValue(Integer.parseInt(current.getValue(), 2)));
        }
        if (peek().getType() == TokenType.WORD) {
            final String name = current.getValue();
            consume(TokenType.WORD);
            if (match(TokenType.LPAREN)) {
                return function(name);
            }
            return new VariableExpression(name);
        }
        if (match(TokenType.LPAREN)) {
            Expression expr = expression();
            consume(TokenType.RPAREN);
            return expr;
        }
        if (match(TokenType.STRING)) {
            return new ValueExpression(new StringValue(current.getValue()));
        }
        if (match(TokenType.TRUE)) {
            return new ValueExpression(new BooleanValue(true));
        }
        if (match(TokenType.FALSE)) {
            return new ValueExpression(new BooleanValue(false));
        }

        if (match(TokenType.NOT)) {
            Expression expr = expression();
            return new ConditionalExpression(expr, new ValueExpression(new BooleanValue(false)), "==");
        }

        throw new RuntimeException("Unknown expression");
    }

    private void consume(TokenType type) {
        Token current = peek();
        if (!match(type)) {
            throw new RuntimeException(current.getType() + " doesn't match type " + type);
        }
    }

    private boolean match(TokenType type) {
        if (peek().getType() == type) {
            pos++;
            return true;
        } else return false;
    }

    private Token peek() {
        if (length > pos) return tokens.get(pos);
        else return new Token(TokenType.EOF);
    }

}
