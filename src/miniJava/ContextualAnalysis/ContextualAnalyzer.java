package miniJava.ContextualAnalysis;

import miniJava.ErrorReporter;
import miniJava.AbstractSyntaxTrees.*;
import miniJava.AbstractSyntaxTrees.Package;
import miniJava.SyntacticAnalyzer.SourcePosition;
import miniJava.SyntacticAnalyzer.Token;
import miniJava.SyntacticAnalyzer.TokenType;

import java.util.Stack;

public class ContextualAnalyzer implements Visitor<Object, TypeDenoter> {

    private IdTable _idtable;
    private Package _tree;
    private ErrorReporter _reporter;
    // private ArrayList<Identifier> _qualrefs;
    private Stack<TypeDenoter[]> _currentargs; 

    /* some current state variables */
    private ClassDecl _cd;
    private MethodDecl _md;
    private VarDecl _vd;

    public ContextualAnalyzer(Package tree, ErrorReporter reporter) {
        _reporter = reporter;
        _idtable = new IdTable(reporter);
        _tree = tree;
        //_qualrefs = new ArrayList<Identifier>();
        _currentargs = new Stack<TypeDenoter[]>();
        validate(_tree);
    }

    /**
     * Traverses the AST to perform identification and type checking.
     * Note that only ID errors will throw; TypeErrors are manually 
     * reported to minimize impact and prevent cascading. 
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
    public TypeDenoter visitPackage(Package prog, Object arg) {

        /* open lowest class scope */
        _idtable.openScope();

        /* insert all class declarations to id table */
        for (ClassDecl cd : prog.classDeclList) {
            _idtable.insert(cd);
        }

        /* make types for all classes */
        for (ClassDecl cd : prog.classDeclList) {
            cd.type = makeClassType(cd);
        }

        for (ClassDecl cd : prog.classDeclList) {
            cd.visit(this, null);
        }

        _idtable.closeScope();
        return null;
    }

    @Override
    public TypeDenoter visitClassDecl(ClassDecl cd, Object arg) {

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

        return cd.type;
    }

    @Override
    public TypeDenoter visitFieldDecl(FieldDecl fd, Object arg) {
        fd.type = fd.type.visit(this, null);
        return fd.type;
    }

    @Override
    public TypeDenoter visitMethodDecl(MethodDecl md, Object arg) {

        _md = md;
        md.type = md.type.visit(this, null);

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

        checkReturnType(md);
        
        if (md.type.typeKind != TypeKind.VOID) {
            checkReturnPath(md);
        } 
        
        return md.type;
    }

    @Override
    public TypeDenoter visitParameterDecl(ParameterDecl pd, Object arg) {
        return pd.type.visit(this,null);
    }

    @Override
    public TypeDenoter visitVarDecl(VarDecl decl, Object arg) {
        return decl.type.visit(this, null);
    }

    @Override
    public TypeDenoter visitBaseType(BaseType type, Object arg) {
        return type;
    }

    @Override
    public TypeDenoter visitClassType(ClassType type, Object arg) {

        if (type.className.spelling.contentEquals("String")) {
            return new BaseType(TypeKind.UNSUPPORTED, type.posn);
        }

        for (ClassDecl cd : _tree.classDeclList) {

            if (type.className.spelling.contentEquals(cd.name)) {
                type.className.visit(this, null);
                return type;
            }
        }
        
        throw new IdError("Invalid constructor", "No class '" + type.className.spelling + "' is present", type.posn);
    }

    @Override
    public TypeDenoter visitArrayType(ArrayType type, Object arg) {
        type.eltType.visit(this, null);
        return type;
    }

    /**
     * ######## STATEMENTS ######## 
     * They don't return types. 
     */

    @Override
    public TypeDenoter visitBlockStmt(BlockStmt stmt, Object arg) {
        _idtable.openScope();
        for (Statement s : stmt.sl) {
            s.visit(this, null);
        }
        _idtable.closeScope();
        return null;
    }

    @Override
    public TypeDenoter visitVardeclStmt(VarDeclStmt stmt, Object arg) {

        _idtable.insert(stmt.varDecl);
        _vd = stmt.varDecl;

        TypeDenoter vdt = stmt.varDecl.visit(this, null);
        TypeDenoter expt = stmt.initExp.visit(this, null);
        
        //System.out.println("assigning " + expt.typeKind + " to " + vdt.typeKind);
        
        if (! vdt.equals(expt)) {
            _reporter.report(new TypeError("Cannot assign an expression of type " + expt.typeKind + " to a declaration of type " + vdt.typeKind, stmt.posn));
            return new BaseType(TypeKind.ERROR, stmt.posn);
        }

        _vd = null;

        return vdt;
    }

    @Override
    public TypeDenoter visitAssignStmt(AssignStmt stmt, Object arg) {
        TypeDenoter vt = stmt.val.visit(this, null);
        TypeDenoter rt = stmt.ref.visit(this, null);
        if (! vt.equals(rt)) {
            _reporter.report(new TypeError("Cannot assign expression of type " + vt.typeKind + " to reference of type " + rt.typeKind, stmt.posn));
            return new BaseType(TypeKind.ERROR, stmt.posn);
        }

        if (stmt.ref instanceof ThisRef) {
            throw new IdError("cannot assign to 'this'", stmt.posn);
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
        /*
        stmt.methodRef.visit(this, null);
        //for (Expression e : stmt.argList) {
        //    e.visit(this, null);
        //}

        // build up argument type list for method resolution
        TypeDenoter[] argtypes = new TypeDenoter[stmt.argList.size()];
        for (int i = 0; i < stmt.argList.size(); i++) {
            argtypes[i] = stmt.argList.get(i).visit(this,null);
        }

        DeclSignature search = new DeclSignature(stmt.methodRef.decl.name, argtypes);

        if (stmt.methodRef instanceof QualRef) {
            QualRef qr = (QualRef) stmt.methodRef;
            ClassDecl cd;

            if (qr.ref.decl instanceof FieldDecl) {
                cd = getClassDeclFromMember((FieldDecl) qr.ref.decl);
            } else {
                cd = getClassDecl(qr.ref.decl);
            }

            //System.out.println("searching class " + cd.name);

            stmt.methodRef.decl = null;

            for (MethodDecl md : cd.methodDeclList) {
                //System.out.println("...for " + md.name);
                if (md.matchSignature(search)) {
                    stmt.methodRef.decl = md;
                    break;
                } else {
                    System.out.println("no match");
                }
            }

            if (stmt.methodRef.decl == null)
                throw new IdError("could not find method '" + search.name + "' with " + search.argtypes.length + " args", stmt.posn);
        } else {
            //System.out.println("entry for method ref " + stmt.methodRef.decl.name + " @ " + stmt.methodRef.posn);
            //System.out.println("search: " + stmt.methodRef.decl.name + " " + argtypes.length);
            stmt.methodRef.decl = _idtable.get(search);
            //System.out.println("got: " + stmt.methodRef.decl);
        }

        
        if (! (stmt.methodRef.decl instanceof MethodDecl)) {
            _reporter.report(new TypeError("Reference to " + stmt.methodRef.decl.name + " is not a method", stmt.posn));
            return new BaseType(TypeKind.ERROR, stmt.posn);
        }
        MethodDecl md = (MethodDecl) stmt.methodRef.decl;
        //checkArgs(stmt, md);    // FIXME: this should be redundant after method resolution.
        return null;
        */

        return checkCall(stmt.methodRef, stmt.argList, stmt.posn);
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

        if (! stmt.cond.type.equals(new BaseType(TypeKind.BOOLEAN, stmt.posn))) {
            _reporter.report(new TypeError("If loop condition must be boolean type (saw "+ stmt.cond.type.typeKind +")", stmt.cond.posn));
            return new BaseType(TypeKind.ERROR, stmt.posn);
        }

        return null;
    }

    @Override
    public TypeDenoter visitWhileStmt(WhileStmt stmt, Object arg) {
        stmt.cond.visit(this, null);

        if (stmt.body instanceof VarDeclStmt) {
            throw new IdError("variable declaration cannot be the only statement in a while loop", stmt.body.posn);
        }

        if (! stmt.cond.type.equals(new BaseType(TypeKind.BOOLEAN, stmt.posn))) {
            _reporter.report(new TypeError("While loop condition must be boolean type (saw "+ stmt.cond.type.typeKind +")", stmt.cond.posn));
        }

        stmt.body.visit(this, null);

        
        return null;
    }

    @Override
    public TypeDenoter visitUnaryExpr(UnaryExpr expr, Object arg) {
        expr.operator.visit(this, null);
        expr.expr.visit(this, null);
        expr.type = checkUnaryExpr(expr);
        return expr.type;
    }

    @Override
    public TypeDenoter visitBinaryExpr(BinaryExpr expr, Object arg) {
        expr.operator.visit(this, null);
        TypeDenoter lefttype = expr.left.visit(this, null);
        TypeDenoter righttype = expr.right.visit(this, null);
        expr.left.type = lefttype;
        expr.right.type = righttype;
        //System.out.println("binary type kinds: " + expr.left.type.typeKind + "\t" + expr.right.type.typeKind);
        expr.type = checkBinExpr(expr);
        //System.out.println("returning type: " + expr.type.typeKind);
        return expr.type;
    }

    @Override
    public TypeDenoter visitRefExpr(RefExpr expr, Object arg) {

        // FIXME: watch it!!
        expr.ref.visit(this, null);
        if (expr.ref.decl instanceof MethodDecl) {
            throw new IdError("cannot reference a method without calling it", expr.posn);
        }
        expr.type = expr.ref.decl.type;
        expr.type = expr.ref.visit(this, null);
        return expr.type;
    }

    @Override
    public TypeDenoter visitIxExpr(IxExpr expr, Object arg) {
        expr.ref.visit(this, null);
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
        expr.type = ((ArrayType) expr.ref.decl.type).eltType;
        return expr.type;
    }

    @Override
    public TypeDenoter visitCallExpr(CallExpr expr, Object arg) {
        /*
        expr.functionRef.visit(this, null);

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
        */
        return checkCall(expr.functionRef, expr.argList, expr.posn);
    }

    @Override
    public TypeDenoter visitLiteralExpr(LiteralExpr expr, Object arg) {
        expr.type = expr.lit.visit(this, null);
        return expr.type;
    }

    @Override
    public TypeDenoter visitNewObjectExpr(NewObjectExpr expr, Object arg) {
        expr.type = expr.classtype.visit(this, null);
        return expr.type;
    }

    @Override
    public TypeDenoter visitNewArrayExpr(NewArrayExpr expr, Object arg) {
        expr.eltType.visit(this, null);
        expr.sizeExpr.visit(this, null);
        expr.type = new ArrayType(expr.eltType, expr.posn);
        return expr.type;
    }

    @Override
    public TypeDenoter visitThisRef(ThisRef ref, Object arg) {
        if (_md.isStatic) {
            throw new IdError("cannot reference an instance in a static context", ref.posn);
        }
        ref.decl = _cd;
        return ref.decl.type;

    }

    @Override
    public TypeDenoter visitIdRef(IdRef ref, Object arg) {
        /*
        if (_currentargs.size() > 0) {
            // we're checking a method reference, so all resolution
            // will be done later and we don't need type information, so 
            // just return nothing
            ref.decl = ref.id.decl;
            return null;
        }  */

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
        ref.decl = unfoldQualRef(ref);

        return ref.id.decl.type;
    }

    @Override
    public TypeDenoter visitIdentifier(Identifier id, Object arg) {
        System.out.println("visiting identifier " + id.spelling);
        if (_currentargs.size() > 0) {
            id.decl = _idtable.get(id.spelling, _currentargs.peek());
        } else {
            id.decl = _idtable.get(id);
        }
        return id.decl.type;
    }

    @Override
    public TypeDenoter visitOperator(Operator op, Object arg) {
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

    /**
     * ################# IDENTIFICATION HELPER METHODS ################### 
     */


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

    private ClassDecl getClassDeclFromDecl(Declaration d) {
        if (d instanceof ClassDecl) return (ClassDecl) d;

        if (! (d.type instanceof ClassType)) throw new IdError("Declaration is not a class type",d.posn);

        ClassType ct = (ClassType) d.type;

        return getClassDecl(ct.className);
    }

    public TypeDenoter checkCall(Reference methodRef, ExprList argList, SourcePosition posn) {

        TypeDenoter[] argtypes = new TypeDenoter[argList.size()];
        for (int i = 0; i < argList.size(); i++) {
            argtypes[i] = argList.get(i).visit(this,null);
        }

        DeclSignature search;

        if (methodRef instanceof IdRef) {
            _currentargs.add(argtypes);
            methodRef.visit(this, null);
            _currentargs.pop();

            search = new DeclSignature(((IdRef) methodRef).id.spelling, argtypes);
        } else {
            methodRef.visit(this, null);
            search = new DeclSignature(methodRef.decl.name, argtypes);
        }

        /*
        for (Expression e : stmt.argList) {
            e.visit(this, null);
        }*/

        // build up argument type list for method resolution
        

        if (methodRef instanceof QualRef) {
            QualRef qr = (QualRef) methodRef;
            ClassDecl cd;

            System.out.println(qr.ref.decl.type);

            if (qr.ref.decl instanceof ClassDecl) {
                cd = getClassDecl(qr.ref.decl);
            } else {
                cd = getClassDeclFromDecl(qr.ref.decl);
            }

            //System.out.println("searching class " + cd.name + " for " + search.name);

            methodRef.decl = null;

            for (MethodDecl md : cd.methodDeclList) {
                //System.out.println( "..." + md.name + " ?");
                if (md.matchSignature(search)) {
                    methodRef.decl = md;
                    //System.out.println("found!");
                    break;
                } else {
                    //System.out.println("no match");
                }
            }

            if (methodRef.decl == null)
                throw new IdError("could not find method " + search.toString(), posn);
        } else {
            methodRef.decl = _idtable.get(search, methodRef.posn);
        }

        
        if (! (methodRef.decl instanceof MethodDecl)) {
            _reporter.report(new TypeError("Reference to " + methodRef.decl.name + " is not a method", posn));
            return new BaseType(TypeKind.ERROR, posn);
        }
        MethodDecl md = (MethodDecl) methodRef.decl;

        return md.type;
    }


    /**
     * ################# TYPE CHECKING HELPER METHODS ################### 
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
            case Modulo:
            System.out.println(expr.left.type + "\t" + expr.right.type);
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
