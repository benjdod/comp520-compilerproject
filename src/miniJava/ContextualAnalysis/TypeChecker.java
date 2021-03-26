package miniJava.ContextualAnalysis;

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
    }

    @Override
    public TypeDenoter visitPackage(Package prog, Object arg) {
        for (ClassDecl cd : prog.classDeclList) {
            cd.visit(this, null);
        }
        return null;
    }

    @Override
    public TypeDenoter visitClassDecl(ClassDecl cd, Object arg) {
        for (FieldDecl fd : cd.fieldDeclList) {
            fd.visit(this, null);
        }
        for (MethodDecl md : cd.methodDeclList) {
            md.visit(this, null);
        }
        return null;
    }

    @Override
    public TypeDenoter visitFieldDecl(FieldDecl fd, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitMethodDecl(MethodDecl md, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitParameterDecl(ParameterDecl pd, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitVarDecl(VarDecl decl, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitBaseType(BaseType type, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitClassType(ClassType type, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitArrayType(ArrayType type, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitBlockStmt(BlockStmt stmt, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitVardeclStmt(VarDeclStmt stmt, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitAssignStmt(AssignStmt stmt, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitIxAssignStmt(IxAssignStmt stmt, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitCallStmt(CallStmt stmt, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitReturnStmt(ReturnStmt stmt, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitIfStmt(IfStmt stmt, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitWhileStmt(WhileStmt stmt, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitUnaryExpr(UnaryExpr expr, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitBinaryExpr(BinaryExpr expr, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitRefExpr(RefExpr expr, Object arg) {
        return expr.ref.visit(this, null);
    }

    @Override
    public TypeDenoter visitIxExpr(IxExpr expr, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitCallExpr(CallExpr expr, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitLiteralExpr(LiteralExpr expr, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitNewObjectExpr(NewObjectExpr expr, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitNewArrayExpr(NewArrayExpr expr, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitThisRef(ThisRef ref, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitIdRef(IdRef ref, Object arg) {
        // TODO Auto-generated method stub
        return ref.decl.type;
    }

    @Override
    public TypeDenoter visitQRef(QualRef ref, Object arg) {
        //ref.id.visit(this, null);
        return ref.id.decl.type;
    }

    @Override
    public TypeDenoter visitIdentifier(Identifier id, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitOperator(Operator op, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitIntLiteral(IntLiteral num, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitBooleanLiteral(BooleanLiteral bool, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TypeDenoter visitNullLiteral(NullLiteral expr, Object arg) {
        // TODO Auto-generated method stub
        return null;
    }

    public TypeDenoter getTypeFromDecl(Declaration d) throws TypeError {
        if (d.type == null) {
            throw new TypeError("declaration" + d.name + " is a class declaration", d.posn);
        } else return d.type;
    }
    
}
