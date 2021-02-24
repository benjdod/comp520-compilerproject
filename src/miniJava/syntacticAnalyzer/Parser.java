package miniJava.SyntacticAnalyzer;

import java.util.Iterator;

import miniJava.ErrorReporter;
import miniJava.SyntacticAnalyzer.SyntaxError;
import miniJava.AbstractSyntaxTrees.*;
import miniJava.AbstractSyntaxTrees.Package;

public class Parser {

    private ErrorReporter _reporter = null;
    private Scanner _scanner = null;

    private Token _token;
    
    private Package _package;

    public Parser(Scanner scanner, ErrorReporter reporter) {
        this._reporter = reporter;
        this._scanner = scanner;
        this._package = new Package(new ClassDeclList(), new SourcePosition(1,1));
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
        
    private boolean isRefContinuer() {
    	return (_token.type == TokenType.Dot && (! _scanner.spaceBefore()));
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

    public AST parse() {
        _token = _scanner.next();   // prime the scanner
        
        AST out = null;
        try {
            out = parseClassDeclaration();
            accept(TokenType.Eot);   
        } catch (SyntaxError e) {
            _reporter.report(e);
        }
        return out;
    }
    
    public Package parsePackage() throws SyntaxError {
    	// the first "real" top level parse method
    	
    	Package out = new Package(new ClassDeclList(), new SourcePosition(1,1));
    	        
        while (_token.type == TokenType.Class) {
            out.classDeclList.add(parseClassDeclaration());
        } 
                        
        if (_token.type != TokenType.Eot) {
            throw new SyntaxError("invalid class declaration. Expected EOT, saw " + _token.type, _token.mark);
        }
    	
    	return out;
    }

    
    private ClassDecl parseClassDeclaration() throws SyntaxError {
        accept(TokenType.Class);
        
    	String classname = _token.spelling;
        // System.out.println("!!!!!!!!" + _token.type);
    	SourcePosition mark = _token.mark;
    	ClassDecl cd = new ClassDecl(classname, new FieldDeclList(), new MethodDeclList(), mark);
    	
        accept(TokenType.Ident);
        accept(TokenType.LBrace);
        
        while (_token.type != TokenType.RBrace) {
        	
            boolean is_private = false;
            boolean is_static = false;
        	
        	// copied verbatim from parseClassLevelStatement...
        	            	
        	if (_token.type == TokenType.Public ||
                _token.type == TokenType.Private) {
        		
        		if (_token.type == TokenType.Private) is_private = true;
        		
                acceptIt();
            } 

            if (_token.type == TokenType.Static) {
            	is_static = true;
                acceptIt();
            }
            
            TypeDenoter td = null;
            MemberDecl md = null;
            String member_name;

            if (_token.type == TokenType.Void) {
            	td = new BaseType(TypeKind.VOID, _token.mark);
                acceptIt();
                member_name = _token.getSpelling();
                accept(TokenType.Ident);
                
                md = new FieldDecl(is_private, is_static, td, member_name, td.posn);
                
                cd.methodDeclList.add(parseClassLevelMethodTail(md));
            } else {
                td = parseType();
                member_name = _token.getSpelling();
                accept(TokenType.Ident);
                if (_token.type == TokenType.Semicolon) {
                    accept(TokenType.Semicolon);
                    FieldDecl fd = new FieldDecl(is_private, is_static, td, member_name, td.posn);
                    cd.fieldDeclList.add(fd);
                } else {
                	md = new FieldDecl(is_private, is_static, td, member_name, td.posn);
                    cd.methodDeclList.add(parseClassLevelMethodTail(md));
                }
            }
        }

        accept(TokenType.RBrace);
        
        return cd;
        
    }

    private MethodDecl parseClassLevelMethodTail(MemberDecl md) throws SyntaxError {
    	
    	MethodDecl out = new MethodDecl(md, null, null, md.posn);
    	
        ParameterDeclList pdl = parseParameterList();
        StatementList sl = parseStatementBlock().sl;
        
        out.parameterDeclList = pdl;
        out.statementList = sl;
        
        return out;
    }

    private MemberDecl parseClassLevelStatement() throws SyntaxError {
        // CLS      ::= vis access CLS'
        // CLS'     ::= type CLS"? | void CLS"
        // CLS"     ::= id (params) {statement*}
    	
    	boolean is_private = false;
    	boolean is_static = false;


        if (_token.type == TokenType.Public ||
            _token.type == TokenType.Private) {
            acceptIt();
        } 

        if (_token.type == TokenType.Static) {
            acceptIt();
        }

        if (_token.type == TokenType.Void) {
        	TypeDenoter td = new BaseType(TypeKind.VOID, _token.mark);
            acceptIt();
            String name = _token.getSpelling();
            SourcePosition name_mark = _token.getMark();
            accept(TokenType.Ident);
            
            MemberDecl md = new FieldDecl(is_private, is_static, td, name, name_mark);
            return parseClassLevelMethodTail(md);
        } else {
            TypeDenoter td = parseType();
            String name = _token.getSpelling();
            SourcePosition name_mark = _token.getMark();
            accept(TokenType.Ident);
            
            MemberDecl fd = new FieldDecl(is_private, is_static, td, name, name_mark);
            
            if (_token.type == TokenType.Semicolon) {
                acceptIt();
                return fd;
            } else {
                return parseClassLevelMethodTail(fd);
            }
        }
    }

    private ParameterDeclList parseParameterList() throws SyntaxError {
        accept(TokenType.LParen);
        
        ParameterDeclList pdl = new ParameterDeclList();
        
        TypeDenoter td = null;
        String name = null;

        if (_token.type != TokenType.RParen)
            while (true)  {

                td = parseType();
                name = _token.getSpelling();

                accept(TokenType.Ident);
                
                pdl.add(new ParameterDecl(td, name, td.posn));

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
        
        return pdl;
    }
    

    private Statement parseStatement() throws SyntaxError {
    	
    	Statement out = null;

        if (_token.type == TokenType.LBrace) {
            return parseStatementBlock();
        } else {
            switch (_token.type) {
                case If:
                    return parseIfStatement();
                case While:
                    return parseWhileStatement();
                case Return:
                    return parseReturnStatement();
                case RBrace:
                    break;
                case Int:
                case Boolean:
                    out = parseLocalDeclaration();
                    accept(TokenType.Semicolon);
                    return out;
                case Ident:
                	Identifier base_id = new Identifier(_token);
                    acceptIt();
                    if (_token.type == TokenType.LBracket) {
                        // could be TypeId[] Id = E; or Ref[E] = E;
                        acceptIt();
                        if (_token.type == TokenType.RBracket) {
                            // TypeId[]
                            acceptIt();
                            
                            TypeDenoter td = new ArrayType(new ClassType(base_id, base_id.posn), base_id.posn);
                            // parse Id = E;
                            out = parseLocalDeclarationTail(td);
                        } else {
                            // parse E] = E;
                            Expression index_expr = parseExpression();
                            accept(TokenType.RBracket);
                            accept(TokenType.Equal);
                            Expression val_expr = parseExpression();
                            
                            out = new IxAssignStmt(
                            		new IdRef(base_id, base_id.posn), 
                            		index_expr, 
                            		val_expr, 
                            		base_id.posn);
                        }
                    } else if (_token.type == TokenType.Ident) {
                        // parse Id = E;
                        out = parseLocalDeclarationTail(new ClassType(base_id, base_id.posn));
                    } else if (_token.type == TokenType.Dot) {
                        QualRef l_ref = parseReferenceTail(new IdRef(base_id, base_id.posn));
                        out = parseLocalReferenceTail(l_ref);
                        
                    } else if (_token.type == TokenType.LParen) {
                        out = parseMethodCallStatement(new IdRef(base_id, base_id.posn));
                    } else {
                        accept(TokenType.Equal);
                        Reference r = new IdRef(base_id, base_id.posn);
                        Expression assign_expr = parseExpression();
                        out = new AssignStmt(r, assign_expr, r.posn);
                    }
                    accept(TokenType.Semicolon);
                    return out;
                case This:
                    Reference r = new ThisRef(_token.mark);
                    r = parseReferenceTail(r);
                    out = parseLocalReferenceTail(r);
                    accept(TokenType.Semicolon);
                    return out;
                default:
                    throw new SyntaxError("Invalid token in statement", _token.mark);
        
            }
            return out;
        }
    }

    private TypeDenoter parseType() throws SyntaxError {
    	TypeDenoter td = null;
        if (_token.type == TokenType.Boolean) {
        	td = new BaseType(TypeKind.BOOLEAN, _token.mark);
            acceptIt();
        } else if( _token.type == TokenType.Int ||
            _token.type == TokenType.Ident) {
        	
        	if (_token.type == TokenType.Int) {
        		td = new BaseType(TypeKind.INT, _token.mark);
        	} else {
        		
        		// check to see if the identifier is a valid class.
        		
        		Iterator<ClassDecl> class_decls = _package.classDeclList.iterator();
        		ClassDecl cd;
        		boolean is_valid = false;
        		
        		while (class_decls.hasNext()) {
        			cd = class_decls.next();
        			
        			if (cd.name.contentEquals(_token.spelling)) {
        				is_valid = true;
        				break;
        			}
        		}
        		
        		if (is_valid)
        			td = new ClassType(new Identifier(_token), _token.mark);
        		else
        			td = new BaseType(TypeKind.UNSUPPORTED, _token.mark);
        	}
        	
            acceptIt();
            
            if (_token.type == TokenType.LBracket) {
                acceptIt();
                accept(TokenType.RBracket);
                td = new ArrayType(td, td.posn);
            } 
        } else {
        	td = new BaseType(TypeKind.ERROR, _token.mark);
            throw new SyntaxError("Expected valid type specifier, saw " + _token.type + " instead", _token.mark);
        }
        
        return td;
    }
    
    private ReturnStmt parseReturnStatement() throws SyntaxError {
    	Expression expr = null;
    	SourcePosition ret_mark = _token.mark;
    	
    	accept(TokenType.Return);
        if (_token.type == TokenType.Semicolon)
            accept(TokenType.Semicolon);
        else {
            expr = parseExpression();
            accept(TokenType.Semicolon);
        }
        
        return new ReturnStmt(expr, ret_mark);
    }

    private IfStmt parseIfStatement() throws SyntaxError {
    	    	
    	Expression expr = null;
    	Statement t = null;
    	Statement e = null;
    	
        accept(TokenType.If);
        accept(TokenType.LParen);
        expr = parseExpression();
        accept(TokenType.RParen);
        t = parseStatement();

        if (_token.type == TokenType.Else) {
            accept(TokenType.Else);
            e = parseStatement();
        }
        
        return new IfStmt(expr, e, t, expr.posn);
    }

    private WhileStmt parseWhileStatement() throws SyntaxError {
    	
    	Expression cond = null;
    	Statement st = null;
    	
        accept(TokenType.While);
        accept(TokenType.LParen);
        cond = parseExpression();
        accept(TokenType.RParen);
        st = parseStatement();
        
        return new WhileStmt(cond, st, cond.posn);
    }

    private BlockStmt parseStatementBlock() throws SyntaxError {
    	
    	SourcePosition mark = _token.mark;

        accept(TokenType.LBrace);
        
        BlockStmt bs = new BlockStmt(new StatementList(), mark);

        while (true) {
            if (_token.type == TokenType.RBrace) {
                acceptIt();
                break;
            } else {
                bs.sl.add(parseStatement());
            }
        }
        
        return bs;
    }

    private VarDeclStmt parseLocalDeclaration() throws SyntaxError {
        TypeDenoter td = parseType();
        return parseLocalDeclarationTail(td);
    }

    private VarDeclStmt parseLocalDeclarationTail(TypeDenoter td) throws SyntaxError {
    	String name = _token.getSpelling();
    	SourcePosition name_mark = _token.mark.makeCopy();
        accept(TokenType.Ident);
        accept(TokenType.Equal);
        Expression expr = parseExpression();
        VarDecl vd = new VarDecl(td, name, name_mark);
        return new VarDeclStmt(vd, expr, name_mark);
    }

    private void parseLocalReference() throws SyntaxError {
    	
    	// start with identifier token
    	
        IdRef idr = new IdRef(new Identifier(_token), _token.mark);
        accept(TokenType.Ident);
        parseLocalReferenceTail(idr);
    }

    private Statement parseLocalReferenceTail(Reference r) throws SyntaxError {
        if (_token.type == TokenType.LParen) {
            return parseMethodCallStatement(r);
        } else {
        	
        	if (_token.type == TokenType.Dot) {
                r = parseReferenceTail(r);
            } 
        	
        	Expression ix_expr = (_token.type == TokenType.LBracket) ? parseArrayIndex() : null;
            
            accept(TokenType.Equal);
            Expression val_expr = parseExpression();
            
            if (ix_expr != null)
            	return new IxAssignStmt(r, ix_expr, val_expr, r.posn);
            else 
            	return new AssignStmt(r, val_expr, r.posn);
        }
    }

    private Expression parseArrayIndex() throws SyntaxError {
    	Expression index_expr;
        accept(TokenType.LBracket);
        index_expr = parseExpression();
        accept(TokenType.RBracket);
        
        return index_expr;
    }

    private CallExpr parseMethodCallExpr(Reference r) throws SyntaxError {
    	
    	CallExpr ce = new CallExpr(r, new ExprList(), r.posn);
    	
        accept(TokenType.LParen);
        if (_token.type == TokenType.RParen) {
            accept(TokenType.RParen);
        } else {
            ce.argList.add(parseExpression());
            while (_token.type == TokenType.Comma) {
                acceptIt();
                ce.argList.add(parseExpression());
            }
            accept(TokenType.RParen);
        }
        
        return ce;
    }
    
    private CallStmt parseMethodCallStatement(Reference r) throws SyntaxError {
    	CallStmt cs = new CallStmt(r, new ExprList(), r.posn);
        accept(TokenType.LParen);
        if (_token.type == TokenType.RParen) {
            accept(TokenType.RParen);
        } else {
            cs.argList.add(parseExpression());
            while (_token.type == TokenType.Comma) {
                acceptIt();
                cs.argList.add(parseExpression());
            }
            accept(TokenType.RParen);
        }
        
        return cs;
    }

    private Expression parseDisjuncExpr(Expression left_expr) throws SyntaxError {
        Expression out = left_expr != null ? left_expr : parseConjuncExpr(null);
        Expression le = null;
        Expression re = null;
        Operator o = null;

        while (
            _token.type == TokenType.BarBar
        ) {
            le = out;   // left associative shift
            o = new Operator(_token);
            acceptIt();
            re = parseConjuncExpr(null);
            out = new BinaryExpr(o, le, re, le.posn);
        }

        return out;
    }

    private Expression parseConjuncExpr(Expression left_expr) throws SyntaxError {
        Expression out = left_expr != null ? left_expr : parseRelationalExpr(null);
        Expression le = null;
        Expression re = null;
        Operator o = null;

        while (
            _token.type == TokenType.BarBar
        ) {
            le = out;   // left associative shift
            o = new Operator(_token);
            acceptIt();
            re = parseRelationalExpr(null);
            out = new BinaryExpr(o, le, re, le.posn);
        }

        return out;
    }

    private Expression parseRelationalExpr(Expression left_expr) throws SyntaxError {

        Expression out = left_expr != null ? left_expr : parseAddSubExpr(null);
        
        Expression le = null;
        Expression re = null;
        Operator o = null;

        while (
            _token.type == TokenType.LessEqual      || 
            _token.type == TokenType.GreaterEqual   ||
            _token.type == TokenType.NotEqual       ||
            _token.type == TokenType.EqualEqual
        ) {
            le = out;   // left associative shift
            o = new Operator(_token);
            acceptIt();
            re = parseAddSubExpr(null);
            out = new BinaryExpr(o, le, re, le.posn);
        }

        return out;
    }

    private Expression parseAddSubExpr(Expression left_expr) throws SyntaxError {

        Expression out = left_expr != null ? left_expr : parseMultDivExpr(null);

        Expression le = null;
        Expression re = null;
        Operator o = null;

        while (
            _token.type == TokenType.Plus      || 
            _token.type == TokenType.Minus
        ) {
            le = out;   // left associative shift
            o = new Operator(_token); 
            acceptIt();
            re = parseMultDivExpr(null);
            out = new BinaryExpr(o, le, re, le.posn);
        }
        
        return out;
    }

    private Expression parseMultDivExpr(Expression left_expr) throws SyntaxError {

        Expression out = left_expr != null ? left_expr : parseUnaryExpr();

        Expression le = null;
        Expression re = null;
        Operator o = null;

        while (
            _token.type == TokenType.Star      || 
            _token.type == TokenType.FSlash
        ) {
            le = out;   // left associative shift
            o = new Operator(_token);
            acceptIt();
            re = parseUnaryExpr();
            out = new BinaryExpr(o, le, re, le.posn);
        }
        
        return out;
    }

    private Expression parseUnaryExpr() throws SyntaxError {

        Operator o = null;
        Expression e = null;

        if (_token.type == TokenType.Minus || _token.type == TokenType.Not) {
            acceptIt();
        }

        if (_token.type == TokenType.LParen) e = parseParenthetical();
        else e = parseLiteralExpr();

        return (o != null) ? new UnaryExpr(o, e, o.posn) : e; 
    }

    private Expression parseLiteralExpr() throws SyntaxError {
        if (_token.type == TokenType.Boolean) {
            return new LiteralExpr(new BooleanLiteral(_token), _token.mark);
        } else if (_token.type == TokenType.Int) {
            return new LiteralExpr(new IntLiteral(_token), _token.mark);
        } else if (_token.type == TokenType.Ident || _token.type == TokenType.This) {
            return new RefExpr(parseReference(), _token.mark);
        } else {
            throw new SyntaxError("invalid literal expression (saw " + _token.type + ").", _token.mark);
        }
    }

    private Expression parseParenthetical() throws SyntaxError {
        accept(TokenType.LParen);
        Expression e = parseExpression();
        accept(TokenType.RParen);

        return e;
    }

    private Expression parseExpression() throws SyntaxError {
    	Expression e = null;
        switch (_token.type) {
            case Num:
            	e = new LiteralExpr(new IntLiteral(_token), _token.mark);
                acceptIt();
                break;
            case True:
            case False:
            	e = new LiteralExpr(new BooleanLiteral(_token), _token.mark);
                acceptIt();
                break;
            case Ident:
            case This:
                Reference r = parseReference();
                if (_token.type == TokenType.LBracket) {
                    Expression ix = parseArrayIndex();
                    e = new IxExpr(r,ix,r.posn);
                } else if (_token.type == TokenType.LParen) {
                    e = parseMethodCallExpr(r);
                } else {
                    throw new SyntaxError("improper reference to 'this'", _token.mark);
                }
                break;
            case Minus:
            case Not:
                e = parseUnaryExpr();
                break;
            case LParen:  
                acceptIt();
                e = parseExpression();
                accept(TokenType.RParen);
                break;
            case New:
                acceptIt();
                if (_token.type == TokenType.Int) {
                    acceptIt();
                    Expression idx_expr = parseArrayIndex();
                    return new NewArrayExpr(new BaseType(TypeKind.INT, _token.mark), idx_expr, _token.mark)
                } else {
                    Identifier target_ident = new Identifier(_token);
                    accept(TokenType.Ident);
                    if (_token.type == TokenType.LParen) {
                        accept(TokenType.LParen);
                        accept(TokenType.RParen);
                    } else if (_token.type == TokenType.LBracket) {
                        Expression idx_expr = parseArrayIndex();
                        return new NewArrayExpr(new ClassType(target_ident, target_ident.posn), idx_expr, target_ident.posn)
                    }
                }
                break;
            default:
                throw new SyntaxError("bad expression!", _token.mark);
        }



        if (isBinaryOp(_token.type)) {
            parseDisjuncExpr();
        } 
        
        return null;
    }

    private Reference parseReference() throws SyntaxError {
    	
    	Reference r = null;
    	
        if (_token.type == TokenType.Ident || _token.type == TokenType.This) {
        	if (_token.type == TokenType.This) 
        		r = new ThisRef(_token.mark);
        	else 
        		r = new IdRef(new Identifier(_token), _token.mark);
        	
            acceptIt();
            
        	if (isRefContinuer())
        		r = parseReferenceTail(r);
        	
        } else {
            throw new SyntaxError("Syntax error in reference", _token.mark);
        }
        
        return r;
    }

    private QualRef parseReferenceTail(Reference r) throws SyntaxError {
    	
    	if (isRefContinuer())
    		accept(TokenType.Dot);
    	
    	QualRef qr = new QualRef(r, null, r.posn);
    	Identifier id = null;
    
    	if (! _scanner.spaceBefore()) {
    		id = new Identifier(_token);
    		accept(TokenType.Ident);
    		qr.id = new Identifier(_token);
    	}
    	
    	if (isRefContinuer()) {
    		qr = parseReferenceTail(qr);
    	}

    	return qr;
    	
    	/*
    	
        while (_token.type == TokenType.Dot && ! _scanner.spaceBefore()) {
        	
            accept(TokenType.Dot);
            
            if (_scanner.spaceBefore()) {
            	throw new SyntaxError(
            			"unterminated reference", 	// error
            			"dotted references cannot be whitespace separated", // hint
            			_token.mark);
            }
            accept(TokenType.Ident);
        }
        
        */
    }
}
