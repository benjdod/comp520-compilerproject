package syntacticAnalyzer;

import syntacticAnalyzer.SyntaxError;
import miniJava.ErrorReporter;
import java.util.function.*;
public class Parser {


    private ErrorReporter _reporter = null;
    private Scanner _scanner = null;

    private Token _token;

    public Parser(Scanner scanner, ErrorReporter reporter) {
        this._reporter = reporter;
        this._scanner = scanner;
    }

    public boolean ready() {
        return (
            _reporter != null &&
            _scanner != null
        );
    }

    private boolean isUnaryOp(TokenType type) {
        switch (type) {
            case Minus:
            case Not:
                return true;
            default: 
                return false;
        }
    }

    private boolean isArithOp(TokenType type) {
        switch (type) {
            case Plus:
            case Minus:   
            case Star:  
            case FSlash:  
                return true;
            default:
                return false;
        }
    }

    private boolean isComparatorOp(TokenType type) {
        switch(type) {
            case RCaret:
            case LCaret:
            case LessEqual:
            case GreaterEqual:
            case EqualEqual:
            case NotEqual:
                return true;
            default:
                return false;
        }
    }

    private boolean isLogicOp(TokenType type) {
        switch(type) {
            case Not: 
            case AmpAmp:  
            case BarBar:
                return true;
            default:
                return false;
        }
    }

    private boolean isBinaryOp(TokenType type) {
        return isArithOp(type) || isComparatorOp(type) || isLogicOp(type);
    }

    public void accept(TokenType type) throws SyntaxError {
        if (_token.type == type) {
            StackTraceElement[] trace_elts = Thread.currentThread().getStackTrace();
            int st, end;
            String s;
            for (StackTraceElement elt : trace_elts) {
                if ((st = elt.toString().indexOf("parse")) != -1) {
                    end = elt.toString().indexOf('(');
                    s = elt.toString().substring(st+5, end);
                    System.out.println("Parser accepted " + type + (s.length() > 0 ? (" in " + s) : ""));
                    break;
                }
            }
            _token = _scanner.next();
        } else {
            StackTraceElement[] trace_elts = Thread.currentThread().getStackTrace();
            System.out.println("error!");

            int st, end;

            for (StackTraceElement elt : trace_elts) {
                if ((st = elt.toString().indexOf("parse")) != -1) {
                    end = elt.toString().indexOf('(');
                    System.out.println("in " + elt.toString().substring(st + 5, end));
                }
            }
            throw new SyntaxError("Expected " + type + " but saw " + _token.type, _token.mark);
        }
    }

    public void acceptIt() {
        accept(_token.type);
    }

    public void parse() {
        _token = _scanner.next();   // prime the scanner
        try {
            parseClassDeclaration();
            accept(TokenType.Eot);
        } catch (SyntaxError e) {
            _reporter.report(e.getMessage());
        }
    }

    
    private void parseClassDeclaration() throws SyntaxError {
        while (_token.type == TokenType.Class) {
            accept(TokenType.Class);
            accept(TokenType.Ident);
            accept(TokenType.LBrace);

            while (_token.type != TokenType.RBrace) {
                parseClassLevelStatement();
            }

            accept(TokenType.RBrace);
        }
        if (_token.type != TokenType.Eot) {
            throw new SyntaxError("invalid class declaration. Expected EOT, saw " + _token.type, _token.mark);
        }
    }

    private void parseClassLevelMethodTail() throws SyntaxError {
        parseParameterList();
        parseStatementBlock();
    }

    private void parseClassLevelStatement() throws SyntaxError {
        // CLS      ::= vis access CLS'
        // CLS'     ::= type CLS"? | void CLS"
        // CLS"     ::= id (params) {statement*}


        if (_token.type == TokenType.Public ||
            _token.type == TokenType.Private) {
            acceptIt();
        } 

        if (_token.type == TokenType.Static) {
            acceptIt();
        }

        if (_token.type == TokenType.Void) {
            acceptIt();
            accept(TokenType.Ident);
            parseClassLevelMethodTail();
        } else {
            parseType();
            accept(TokenType.Ident);
            if (_token.type == TokenType.Semicolon) {
                acceptIt();
            } else {
                parseClassLevelMethodTail();
            }
        }
    }

    private void parseParameterList() throws SyntaxError {
        accept(TokenType.LParen);

        if (_token.type != TokenType.RParen)
            while (true)  {

                parseType();

                accept(TokenType.Ident);

                if (_token.type == TokenType.Comma) {
                    acceptIt();
                    continue;
                } else if (_token.type == TokenType.RParen) {
                    break;
                } else {
                    throw new SyntaxError("Expected comma to continue parameter list", _token.mark);
                }
            } 

        accept(TokenType.RParen);
    }
    

    private void parseStatement() throws SyntaxError {

        if (_token.type == TokenType.LBrace) {
            parseStatementBlock();
        } else {
            switch (_token.type) {
                case If:
                    parseIfStatement();
                    break;
                case While:
                    parseWhileStatement();
                    break;
                case Return:
                    accept(TokenType.Return);
                    if (_token.type == TokenType.Semicolon)
                        accept(TokenType.Semicolon);
                    else {
                        parseExpression();
                        accept(TokenType.Semicolon);
                    }
                    break;
                case RBrace:
                    break;
                case Int:
                case Boolean:
                    parseLocalDeclaration();
                    accept(TokenType.Semicolon);
                    break;
                case Ident:
                    acceptIt();
                    if (_token.type == TokenType.LBracket) {
                        // could be TypeId[] Id = E; or Ref[E] = E;
                        acceptIt();
                        if (_token.type == TokenType.RBracket) {
                            // TypeId[];
                            acceptIt();
                            // parse Id = E;
                            parseLocalDeclarationTail();
                        } else {
                            // parse E] = E;
                            parseExpression();
                            accept(TokenType.RBracket);
                            accept(TokenType.Equal);
                            parseExpression();
                        }
                    } else if (_token.type == TokenType.Ident) {
                        // parse Id = E;
                        parseLocalDeclarationTail();
                    } else if (_token.type == TokenType.Dot) {
                        acceptIt();
                        parseReference();
                        parseLocalReferenceTail();
                        
                    } else {
                        if (_token.type == TokenType.LParen) {
                            parseMethodCall();
                        } else {
                            accept(TokenType.Equal);
                            parseExpression();
                        }
                    }
                    accept(TokenType.Semicolon);
                    break;
                case This:
                    parseLocalReference();
                    accept(TokenType.Semicolon);
                    break;
                default:
                    throw new SyntaxError("Invalid token in statement", _token.mark);
            }
        }
    }

    private void parseType() throws SyntaxError {
        if (_token.type == TokenType.Boolean) 
            acceptIt();
        else if( _token.type == TokenType.Int ||
            _token.type == TokenType.Ident) {
            acceptIt();
            if (_token.type == TokenType.LBracket) {
                acceptIt();
                accept(TokenType.RBracket);
            }
        } else {
            throw new SyntaxError("Expected valid type specifier, saw " + _token.type + " instead", _token.mark);
        }
    }

    private void parseIfStatement() throws SyntaxError {
        accept(TokenType.If);
        accept(TokenType.LParen);
        parseExpression();
        accept(TokenType.RParen);
        parseStatement();

        if (_token.type == TokenType.Else) {
            accept(TokenType.Else);
            parseStatement();
        }
    }

    private void parseWhileStatement() throws SyntaxError {
        accept(TokenType.While);
        accept(TokenType.LParen);
        parseExpression();
        accept(TokenType.RParen);
        parseStatement();
    }

    private void parseStatementBlock() throws SyntaxError {

        accept(TokenType.LBrace);

        while (true) {
            if (_token.type == TokenType.RBrace) {
                acceptIt();
                break;
            } else {
                parseStatement();
            }
        }
    }

    private void parseLocalDeclaration() throws SyntaxError {
        parseType();
        parseLocalDeclarationTail();
    }

    private void parseLocalDeclarationTail() throws SyntaxError {
        accept(TokenType.Ident);
        accept(TokenType.Equal);
        parseExpression();
    }

    private void parseLocalReference() throws SyntaxError {
        acceptIt();
        parseLocalReferenceTail();
    }

    private void parseLocalReferenceTail() throws SyntaxError {
        if (_token.type == TokenType.LParen) {
            parseMethodCall();
            return;
        } else {
            if (_token.type == TokenType.LBracket) parseArrayIndex();
            else if (_token.type == TokenType.Dot) {
                parseReferenceTail();
            } 
            accept(TokenType.Equal);
            parseExpression();
        }
    }

    private void parseArrayIndex() throws SyntaxError {
        accept(TokenType.LBracket);
        parseExpression();
        accept(TokenType.RBracket);
    }

    private void parseMethodCall() throws SyntaxError {
        accept(TokenType.LParen);
        if (_token.type == TokenType.RParen) {
            accept(TokenType.RParen);
        } else {
            parseExpression();
            while (_token.type == TokenType.Comma) {
                acceptIt();
                parseExpression();
            }
            accept(TokenType.RParen);
        }
    }

    private void parseExpression() throws SyntaxError {
        switch (_token.type) {
            case Num:
            case True:
            case False:
                acceptIt();
                break;
            case Ident:
            case This:
                parseReference();
                if (_token.type == TokenType.LBracket) {
                    parseArrayIndex();
                } else if (_token.type == TokenType.LParen) {
                    parseMethodCall();
                }
                break;
            case Minus:
            case Not:
                acceptIt();
                parseExpression();
                break;
            case LParen:  
                acceptIt();
                parseExpression();
                accept(TokenType.RParen);
                break;
            case New:
                acceptIt();
                if (_token.type == TokenType.Int) {
                    acceptIt();
                    parseArrayIndex();
                } else {
                    accept(TokenType.Ident);
                    if (_token.type == TokenType.LParen) {
                        accept(TokenType.LParen);
                        accept(TokenType.RParen);
                    } else if (_token.type == TokenType.LBracket) {
                        parseArrayIndex();
                    }
                }
                break;
        }

        if (isBinaryOp(_token.type)) {
            acceptIt();
            parseExpression();
        }

    }

    private void parseReference() throws SyntaxError {
        if (_token.type == TokenType.Ident || _token.type == TokenType.This) {
            acceptIt();
            parseReferenceTail();
        } else {
            throw new SyntaxError("Syntax error in reference", _token.mark);
        }
    }

    private void parseReferenceTail() throws SyntaxError {
        while (_token.type == TokenType.Dot) {
            accept(TokenType.Dot);
            accept(TokenType.Ident);
        }
    }
}
