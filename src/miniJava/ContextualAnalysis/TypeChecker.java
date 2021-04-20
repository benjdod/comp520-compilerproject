package miniJava.ContextualAnalysis;

import miniJava.ErrorReporter;
import miniJava.AbstractSyntaxTrees.*;
import miniJava.AbstractSyntaxTrees.Package;
import miniJava.SyntacticAnalyzer.SourcePosition;
import miniJava.SyntacticAnalyzer.Token;
import miniJava.SyntacticAnalyzer.TokenType;

public class TypeChecker implements Visitor<Object, TypeDenoter> {

    private ErrorReporter _reporter;
    private Package _tree;

    public TypeChecker(Package tree, ErrorReporter reporter) {
        this._reporter = reporter;
        this._tree = tree;
        validate();
    }

    private void validate() {

        if (_tree == null) return;

        try {
            visitPackage(_tree, null);
        } catch (TypeError e) {
            _reporter.report(e);
        }
    }

    @Override
    public TypeDenoter visitPackage(Package prog, Object arg)  {

        /* make types for all classes */
        for (ClassDecl cd : prog.classDeclList) {
            cd.type = makeClassType(cd);
        }

        for (ClassDecl cd : prog.classDeclList) {
            cd.visit(this, null);
        }
        return null;
    }

    @Override
    public TypeDenoter visitClassDecl(ClassDecl cd, Object arg)  {
        for (FieldDecl fd : cd.fieldDeclList) {
            fd.visit(this, null);
        }
        for (MethodDecl md : cd.methodDeclList) {
            md.visit(this, null);
        }

        return cd.type;
    }

    @Override
    public TypeDenoter visitFieldDecl(FieldDecl fd, Object arg)  {
        fd.type = fd.type.visit(this, null);
        return fd.type;
    }

    @Override
    public TypeDenoter visitMethodDecl(MethodDecl md, Object arg)  {

        for (ParameterDecl pd : md.parameterDeclList) {
            pd.visit(this, null);
        }

        for (Statement s : md.statementList) {
            s.visit(this, null);
        }

        md.type = md.type.visit(this, null);

        checkReturnType(md);
        
        /*
        if (md.type.typeKind != TypeKind.VOID) {
            checkReturnPath(md);
        } */
        
        return md.type;
    }

    @Override
    public TypeDenoter visitParameterDecl(ParameterDecl pd, Object arg)  {
        TypeDenoter t = pd.type.visit(this, null);
        return t;
    }

    @Override
    public TypeDenoter visitVarDecl(VarDecl decl, Object arg)  {
        decl.type = decl.type.visit(this, null);
        return decl.type;
    }

    @Override
    public TypeDenoter visitBaseType(BaseType type, Object arg)  {
        // mirror type back
        return type;
    }

    @Override
    public TypeDenoter visitClassType(ClassType type, Object arg)  {
        // weed out unsupported String class. otherwise, mirror back type
        if (type.className.spelling.contentEquals("String")) {
            return new BaseType(TypeKind.UNSUPPORTED, type.posn);
        }
        return type;
    }

    @Override
    public TypeDenoter visitArrayType(ArrayType type, Object arg)  {
        return type;
    }

    @Override
    public TypeDenoter visitBlockStmt(BlockStmt stmt, Object arg)  {
        for (Statement s : stmt.sl) {
            s.visit(this, null);
        }
        return null;
    }

    @Override
    public TypeDenoter visitVardeclStmt(VarDeclStmt stmt, Object arg)  {
        TypeDenoter vdt = stmt.varDecl.visit(this, null);
        TypeDenoter expt = stmt.initExp.visit(this, null);
        
        //System.out.println("assigning " + expt.typeKind + " to " + vdt.typeKind);
        
        if (! vdt.equals(expt)) {
            _reporter.report(new TypeError("Cannot assign an expression of type " + expt.typeKind + " to a declaration of type " + vdt.typeKind, stmt.posn));
            return new BaseType(TypeKind.ERROR, stmt.posn);
        }

        return vdt;
    }

    @Override
    public TypeDenoter visitAssignStmt(AssignStmt stmt, Object arg)  {
        TypeDenoter vt = stmt.val.visit(this, null);
        TypeDenoter rt = stmt.ref.visit(this, null);
        if (! vt.equals(rt)) {
            _reporter.report(new TypeError("Cannot assign expression of type " + vt.typeKind + " to reference of type " + rt.typeKind, stmt.posn));
            return new BaseType(TypeKind.ERROR, stmt.posn);
        }
        return vt;
    }

    @Override
    public TypeDenoter visitIxAssignStmt(IxAssignStmt stmt, Object arg) {
        TypeDenoter idx_expr = stmt.ix.visit(this, null);
        stmt.ref.visit(this, null);
        stmt.exp.visit(this, null);
        if (idx_expr.typeKind != TypeKind.INT) {
            _reporter.report(new TypeError("Invalid index type! (requires Int)", stmt.posn));
        }
        TypeDenoter array_elttype = ((ArrayType) stmt.ref.decl.type).eltType;
        if (! TypeDenoter.equals(array_elttype, stmt.exp.type)) {
            _reporter.report(new TypeError("Cannot assign " + stmt.exp.type.typeKind + " to array of " + array_elttype.typeKind, stmt.posn));
        }
        return null;
    }

    @Override
    public TypeDenoter visitCallStmt(CallStmt stmt, Object arg) {

        for (Expression e : stmt.argList) {
            e.visit(this, null);
        }

        if (! (stmt.methodRef.decl instanceof MethodDecl)) {
            _reporter.report(new TypeError("Reference to " + stmt.methodRef.decl.name + " is not a method", stmt.posn));
            return new BaseType(TypeKind.ERROR, stmt.posn);
        }
        MethodDecl md = (MethodDecl) stmt.methodRef.decl;
        checkArgs(stmt, md);
        return null;
    }

    @Override
    public TypeDenoter visitReturnStmt(ReturnStmt stmt, Object arg) {
        if (stmt.returnExpr != null) {
            stmt.returnExpr.visit(this, null);
        } 
        return null;
    }

    @Override
    public TypeDenoter visitIfStmt(IfStmt stmt, Object arg) {
        stmt.cond.visit(this, null);
        stmt.thenStmt.visit(this, null);
        if (stmt.elseStmt != null) {
            stmt.elseStmt.visit(this, null);
        }
        if (! stmt.cond.type.equals(new BaseType(TypeKind.BOOLEAN, stmt.posn))) {
            _reporter.report(new TypeError("If loop condition must be boolean type (saw "+ stmt.cond.type.typeKind +")", stmt.cond.posn));
            return new BaseType(TypeKind.ERROR, stmt.posn);
        }
        return null;
    }

    @Override
    public TypeDenoter visitWhileStmt(WhileStmt stmt, Object arg)  {
        stmt.cond.visit(this, null);
        stmt.body.visit(this, null);

        //System.out.println(stmt.cond.type);

        if (! stmt.cond.type.equals(new BaseType(TypeKind.BOOLEAN, stmt.posn))) {
            _reporter.report(new TypeError("While loop condition must be boolean type (saw "+ stmt.cond.type.typeKind +")", stmt.cond.posn));
            return new BaseType(TypeKind.ERROR, stmt.posn);
        }

        return null;
    }

    @Override
    public TypeDenoter visitUnaryExpr(UnaryExpr expr, Object arg)  {
        expr.expr.visit(this, null);
        expr.type = checkUnaryExpr(expr);
        return expr.type;
    }

    @Override
    public TypeDenoter visitBinaryExpr(BinaryExpr expr, Object arg)  {
        TypeDenoter lefttype = expr.left.visit(this, null);
        TypeDenoter righttype = expr.right.visit(this, null);
        //System.out.println("binary type kinds: " + expr.left.type.typeKind + "\t" + expr.right.type.typeKind);
        expr.type = checkBinExpr(expr);
        //System.out.println("returning type: " + expr.type.typeKind);
        return expr.type;
    }

    @Override
    public TypeDenoter visitRefExpr(RefExpr expr, Object arg) {
        expr.type = expr.ref.visit(this, null);
        return expr.type;
    }

    @Override
    public TypeDenoter visitIxExpr(IxExpr expr, Object arg) {
        expr.ixExpr.visit(this, null);
        if (expr.ixExpr.type.typeKind != TypeKind.INT) {
            _reporter.report(new TypeError("Array index is not an integer expression", expr.posn));
            expr.type = new BaseType(TypeKind.ERROR, expr.posn);
            return expr.type;
        }
        if (! (expr.ref.decl.type instanceof ArrayType)) {
            _reporter.report(new TypeError("Variable is not an array", expr.posn));
            expr.type = new BaseType(TypeKind.ERROR, expr.posn);
            return expr.type;
        }
        //System.out.println(expr.ref.decl.type.typeKind);
        expr.type = ((ArrayType) expr.ref.decl.type).eltType;
        return expr.type;
    }

    @Override
    public TypeDenoter visitCallExpr(CallExpr expr, Object arg) {

        for (Expression e : expr.argList) {
            e.visit(this, null);
        }

        if (! (expr.functionRef.decl instanceof MethodDecl)) {
            _reporter.report(new TypeError("reference " + expr.functionRef.decl.name + " is not a method", expr.posn));
            return new BaseType(TypeKind.ERROR, expr.posn);
        }
        MethodDecl call_md = (MethodDecl) expr.functionRef.decl;

        checkArgs(expr.argList, call_md, expr.posn);

        expr.type = expr.functionRef.decl.type;
        return expr.type;
    }

    @Override
    public TypeDenoter visitLiteralExpr(LiteralExpr expr, Object arg)  {
        expr.type = expr.lit.visit(this, null);
        return expr.type;
    }

    @Override
    public TypeDenoter visitNewObjectExpr(NewObjectExpr expr, Object arg)  {
        expr.type = expr.classtype.visit(this, null);
        return expr.type;
    }

    @Override
    public TypeDenoter visitNewArrayExpr(NewArrayExpr expr, Object arg)  {
        expr.type = new ArrayType(expr.eltType, expr.posn);
        return expr.type;
    }

    @Override
    public TypeDenoter visitThisRef(ThisRef ref, Object arg)  {
    	//System.out.println("this ref type: " + ref.decl.posn);
    	
        return ref.decl.type;
    }

    @Override
    public TypeDenoter visitIdRef(IdRef ref, Object arg) {
        
        ref.id.visit(this, null);
        //System.out.println(ref.id.decl.type);
        if (ref.id.decl.type.typeKind == TypeKind.UNSUPPORTED) {

            return new BaseType(TypeKind.UNSUPPORTED, ref.posn);
        } else if (ref.decl instanceof ClassDecl) {
            _reporter.report(new TypeError("reference to '" + ref.decl.name + "'' is a class, not a variable ", ref.posn));
            return new BaseType(TypeKind.ERROR, ref.posn);
        }
        return ref.decl.type;
    }

    @Override
    public TypeDenoter visitQRef(QualRef ref, Object arg) {
        //ref.id.visit(this, null);
        //System.out.println("type checking qual ref:\t\t" +  ref.id.hashCode());
        return ref.id.decl.type;
    }

    @Override
    public TypeDenoter visitIdentifier(Identifier id, Object arg)  {
        if (id.decl.type instanceof BaseType) {
            //System.out.println("id visit: " + ((BaseType) id.decl.type).typeKind);
        }
        //System.out.println(id.decl.type);
        return id.decl.type;
    }

    @Override
    public TypeDenoter visitOperator(Operator op, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitIntLiteral(IntLiteral num, Object arg) {
        return new BaseType(TypeKind.INT, num.posn);
    }

    @Override
    public TypeDenoter visitBooleanLiteral(BooleanLiteral bool, Object arg) {
        return new BaseType(TypeKind.BOOLEAN, bool.posn);

    }

    @Override
    public TypeDenoter visitNullLiteral(NullLiteral expr, Object arg) {
        return new BaseType(TypeKind.NULL, expr.posn);
    }

    /*
    private TypeDenoter getTypeFromDecl(Declaration d)  {
        if (d.type == null) {
            throw new TypeError("declaration" + d.name + " is a class declaration", d.posn);
        } else return d.type;
    }
    */

    private TypeDenoter checkBinExpr(BinaryExpr expr) {

        switch (expr.operator.type) {
            case EqualEqual:
            case NotEqual:

                /*
                TypeDenoter lt = expr.left.type;
                TypeDenoter rt = expr.right.type; 
                System.out.println(expr.left + "\t" + expr.right);
                */
                
                if (! TypeDenoter.equals(expr.left.type, expr.right.type)) {
                    _reporter.report(new TypeError("Both sides of a equivalence binary expression must be the same type", expr.posn)); 
                    return new BaseType(TypeKind.ERROR, expr.posn);
                }

                return new BaseType(TypeKind.BOOLEAN, expr.posn);
            case LessEqual:
            case GreaterEqual:
            case LCaret:
            case RCaret:
                if ( 
                    (expr.left.type.typeKind != TypeKind.INT && expr.right.type.typeKind != TypeKind.INT)
                ) {
                    _reporter.report(new TypeError("Both sides of an relational binary expression must be integers", expr.posn));
                    return new BaseType(TypeKind.ERROR, expr.posn);
                } else if (! TypeDenoter.equals(expr.left.type, expr.right.type)) {
                    _reporter.report(new TypeError("Both sides of an relational binary expression must be integers", expr.posn));
                    return new BaseType(TypeKind.ERROR, expr.posn);
                }
                return new BaseType(TypeKind.BOOLEAN, expr.posn);
            case Star:
            case Plus:
            case Minus:
            case FSlash:
            //System.out.println(expr.left.type + "\t" + expr.right.type);
                if ( 
                    (expr.left.type.typeKind != TypeKind.INT && expr.right.type.typeKind != TypeKind.INT)
                ) {
                    _reporter.report(new TypeError("Both sides of an arithetmical binary expression must be integers", expr.posn));
                    return new BaseType(TypeKind.ERROR, expr.posn);
                } else if (! TypeDenoter.equals(expr.left.type, expr.right.type)) {
                    _reporter.report(new TypeError("Both sides of an arithmetical binary expression must be integers", expr.posn));
                    return new BaseType(TypeKind.ERROR, expr.posn);
                }
                return new BaseType(TypeKind.INT, expr.posn);

            case AmpAmp:
            case BarBar:
            if ( 
                (expr.left.type.typeKind != TypeKind.BOOLEAN && expr.right.type.typeKind != TypeKind.BOOLEAN)
            ) {
                _reporter.report(new TypeError("Both sides of a boolean binary expression must be integers", expr.posn));
                return new BaseType(TypeKind.ERROR, expr.posn);
            } else if (! TypeDenoter.equals(expr.left.type, expr.right.type)) {
                _reporter.report(new TypeError("Both sides of a boolean binary expression must be integers", expr.posn));
                return new BaseType(TypeKind.ERROR, expr.posn);
            }
            return new BaseType(TypeKind.BOOLEAN, expr.posn);
            default:
                _reporter.report(new TypeError("unsupported operator type in expression", expr.posn));
                return new BaseType(TypeKind.ERROR, expr.posn);
        }
    }  

    private TypeDenoter checkUnaryExpr(UnaryExpr expr)  {
        switch (expr.operator.type) {
            case Minus:
                if (expr.expr.type.equals(new BaseType(TypeKind.INT, expr.posn))) {
                    return expr.expr.type;
                } else {
                    _reporter.report( new TypeError("Unary operand must be an integer", expr.posn));
                    return new BaseType(TypeKind.ERROR, expr.posn);
                }
            case Not:
                if (expr.expr.type.equals(new BaseType(TypeKind.BOOLEAN, expr.posn))) {
                    return expr.expr.type;
                } else {
                    _reporter.report(new TypeError("Unary ! operand must be a boolean", expr.posn));
                    return new BaseType(TypeKind.ERROR, expr.posn);
                }
            default:
                _reporter.report(new TypeError("Bad unary operator, cannot validate type", expr.posn));
                return new BaseType(TypeKind.ERROR, expr.posn);

        }
    }

    private void checkArgs(CallStmt stmt, MethodDecl md)  {
        ExprList el = stmt.argList;
        ParameterDeclList pdl = md.parameterDeclList;
        if (el.size() != pdl.size()) {
            _reporter.report(new TypeError("bad arguments for method " + md.name, "expected " + pdl.size() + " args, saw " + el.size(), stmt.posn));
        }

        int i = 0;
        int size = el.size();

        while (i < size) {
            //Expression e_arg = el.get(i);
            //System.out.println(e_arg.type);
            TypeDenoter expr_arg = el.get(i).type;
            TypeDenoter decl_arg = pdl.get(i).type;
            //System.out.println(expr_arg + "\t" + decl_arg);
            boolean match = TypeDenoter.equals(expr_arg, decl_arg);

            if (! match) {
                _reporter.report(new TypeError("mismatched arguments for method " + md.name, stmt.posn));
            }

            i += 1;
        }
    }

    private void checkArgs(ExprList el, MethodDecl md, SourcePosition listpos) {
        ParameterDeclList pdl = md.parameterDeclList;
        if (el.size() != pdl.size()) {
            _reporter.report(new TypeError("bad arguments for method " + md.name, "expected " + pdl.size() + " args, saw " + el.size(), listpos));
        }

        int i = 0;
        int size = el.size();

        while (i < size) {
            //Expression e_arg = el.get(i);
            //System.out.println(e_arg.type);
            TypeDenoter expr_arg = el.get(i).type;
            TypeDenoter decl_arg = pdl.get(i).type;
            //System.out.println(expr_arg + "\t" + decl_arg);
            boolean match = TypeDenoter.equals(expr_arg, decl_arg);

            if (! match) {
                _reporter.report(new TypeError("mismatched arguments for method " + md.name, listpos));
            }

            i += 1;
        }
    }

    private void checkReturnType(MethodDecl md)  {
        checkReturnType(md.statementList, md.type);
    }

    private void checkReturnType(StatementList sl, TypeDenoter expected)  {
        for (Statement s : sl) {
            checkReturnType(s, expected);
        }
    }

    private void checkReturnType(Statement s, TypeDenoter expected)  {
        if (s instanceof BlockStmt) {
            checkReturnType(((BlockStmt) s).sl, expected);
        } else if (s instanceof IfStmt) {
            IfStmt if_stmt = (IfStmt) s;
            checkReturnType(if_stmt.thenStmt, expected);
            if (if_stmt.elseStmt != null) {
                checkReturnType(if_stmt.elseStmt, expected);
            }
        } else if (s instanceof WhileStmt) {
            checkReturnType(((WhileStmt) s).body, expected);
        } else if (s instanceof ReturnStmt) {
            ReturnStmt rs = (ReturnStmt) s;
            TypeDenoter actual_rettype = (rs.returnExpr != null) ? rs.returnExpr.type : new BaseType(TypeKind.VOID, s.posn);
            //System.out.println("ret type: " + actual_rettype);
            if (! actual_rettype.equals(expected)) {
                _reporter.report(new TypeError("Invalid return type for method", s.posn));
            }
        }
    }

    private void checkReturnPath(MethodDecl md)  {

        checkReturnPath(md.statementList, md.type, md.posn);
    }

    private void checkReturnPath(StatementList sl, TypeDenoter expected, SourcePosition posn) {

        Statement last = sl.getLast();
        //System.out.println("method last st: " + last);
        if (last != null)
            checkReturnPath(last, expected, posn);
        else {
            if (expected.typeKind != TypeKind.VOID) {
                _reporter.report(new TypeError("block in non-void method returned nothing", posn));
            }
        }
    }

    private void checkReturnPath(Statement s, TypeDenoter expected, SourcePosition posn)  {
        if (s instanceof ReturnStmt) return;
        else if (s instanceof BlockStmt) {
            checkReturnPath(((BlockStmt) s).sl, expected, s.posn);
            return;
        } else if (s instanceof IfStmt) {
            IfStmt s_if = (IfStmt) s;

            if (s_if.elseStmt != null) {
                checkReturnPath(s_if.thenStmt, expected, posn);
                checkReturnPath(s_if.elseStmt, expected, posn);
                return;
            } else {
                checkReturnPath(s_if.thenStmt, expected, posn);
                _reporter.report(new TypeError("method does not return a value", "no else statement contains a valid return", posn));

            }
        } else {
            _reporter.report(new TypeError("method does not return a value", posn));
        }
    }

    private TypeDenoter makeClassType(ClassDecl cd) {

        // crude way of checking for unsupported String class
        if (cd.name == "String") {
            return new BaseType(TypeKind.UNSUPPORTED, cd.posn);
        }

        return new ClassType(new Identifier(new Token(TokenType.Ident, cd.posn, cd.name)), cd.posn);
    }
}
