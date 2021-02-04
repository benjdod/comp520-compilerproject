package syntacticAnalyzer;

import syntacticAnalyzer.SyntaxError;
import miniJava.ErrorReporter;
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

    private boolean isBinaryOp(TokenType type) {
        switch (type) {
            case Plus:
            case Minus:
            case Star:
            case FSlash:     
            case RCaret:   
            case LCaret:     
            case LessEqual:    
            case GreaterEqual:    
            case EqualEqual:      
            case NotEqual:     
            case AmpAmp:     
            case BarBar:     
                return true;
            default: 
                return false;
        }
    }

    public void accept(TokenType type) throws SyntaxError {
        if (_token.type == type) {
            StackTraceElement[] trace_elts = Thread.currentThread().getStackTrace();
            int idx;
            for (StackTraceElement elt : trace_elts) {
                if ((idx = elt.toString().indexOf("parse")) != -1) {
                    System.out.println("Parser accepted " + type + " in " + elt.toString().substring(idx));
                    break;
                }
            }
            _token = _scanner.next();
        } else {
            StackTraceElement[] trace_elts = Thread.currentThread().getStackTrace();
            System.out.println("error!");

            for (StackTraceElement elt : trace_elts) {
                if ((elt.toString().indexOf("parse")) != -1) {
                    System.out.println(elt.toString());
                }
            }
            _reporter.report("Expected " + type + " but saw " + _token.type + " at (" + _token.line + ", " + _token.column + ")");
            throw new SyntaxError();
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
        accept(TokenType.Class);
        accept(TokenType.Ident);
        accept(TokenType.LBrace);

        while (_token.type != TokenType.RBrace) {
            // fuzzy for field vs method declaration...
            if (_token.type == TokenType.Public ||
                _token.type == TokenType.Private) {
                acceptIt();
            } else {
                throw new SyntaxError("Expected access specifier");
            }


            if (_token.type == TokenType.Static) {
                acceptIt();
            }

            boolean method_dec = false;

            if (_token.type == TokenType.Void) {
                method_dec = true;
                acceptIt();
            } else if (_token.type == TokenType.Int || _token.type == TokenType.Boolean) {
                acceptIt();
            } else {
                throw new SyntaxError("Expected type specifier");
            }

            accept(TokenType.Ident);

            if (_token.type == TokenType.LParen) {
                parseParameterList();
                parseStatementBlock();
            } else {
                if (method_dec) throw new SyntaxError("Expected non-void type specifier");
                else accept(TokenType.Semicolon);
            }
        }

        accept(TokenType.RBrace);
    }

    private void parseParameterList() {
        accept(TokenType.LParen);
        
        // this is debatably unnecessary ninja stuff
        while (true)  {
            if (_token.type == TokenType.Int || _token.type == TokenType.Boolean) {
                acceptIt();
            } else {throw new SyntaxError("Expected type specifier for parameter, saw " + _token.type + " instead");}
            accept(TokenType.Ident);

            if (_token.type == TokenType.Comma) {
                acceptIt();
                continue;
            } else {
                break;
            }
        } 

        accept(TokenType.RParen);
    }
    

    private void parseStatement() throws SyntaxError {

        if (_token.type == TokenType.LBrace) {
            parseStatementBlock();
        } else {
            switch (_token.type) {
                case Int:
                case Boolean:
                    parseLocalDeclaration();
                    accept(TokenType.Semicolon);
                    break;
                case Ident:
                case This:
                    parseLocalReference();
                    accept(TokenType.Semicolon);
                    break;
                case If:

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
                default:
                    throw new SyntaxError("Invalid token in statement");
            }
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

    private void parseStatementBlock() throws SyntaxError {

        acceptIt();

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
        acceptIt();
        accept(TokenType.Ident);
        accept(TokenType.Equal);
        parseExpression();
    }

    private void parseLocalReference() throws SyntaxError {
        acceptIt();

        if (_token.type == TokenType.LParen) {
            parseMethodCall();
            return;
        } else {
            if (_token.type == TokenType.LBracket) parseArrayIndex();
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
                if (_token.type == TokenType.LBrace) {
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
            if (_token.type == TokenType.Dot) {
                acceptIt();
                parseReference();
            }
        } else {
            throw new SyntaxError("Syntax error in reference");
        }
    }
}
