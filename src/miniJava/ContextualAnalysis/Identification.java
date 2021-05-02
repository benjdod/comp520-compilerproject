package miniJava.ContextualAnalysis;

import miniJava.ErrorReporter;
import miniJava.AbstractSyntaxTrees.*;
import miniJava.AbstractSyntaxTrees.Package;
import miniJava.SyntacticAnalyzer.SourcePosition;
import miniJava.SyntacticAnalyzer.Token;
import miniJava.SyntacticAnalyzer.TokenType;

import java.util.ArrayList;

public class Identification implements Visitor<Object, Object> {

    private IdTable _idtable;
    private Package _tree;
    private ErrorReporter _reporter;
    private ArrayList<Identifier> _qualrefs;

    /* some current state variables */
    private ClassDecl _cd;
    private MethodDecl _md;
    private VarDecl _vd;

    public Identification(Package tree, ErrorReporter reporter) {
        _reporter = reporter;
        _idtable = new IdTable(reporter);
        _tree = tree;
        _qualrefs = new ArrayList<Identifier>();
        validate(_tree);
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
        _cd = cd;

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
        _md = md;
        md.type.visit(this, null);

        /*
        if ((_idtable.contains(md.name))) {
            throw new IdError("There is already a declaration '" + _idtable.get(md.name).name + "'", md.posn);
        }*/

        _idtable.openScope();
        for (ParameterDecl pd : md.parameterDeclList) {
            _idtable.insert(pd);
            pd.visit(this, null);
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
        //System.out.println("visited base type: " + type.typeKind + " @ " + type.posn);
        return null;
    }

    @Override
    public Object visitClassType(ClassType type, Object arg) throws IdError {

        //System.out.println("visited class type: '" + type.className.spelling +"' @ " + type.posn);

        for (ClassDecl cd : _tree.classDeclList) {

            if (type.className.spelling.contentEquals(cd.name)) {
                type.className.visit(this, null);
                return null;
            }
        }
        
        throw new IdError("Invalid constructor", "No class '" + type.className.spelling + "' is present", type.posn);
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
        _vd = stmt.varDecl;
        stmt.varDecl.visit(this, null);
        stmt.initExp.visit(this, null);
        _vd = null;

        return null;
    }

    @Override
    public Object visitAssignStmt(AssignStmt stmt, Object arg) throws IdError {
        stmt.ref.visit(this, null);
        stmt.val.visit(this, null);

        if (stmt.ref instanceof ThisRef) {
            throw new IdError("cannot assign to 'this'", stmt.posn);
        }

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
        if (stmt.returnExpr != null)
            stmt.returnExpr.visit(this, null);
        return null;
    }

    @Override
    public Object visitIfStmt(IfStmt stmt, Object arg) throws IdError {
        stmt.cond.visit(this, null);
        if (stmt.thenStmt instanceof VarDeclStmt) {
            throw new IdError("variable declaration cannot be the only statement in an if statement", stmt.thenStmt.posn);
        }
        stmt.thenStmt.visit(this, null);
        if (stmt.elseStmt != null) {
            if (stmt.elseStmt instanceof VarDeclStmt) {
                throw new IdError("variable declaration cannot be the only statement in an else statement", stmt.thenStmt.posn);
            }
            stmt.elseStmt.visit(this, null);
        }
        return null;
    }

    @Override
    public Object visitWhileStmt(WhileStmt stmt, Object arg) throws IdError {
        stmt.cond.visit(this, null);

        if (stmt.body instanceof VarDeclStmt) {
            throw new IdError("variable declaration cannot be the only statement in a while loop", stmt.body.posn);
        }

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
        if (expr.ref.decl instanceof MethodDecl) {
            throw new IdError("cannot reference a method without calling it", expr.posn);
        }
        expr.type = expr.ref.decl.type;
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
        /*
        if (expr.lit.type == TokenType.True || expr.lit.type == TokenType.False) {
            expr.type = new BaseType(TypeKind.BOOLEAN, expr.posn);
        } else if (expr.lit.type == TokenType.Num) {
            expr.type = new BaseType(TypeKind.INT, expr.posn);
        }*/
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
        //System.out.println("this ref");
        if (_md.isStatic) {
            throw new IdError("cannot reference an instance in a static context", ref.posn);
        }
        ref.decl = _cd;
        return null;
    }

    @Override
    public Object visitIdRef(IdRef ref, Object arg) throws IdError {
        ref.id.visit(this,null);
        ref.decl = ref.id.decl;

        //System.out.println("id decl: " + ref.decl.posn);

        // if we are in the initial expression for a 
        // variable declaration, we have to check that
        // we're not referring to the newly declared variable
        if (_vd != null) {
            if (ref.id.spelling.contentEquals(_vd.name)) {
                throw new IdError("cannot refer to a newly declared variable in its declaration", ref.posn);
            }
        }

        if (ref.decl instanceof MemberDecl) {
            MemberDecl md = (MemberDecl) ref.decl;

            if (_md.isStatic) {
                if (! md.isStatic) {
                    throw new IdError("Cannot reference non-static field '" + ref.decl.name + "' in static method '" + _md.name + "'", ref.posn);
                }
            }
        }

        return null;
    }

    @Override
    public Object visitQRef(QualRef ref, Object arg) throws IdError {
        //System.out.println("identification for qref: " + ref.id.spelling);
        ref.decl = unfoldQualRef(ref);
        //ref.ref.visit(this,null);
        //ref.id.visit(this,null);
        //System.out.println(_idtable.toString());
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //
    // TERMINALS
    //
    ///////////////////////////////////////////////////////////////////////////////

    @Override
    public Object visitIdentifier(Identifier id, Object arg) throws IdError {
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



    /* 
     *
     * 
     *  OTHER STUFF
     * 
     * 
     * */



    /*
    public void unfoldQualRef(QualRef qr) throws IdError {

        //System.out.println("unfolding qual ref");

        QualRef current = qr;
        
        Reference base = qr;

        ArrayList<Identifier> id_arr = new ArrayList<Identifier>();
        ArrayList<QualRef> ref_arr = new ArrayList<QualRef>();

        id_arr.add(0, qr.id);

        while (current.ref instanceof QualRef) {
            current = (QualRef) current.ref;
            ref_arr.add(0, current);
            id_arr.add(0, current.id);
        }
        
        base = current.ref;
        boolean isStatic = false;
                
        // if the start is a 'this', set the base.decl to the current class
        if (base instanceof ThisRef) {
            //System.out.println("THIS REF + " + id_arr.toString());
            base.decl = _cd;
            if (_md.isStatic) {
                //System.out.println("method " + _md.name + " is static");
                isStatic = true;
                throw new IdError("cannot reference this in a static context", base.posn);
            }
        } else if (base instanceof IdRef) {

            IdRef base_idref = (IdRef) base;

            Declaration d = _idtable.get(base_idref.id);

            base.decl = d;

            if (d instanceof ClassDecl) {

                // if the qual ref is referencing a field starting in the class level,
                // the field is necessarily static.
                isStatic = true;
                //System.out.println("resolving static field in class " + d.name);
                
            } else {
                //System.out.println("Ident " + base.decl.name + " is not static");
                ;
            }
            //System.out.println("IDENT REF + " + id_arr.toString());
        }

        int i;
        MemberDecl md;
        Declaration decl = base.decl;
        Identifier id = id_arr.get(0);
        

        md = resolveClassField(decl, id_arr.get(0), isStatic);
        id.decl = md;

        id_arr.remove(0);

        // loop over the rest of the fields, which are necessarily
        // non-static
        for (i = 0; i < id_arr.size(); i++) {


            decl = getClassDeclFromMember(md);

            id = id_arr.get(i);
            

            //System.out.println("-- " + id_arr.get(i).spelling);
            md = resolveClassField(decl, id, false);
            id.decl = md;
            if (i < ref_arr.size()) {
                ref_arr.get(i).decl = md;
            }
            //System.out.println("found member decl " + md.name);
        }

        qr.decl = qr.id.decl;
    } */

    private Declaration unfoldQualRef(QualRef qr) {
        Declaration d;

        if (qr.ref instanceof QualRef) {
            d = unfoldQualRef((QualRef) qr.ref);
        } else if (qr.ref instanceof IdRef) {
            // we've hit the bottom

            IdRef ref = (IdRef) qr.ref;

            d = _idtable.get(ref.id);

            ref.id.decl = d;
            ref.decl = d;

        } else if (qr.ref instanceof ThisRef) {
            d = _cd;
            qr.ref.decl = _cd;
        } else {
            throw new IdError("unresolvable reference type", "type is " + qr.ref.toString(), qr.posn);
        }

        Identifier id = qr.id;

        if (d instanceof ClassDecl) {

            ClassDecl cd  = (ClassDecl) d;

            boolean staticonly = _md != null && _md.isStatic;

            System.out.println("referencing in static environment? " + staticonly);

            qr.decl = resolveClassField(cd, id, staticonly);
            //qr.id.decl = qr.decl;
            //return qr.decl;

        } else if (d.type instanceof ArrayType && id.spelling.contentEquals("length")) {
            SourcePosition nopos = new SourcePosition(0,0);
            qr.decl = new FieldDecl(true,true,new BaseType(TypeKind.INT,nopos),"length",nopos);
        } else {
            ClassDecl cd = getClassDecl((d.type));
            qr.decl = resolveClassField(cd, id, false);
        }

        qr.id.decl = qr.decl;
        return qr.decl;

    }

    private MemberDecl resolveClassField(Declaration d, Identifier id, boolean staticOnly) throws IdError {
        return resolveClassField(d, id, _cd, staticOnly);
    }

    private MemberDecl resolveClassField(Declaration d, Identifier id, ClassDecl currentclass, boolean staticOnly) throws IdError {

        // resolve Class declaration from the name of the provided declaration
        ClassDecl cd;

        /*
        if (d instanceof MemberDecl) {
            cd = getClassDeclFromInst(d);
        } else {
            System.out.println("agh");

            cd = getClassDecl(d);
        }*/
        
        //System.out.println("resolving field " + id.spelling + " for decl " + d.type);
        
        if (d instanceof ClassDecl) {
            cd = getClassDecl(d);
        } else if (d.type instanceof ArrayType && id.spelling.contentEquals("length")) {
        	SourcePosition nopos = new SourcePosition(0,0);
        	return new FieldDecl(true,true,new BaseType(TypeKind.INT,nopos),"length",nopos);
        } else {
            cd = getClassDeclFromInst(d);
        }

        //System.out.println(currentclass == cd);

        for (FieldDecl fd : cd.fieldDeclList) {
            //System.out.println("checking field decl " + fd.name);
            if (fd.name.contentEquals(id.spelling)) {
                //System.out.println(id.spelling + " = " + fd.name);
                if (fd.isPrivate && cd != currentclass) {
                    throw new IdError("Cannot access private field in class '" + cd.name + "'", id.posn);
                } else if (staticOnly && (!fd.isStatic)) {
                    throw new IdError("Cannot access non-static field '" + fd.name + "' in class '" + cd.name +"' using a static reference" , id.posn);
                } else {
                    return fd;
                }
            }
        }
        for (MethodDecl md : cd.methodDeclList) {
            if (md.name.contentEquals(id.spelling)) {
                if (md.isPrivate && cd != currentclass) {
                    throw new IdError("Cannot access private method in class", id.posn);
                } else if (staticOnly && (!md.isStatic)) {
                    throw new IdError("Cannot access non-static method '" + md.name + "' in class '" + cd.name +"' using a static reference" , id.posn);
                } else {
                    return md;
                }
            }
        }

        throw new IdError("No member '" + id.spelling + "' exists in class '" + cd.name + "'", id.posn);
    }

    private ClassDecl getClassDecl(TypeDenoter td) throws IdError {
        if (td instanceof ClassType) {
            ClassType ct = (ClassType) td;

            return getClassDecl(ct.className);
        } else {
            throw new IdError("Not a class type, no related class", td.posn);
        }
    }

    private ClassDecl getClassDeclFromMember(MemberDecl md) throws IdError {
        if (md instanceof MethodDecl) {
            throw new IdError("member " + md.name + " is a method",  md.posn);
        } else if (md.type.typeKind == TypeKind.CLASS) {
            ClassType ct = (ClassType) md.type;
            
            return getClassDecl(ct.className);
        } else {
            throw new IdError("No fields for member " + md.name, md.name + " is not a class", md.posn);
        }
    }

    private ClassDecl getClassDeclFromInst(Declaration inst) throws TypeError {
        if (inst.type.typeKind == TypeKind.CLASS) {
            ClassType ct = (ClassType) inst.type;
            
            return getClassDecl(ct.className);
        } else {
            throw new IdError("No fields for instance " + inst.name, inst.name + " is not a class", inst.posn);
        }
    }

    private ClassDecl getClassDecl(Declaration d) throws IdError { 
        if (d instanceof ClassDecl) return (ClassDecl) d;
        return getClassDecl(d.name, d.posn);
    }

    private ClassDecl getClassDecl(Identifier id) throws IdError {
        return getClassDecl(id.spelling, id.posn);
    }

    private ClassDecl getClassDecl(String s, SourcePosition posn) throws IdError {
        for (ClassDecl cd : _tree.classDeclList) {
            // sketchy
            if (s.contentEquals(cd.name)) {
                return cd;
            }
        }
        throw new IdError("no class '" + s + "' in program", posn);
    }
}
