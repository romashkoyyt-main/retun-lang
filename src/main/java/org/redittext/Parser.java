package org.redittext;

import org.redittext.ast.*;
import org.redittext.lib.*;

import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Parser {

    private final ArrayList<Token> tokens;
    private int pos = 0;
    private final int length;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.length = tokens.size();
    }

    public ArrayList<Statement> parse() {
        ArrayList<Statement> statements = new ArrayList<>();

        while (!match(TokenType.EOF)) {
            statements.add(statement());
        }

        return statements;
    }

    private Statement statement() {
        if (match(TokenType.PRINT)) {
            return printStatement();
        }
        if (match(TokenType.WRITE)) {
            return writeStatement();
        }
        if (peek().getType() == TokenType.WORD) {
            return reassigmentStatement();
        }
        if (match(TokenType.IF)) {
            return ifStatement();
        }
        if (match(TokenType.COMMENT)) {
            return new CommentStatement();
        }
        return assignmentStatement();
    }

    private Statement ifStatement() {
        if (match(TokenType.NOT)) {
            Expression expr2 = expression();
            Statement statement = statement();
            return new IfStatement(new ValueExpression(new BooleanValue(false)), expr2, statement, "==");
        }

        Expression expr1 = expression();
        if (match(TokenType.EQEQ)) {
            Expression expr2 = expression();
            Statement statement = statement();
            return new IfStatement(expr1, expr2, statement, "==");
        }
        if (match(TokenType.GT)) {
            Expression expr2 = expression();
            Statement statement = statement();
            return new IfStatement(expr1, expr2, statement, ">");
        }
        if (match(TokenType.LT)) {
            Expression expr2 = expression();
            Statement statement = statement();
            return new IfStatement(expr1, expr2, statement, "<");
        }
        if (match(TokenType.LTEQ)) {
            Expression expr2 = expression();
            Statement statement = statement();
            return new IfStatement(expr1, expr2, statement, "<=");
        }
        if (match(TokenType.GTEQ)) {
            Expression expr2 = expression();
            Statement statement = statement();
            return new IfStatement(expr1, expr2, statement, ">=");
        }
        if (match(TokenType.NEQ)) {
            Expression expr2 = expression();
            Statement statement = statement();
            return new IfStatement(expr1, expr2, statement, "!=");
        }

        Statement statement = statement();
        return new IfStatement(new ValueExpression(new BooleanValue(true)), expr1, statement, "==");

        // throw new RuntimeException("Incorrect 'if' statement");
    }

    private Statement reassigmentStatement() {
        String name = peek().getValue();
        consume(TokenType.WORD);
        if (match(TokenType.PLUS)) {
            consume(TokenType.PLUS);
            consume(TokenType.SEMICOLON);
            return new PlusPlusStatement(name);
        }
        consume(TokenType.EQ);
        Expression expr = expression();
        consume(TokenType.SEMICOLON);
        return new ReAssignmentStatement(name, expr);
    }

    private Statement printStatement() {
        consume(TokenType.LPAREN);
        Expression expr = expression();
        while (true) {
            if (match(TokenType.COM)) {
                expr = new BinaryExpression(expr, expression(), ',');
                continue;
            }
            break;
        }
        consume(TokenType.RPAREN);
        consume(TokenType.SEMICOLON);
        return new PrintStatement(expr);
    }

    private Statement writeStatement() {
        consume(TokenType.LPAREN);
        Expression expr = expression();
        while (true) {
            if (match(TokenType.COM)) {
                expr = new BinaryExpression(expr, expression(), '&');
                continue;
            }
            break;
        }
        consume(TokenType.RPAREN);
        consume(TokenType.SEMICOLON);
        return new WriteStatement(expr);
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

        throw new RuntimeException("Unknown statement");
    }

    private Expression expression() {
        return concatenation();
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
        if (match(TokenType.LPAREN)) {
            Expression expr = expression();
            consume(TokenType.RPAREN);
            return expr;
        }
        if (match(TokenType.WORD)) {
            return new VariableExpression(current.getValue());
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
        if (match(TokenType.READ)) {
            consume(TokenType.LPAREN);
            Expression expr = new ValueExpression(new StringValue(""));
            if (peek().getType() != TokenType.RPAREN) {
                expr = expression();
            }
            consume(TokenType.RPAREN);

            return new ReadExpression(expr);
        }
        if (match(TokenType.FORMAT)) {
            consume(TokenType.LPAREN);
            ArrayList<Expression> values = new ArrayList<>();
            Expression expr1 = expression();

            while (true) {
                if (match(TokenType.COM)) {
                    values.add(expression());
                    continue;
                }
                break;
            }

            consume(TokenType.RPAREN);

            return new FormatExpression(expr1, values);
        }
        if (match(TokenType.TONUM)) {
            consume(TokenType.LPAREN);
            Expression expr = expression();
            consume(TokenType.RPAREN);

            return new ToNumberExpression(expr);
        }
        if (match(TokenType.TOSTR)) {
            consume(TokenType.LPAREN);
            Expression expr = expression();
            consume(TokenType.RPAREN);

            return new ToStringExpression(expr);
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
