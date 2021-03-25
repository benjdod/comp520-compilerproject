package miniJava.ContextualAnalysis;

import miniJava.ErrorReporter;
import miniJava.AbstractSyntaxTrees.*;
import miniJava.AbstractSyntaxTrees.Package;

public class Identification implements Visitor<Object, Object> {

    public IdTable _idtable;
    private ErrorReporter _reporter;

    public Identification(ErrorReporter reporter) {
        _reporter = reporter;
        _idtable = new IdTable(reporter);
    }

    /* Traverses an AST to match all Identifier nodes with a single corresponding Declaration node.
     */
    public void validate(Package tree) {
        try {
            tree.visit(this, null);
        } catch (IdError e) {
            _reporter.report(e);
        }
        return;
    }

    @Override
    public Object visitPackage(Package prog, Object arg) throws IdError {
        _idtable.openScope();

        for (ClassDecl cd : prog.classDeclList) {
            _idtable.insert(cd);
        }

        for (ClassDecl cd : prog.classDeclList) {
            cd.visit(this, null);
        }

        _idtable.closeScope();
        return null;
    }

    @Override
    public Object visitClassDecl(ClassDecl cd, Object arg) throws IdError {

        _idtable.openScope();

        for (FieldDecl f : cd.fieldDeclList) {
            _idtable.insert(f);
        }
        for (MethodDecl m : cd.methodDeclList) {
            _idtable.insert(m);
        }

        // visit Fields and methods
        for (FieldDecl f : cd.fieldDeclList) { f.visit(this, null); }
        for (MethodDecl m : cd.methodDeclList) { m.visit(this, null); }

        _idtable.closeScope();

        return null;
    }

    @Override
    public Object visitFieldDecl(FieldDecl fd, Object arg) throws IdError {
        fd.type.visit(this, null);
        return null;
    }

    @Override
    public Object visitMethodDecl(MethodDecl md, Object arg) throws IdError {
        md.type.visit(this, null);

        _idtable.openScope();
        for (ParameterDecl pd : md.parameterDeclList) {
            _idtable.insert(pd);
        }

        _idtable.openScope();
        for (Statement st : md.statementList) {
            st.visit(this, null);
        }

        _idtable.closeScope();
        _idtable.closeScope();

        return null;
    }

    @Override
    public Object visitParameterDecl(ParameterDecl pd, Object arg) throws IdError {
        pd.type.visit(this, null);
        return null;
    }

    @Override
    public Object visitVarDecl(VarDecl decl, Object arg) throws IdError {
        decl.type.visit(this, null);
        return null;
    }

    @Override
    public Object visitBaseType(BaseType type, Object arg) throws IdError {
        // no visiting to do here.
        return null;
    }

    @Override
    public Object visitClassType(ClassType type, Object arg) throws IdError {
        type.className.visit(this, null);
        return null;
    }

    @Override
    public Object visitArrayType(ArrayType type, Object arg) throws IdError {
        type.eltType.visit(this, null);
        return null;
    }


    /* 
     * STATEMENTS
     */

    @Override
    public Object visitBlockStmt(BlockStmt stmt, Object arg) throws IdError {
        
        _idtable.openScope();
        for (Statement s : stmt.sl) {
            s.visit(this, null);
        }
        _idtable.closeScope();
        return null;
    }

    @Override
    public Object visitVardeclStmt(VarDeclStmt stmt, Object arg) throws IdError {

        // we don't need to open a new scope;
        // if we're here, the table is already in local scope
        // or inside a block

        _idtable.insert(stmt.varDecl);
        stmt.varDecl.visit(this, null);

        return null;
    }

    @Override
    public Object visitAssignStmt(AssignStmt stmt, Object arg) throws IdError {
        stmt.ref.visit(this, null);
        stmt.val.visit(this, null);
        return null;
    }

    @Override
    public Object visitIxAssignStmt(IxAssignStmt stmt, Object arg) throws IdError {
        stmt.ref.visit(this, null);
        stmt.ix.visit(this, null);
        stmt.exp.visit(this, null);
        return null;
    }

    @Override
    public Object visitCallStmt(CallStmt stmt, Object arg) throws IdError {
        stmt.methodRef.visit(this, null);
        for (Expression e : stmt.argList) {
            e.visit(this, null);
        }
        return null;
    }

    @Override
    public Object visitReturnStmt(ReturnStmt stmt, Object arg) throws IdError {
        // FIXME: sticky spot????
        if (stmt.returnExpr != null)
            stmt.returnExpr.visit(this, null);
        return null;
    }

    @Override
    public Object visitIfStmt(IfStmt stmt, Object arg) throws IdError {
        stmt.cond.visit(this, null);
        stmt.thenStmt.visit(this, null);
        if (stmt.elseStmt != null) {
            stmt.elseStmt.visit(this, null);
        }
        return null;
    }

    @Override
    public Object visitWhileStmt(WhileStmt stmt, Object arg) throws IdError {
        stmt.cond.visit(this, null);
        stmt.body.visit(this, null);
        return null;
    }

    @Override
    public Object visitUnaryExpr(UnaryExpr expr, Object arg) throws IdError {
        expr.operator.visit(this, null);
        expr.expr.visit(this, null);
        return null;
    }

    @Override
    public Object visitBinaryExpr(BinaryExpr expr, Object arg) throws IdError {
        expr.operator.visit(this, null);
        expr.left.visit(this, null);
        expr.right.visit(this, null);
        return null;
    }

    @Override
    public Object visitRefExpr(RefExpr expr, Object arg) throws IdError {
        expr.ref.visit(this, null);
        return null;
    }

    @Override
    public Object visitIxExpr(IxExpr expr, Object arg) throws IdError {
        expr.ref.visit(this, null);
        expr.ixExpr.visit(this, null);
        return null;
    }

    @Override
    public Object visitCallExpr(CallExpr expr, Object arg) throws IdError {
        expr.functionRef.visit(this, null);
        for (Expression e : expr.argList) {
            e.visit(this, null);
        }
        return null;
    }

    @Override
    public Object visitLiteralExpr(LiteralExpr expr, Object arg) throws IdError {
        expr.lit.visit(this, null);
        return null;
    }

    @Override
    public Object visitNewObjectExpr(NewObjectExpr expr, Object arg) throws IdError {
        expr.classtype.visit(this, null);
        return null;
    }

    @Override
    public Object visitNewArrayExpr(NewArrayExpr expr, Object arg) throws IdError {
        expr.eltType.visit(this, null);
        expr.sizeExpr.visit(this, null);
        return null;
    }

    @Override
    public Object visitThisRef(ThisRef ref, Object arg) throws IdError {
        return null;
    }

    @Override
    public Object visitIdRef(IdRef ref, Object arg) throws IdError {
        ref.id.visit(this,null);
        return null;
    }

    @Override
    public Object visitQRef(QualRef ref, Object arg) throws IdError {
        ref.ref.visit(this,null);
        ref.id.visit(this,null);
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //
    // TERMINALS
    //
    ///////////////////////////////////////////////////////////////////////////////

    @Override
    public Object visitIdentifier(Identifier id, Object arg) throws IdError {
        System.out.println("visiting Identifier '" + id.spelling + "'");
        id.decl = _idtable.get(id);
        //System.out.println(_idtable.toString());
        return null;
    }

    @Override
    public Object visitOperator(Operator op, Object arg) throws IdError {
        return null;
    }

    @Override
    public Object visitIntLiteral(IntLiteral num, Object arg) throws IdError {
        return null;
    }

    @Override
    public Object visitBooleanLiteral(BooleanLiteral bool, Object arg) throws IdError {
        return null;
    }

    @Override
    public Object visitNullLiteral(NullLiteral expr, Object arg) throws IdError {
        return null;
    }
}
