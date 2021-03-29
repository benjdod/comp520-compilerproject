package miniJava.ContextualAnalysis;

import java.lang.ProcessBuilder.Redirect.Type;
import java.lang.reflect.Field;

import miniJava.ErrorReporter;
import miniJava.AbstractSyntaxTrees.*;
import miniJava.AbstractSyntaxTrees.Package;

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
    public TypeDenoter visitPackage(Package prog, Object arg) throws TypeError {
        for (ClassDecl cd : prog.classDeclList) {
            cd.visit(this, null);
        }
        return null;
    }

    @Override
    public TypeDenoter visitClassDecl(ClassDecl cd, Object arg) throws TypeError {
        for (FieldDecl fd : cd.fieldDeclList) {
            fd.visit(this, null);
        }
        for (MethodDecl md : cd.methodDeclList) {
            md.visit(this, null);
        }
        return null;
    }

    @Override
    public TypeDenoter visitFieldDecl(FieldDecl fd, Object arg) throws TypeError {
        return fd.type;
    }

    @Override
    public TypeDenoter visitMethodDecl(MethodDecl md, Object arg) throws TypeError {

        for (ParameterDecl pd : md.parameterDeclList) {
            pd.visit(this, null);
        }

        for (Statement s : md.statementList) {
            s.visit(this, null);
        }

        checkReturnType(md);

        // need return checking!
        
        return md.type;
    }

    @Override
    public TypeDenoter visitParameterDecl(ParameterDecl pd, Object arg) throws TypeError {
        return pd.type;
    }

    @Override
    public TypeDenoter visitVarDecl(VarDecl decl, Object arg) throws TypeError {
        return decl.type;
    }

    @Override
    public TypeDenoter visitBaseType(BaseType type, Object arg) throws TypeError {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitClassType(ClassType type, Object arg) throws TypeError {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitArrayType(ArrayType type, Object arg) throws TypeError {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitBlockStmt(BlockStmt stmt, Object arg) throws TypeError {
        for (Statement s : stmt.sl) {
            s.visit(this, null);
        }
        return null;
    }

    @Override
    public TypeDenoter visitVardeclStmt(VarDeclStmt stmt, Object arg) throws TypeError {
        TypeDenoter vdt = stmt.varDecl.visit(this, null);
        TypeDenoter expt = stmt.initExp.visit(this, null);

        if (! vdt.equals(expt)) {
            throw new TypeError("Cannot assign an expression of type " + expt.typeKind + " to a declaration of type " + vdt.typeKind, stmt.posn);
        }

        return vdt;
    }

    @Override
    public TypeDenoter visitAssignStmt(AssignStmt stmt, Object arg) throws TypeError {
        TypeDenoter vt = stmt.val.visit(this, null);
        TypeDenoter rt = stmt.ref.visit(this, null);
        if (! vt.equals(rt)) {
            throw new TypeError("Cannot assign expression of type " + vt.typeKind + " to reference of type " + rt.typeKind, stmt.posn);
        }
        return vt;
    }

    @Override
    public TypeDenoter visitIxAssignStmt(IxAssignStmt stmt, Object arg) {
        TypeDenoter idx_expr = stmt.ix.visit(this, null);
        stmt.exp.visit(this, null);
        if (idx_expr.typeKind != TypeKind.INT) {
            throw new TypeError("Invalid index type! (requires Int)", stmt.posn);
        }
        TypeDenoter array_elttype = ((ArrayType) stmt.ix.type).eltType;
        if (! TypeDenoter.equals(array_elttype, stmt.exp.type)) {
            throw new TypeError("Cannot assign " + stmt.exp.type.typeKind + " to array of " + array_elttype.typeKind, stmt.posn);
        }
        return null;
    }

    @Override
    public TypeDenoter visitCallStmt(CallStmt stmt, Object arg) {

        for (Expression e : stmt.argList) {
            e.visit(this, null);
        }

        if (! (stmt.methodRef.decl instanceof MethodDecl)) {
            throw new TypeError("Reference to " + stmt.methodRef.decl.name + " is not a method", stmt.posn);
        }
        MethodDecl md = (MethodDecl) stmt.methodRef.decl;
        checkArgs(stmt.argList,  md.parameterDeclList, md);
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
            throw new TypeError("If condition must be boolean type (saw "+ stmt.cond.type.typeKind +")", stmt.cond.posn);
        }
        return null;
    }

    @Override
    public TypeDenoter visitWhileStmt(WhileStmt stmt, Object arg) throws TypeError {
        stmt.cond.visit(this, null);
        stmt.body.visit(this, null);

        System.out.println(stmt.cond.type);

        if (! stmt.cond.type.equals(new BaseType(TypeKind.BOOLEAN, stmt.posn))) {
            throw new TypeError("While loop condition must be boolean type (saw "+ stmt.cond.type.typeKind +")", stmt.cond.posn);
        }

        return null;
    }

    @Override
    public TypeDenoter visitUnaryExpr(UnaryExpr expr, Object arg) throws TypeError {
        expr.expr.visit(this, null);
        expr.type = checkUnaryExpr(expr);
        return expr.type;
    }

    @Override
    public TypeDenoter visitBinaryExpr(BinaryExpr expr, Object arg) throws TypeError {
        TypeDenoter lefttype = expr.left.visit(this, null);
        TypeDenoter righttype = expr.right.visit(this, null);
        System.out.println(expr.left.type + "\t" + expr.right.type);
        expr.type = checkBinExpr(expr);
        return expr.type;
    }

    @Override
    public TypeDenoter visitRefExpr(RefExpr expr, Object arg) {
        expr.type = expr.ref.visit(this, null);
        return expr.type;
    }

    @Override
    public TypeDenoter visitIxExpr(IxExpr expr, Object arg) throws TypeError {
        expr.ixExpr.visit(this, null);
        if (expr.ixExpr.type.typeKind != TypeKind.INT) {
            throw new TypeError("Array index is not an integer expression", expr.posn);
        }
        return ((ArrayType) expr.ref.decl.type).eltType;
    }

    @Override
    public TypeDenoter visitCallExpr(CallExpr expr, Object arg) {

        for (Expression e : expr.argList) {
            e.visit(this, null);
        }

        if (! (expr.functionRef.decl instanceof MethodDecl)) {
            throw new TypeError("reference " + expr.functionRef.decl.name + " is not a method", expr.posn);
        }
        MethodDecl call_md = (MethodDecl) expr.functionRef.decl;

        checkArgs(expr.argList, call_md.parameterDeclList, call_md);

        expr.type = expr.functionRef.decl.type;
        return expr.type;
    }

    @Override
    public TypeDenoter visitLiteralExpr(LiteralExpr expr, Object arg) {
        expr.type = expr.lit.visit(this, null);
        return expr.type;
    }

    @Override
    public TypeDenoter visitNewObjectExpr(NewObjectExpr expr, Object arg) {
        expr.type = expr.classtype;
        return expr.type;
    }

    @Override
    public TypeDenoter visitNewArrayExpr(NewArrayExpr expr, Object arg) {
        expr.type = new ArrayType(expr.eltType, expr.posn);
        return expr.type;
    }

    @Override
    public TypeDenoter visitThisRef(ThisRef ref, Object arg) {
        return ref.decl.type;
    }

    @Override
    public TypeDenoter visitIdRef(IdRef ref, Object arg) {
        return ref.decl.type;
    }

    @Override
    public TypeDenoter visitQRef(QualRef ref, Object arg) {
        //ref.id.visit(this, null);
        //System.out.println("type checking qual ref:\t\t" +  ref.id.hashCode());
        return ref.id.decl.type;
    }

    @Override
    public TypeDenoter visitIdentifier(Identifier id, Object arg) {
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
    private TypeDenoter getTypeFromDecl(Declaration d) throws TypeError {
        if (d.type == null) {
            throw new TypeError("declaration" + d.name + " is a class declaration", d.posn);
        } else return d.type;
    }
    */

    private TypeDenoter checkBinExpr(BinaryExpr expr) throws TypeError {

        System.out.println(expr.left.type);
        switch (expr.operator.type) {
            case EqualEqual:
            case NotEqual:

                TypeDenoter lt = expr.left.type;
                TypeDenoter rt = expr.right.type;
                if (! TypeDenoter.equals(expr.left.type, expr.right.type)) {
                    throw new TypeError("Both sides of a equivalence binary expression must be the same type", expr.posn);
                }

                return new BaseType(TypeKind.BOOLEAN, expr.posn);
            case LessEqual:
            case GreaterEqual:
            case LCaret:
            case RCaret:
                if ( expr.left.type.typeKind != TypeKind.INT || expr.right.type.typeKind != TypeKind.INT) {
                    throw new TypeError("Both sides of an relational binary expression must be type Int", expr.posn);
                }
                return new BaseType(TypeKind.BOOLEAN, expr.posn);
            case Star:
            case Plus:
            case Minus:
            case FSlash:
                if ( expr.left.type.typeKind != TypeKind.INT || expr.right.type.typeKind != TypeKind.INT) {
                    throw new TypeError("Both sides of an arithmetic binary expression must be type Int", expr.posn);
                } else {
                    return new BaseType(TypeKind.INT, expr.posn);
                }
            case AmpAmp:
            case BarBar:
                if ( expr.left.type.typeKind != TypeKind.BOOLEAN || expr.right.type.typeKind != TypeKind.BOOLEAN) {
                    throw new TypeError("Both sides of an arithmetic binary expression must be type Int", expr.posn);
                }
                return new BaseType(TypeKind.BOOLEAN, expr.posn);
            default:
                throw new TypeError("unsupported operator type in expression", expr.posn);
        }
    }  

    private TypeDenoter checkUnaryExpr(UnaryExpr expr) throws TypeError {
        switch (expr.operator.type) {
            case Minus:
                if (expr.expr.type.equals(new BaseType(TypeKind.INT, expr.posn))) {
                    return expr.expr.type;
                } else {
                    throw new TypeError("Unary operand must be an integer", expr.posn);
                }
            case Not:
                if (expr.expr.type.equals(new BaseType(TypeKind.BOOLEAN, expr.posn))) {
                    return expr.expr.type;
                } else {
                    throw new TypeError("Unary ! operand must be a boolean", expr.posn);
                }
            default:
                throw new TypeError("Bad unary operator, cannot validate type", expr.posn);
        }
    }

    private void checkArgs(ExprList el, ParameterDeclList pdl, Declaration method_decl) throws TypeError {
        if (el.size() != pdl.size()) {
            throw new TypeError("bad arguments for method " + method_decl.name, "expected " + pdl.size() + " args, saw " + el.size(), method_decl.posn);
        }

        int i = 0;
        int size = el.size();

        while (i < size) {
            Expression e_arg = el.get(i);
            System.out.println(e_arg.type);
            TypeDenoter expr_arg = el.get(i).type;
            TypeDenoter decl_arg = pdl.get(i).type;
            System.out.println(expr_arg + "\t" + decl_arg);
            boolean match = TypeDenoter.equals(expr_arg, decl_arg);

            if (! match) {
                throw new TypeError("mismatched arguments for method " + method_decl.name, method_decl.posn);
            }

            i += 1;
        }
    }

    private void checkReturnType(MethodDecl md) throws TypeError {
        checkReturnType(md.statementList, md.type);
    }

    private void checkReturnType(StatementList sl, TypeDenoter expected) throws TypeError {
        for (Statement s : sl) {
            checkReturnType(s, expected);
        }
    }

    private void checkReturnType(Statement s, TypeDenoter expected) throws TypeError {
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
            TypeDenoter actual_rettype = rs.returnExpr != null ? rs.returnExpr.type : new BaseType(TypeKind.VOID, s.posn);
            if (! actual_rettype.equals(expected)) {
                throw new TypeError("Invalid return type for method", s.posn);
            }
        }
    }

    private void checkReturnPath(StatementList sl) throws TypeError {
        Statement last = sl.getLast();

        if (last instanceof ReturnStmt) return;
        else if (last instanceof IfStmt) {
            IfStmt last_if = (IfStmt) last;

            if (last_if.elseStmt == null) {

            }
        }
    }
}
