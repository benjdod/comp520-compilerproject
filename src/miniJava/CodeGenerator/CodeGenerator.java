package miniJava.CodeGenerator;

import java.util.Stack;
import miniJava.ErrorReporter;
import miniJava.AbstractSyntaxTrees.*;
import miniJava.AbstractSyntaxTrees.Package;
import miniJava.SyntacticAnalyzer.SourcePosition;
import miniJava.SyntacticAnalyzer.Token;
import miniJava.SyntacticAnalyzer.TokenType;
import mJAM.*;
import mJAM.Machine.*;

public class CodeGenerator implements Visitor<Object, Object> {
	
	private MethodDecl _main_method;
	private int _patch_main;
	private Package _tree;
	private ErrorReporter _reporter;
	private int _static_ptr;
	private int _inst_ptr;
	private int _local_ptr;
	
	private ClassDecl _cd;
	private MethodDecl _md;
	private ExprList _arglist;
	private boolean _method_return;
	private int _vardecl_count;
	private ClassType _strtype;
	private int _bin_depth;
	private BlockPatcher _shortcircuitpatches;
	private TokenType _lastop;

	private Stack<Integer> _argsizestack;
	private Stack<BlockPatcher> _breakpatches;
	private Stack<TokenType> _binopstack;
	
	private static final int PATCHME = -1;
	
	private final SourcePosition NOPOS = new SourcePosition(0,0);
	
	public CodeGenerator(Package tree, ErrorReporter reporter) {
		_tree = tree;
		_reporter = reporter;
		_main_method = null;
		_lastop = null;
		_argsizestack = new Stack<Integer>();
		_breakpatches = new Stack<BlockPatcher>();
		_binopstack = new Stack<TokenType>();
		Token strtoken = new Token(TokenType.Ident, SourcePosition.NOPOS, "String");
		_strtype = new ClassType(new Identifier(strtoken), SourcePosition.NOPOS);
		_bin_depth = 0;

		_shortcircuitpatches = null;
		
		generate();
	}
	
	private void generate() {
		try {
			visitPackage(_tree,null);
		} catch (CompilationError e) {
			_reporter.report(e);
		}
		return;
	}
	
	private void fillGlobalEntities() {
		
		_static_ptr = 0;
		
		for (ClassDecl cd : _tree.classDeclList) {
			
			_inst_ptr = 0;
			
			cd.entity = new UnknownValue(Reg.SB, _static_ptr);
			cd.entity.size = 0;
			
			for (FieldDecl fd : cd.fieldDeclList) {
				if (fd.isStatic) {
					
					Machine.emit(Op.PUSH,1);
					fd.entity = new UnknownValue(Reg.SB, _static_ptr++);
				} else {
					cd.entity.size++;
					fd.entity = new UnknownValue(Reg.OB, _inst_ptr++);
				}
			}
			
			for (MethodDecl md : cd.methodDeclList) {
				md.entity = new MethodEntity(Reg.CB, PATCHME);
			}
		}
	}

	@Override
	public Object visitPackage(Package prog, Object arg) throws CompilationError {
		
		_main_method = null;
		
		// manifest all methods and fields in the program
		fillGlobalEntities();


		// load arguments for main and insert a call statement for it
		Machine.emit(Op.LOADL,0);
		Machine.emit(Prim.newarr);
		int patch_callmain = Machine.nextInstrAddr();
		Machine.emit(Op.CALL, Reg.CB, PATCHME);	// PATCHME main
		Machine.emit(Op.HALT);
		
		// generate code for each class 
		for (ClassDecl cd : prog.classDeclList) {
			cd.visit(this, null);
		}

		// is there a main method?
		if (_main_method == null) {
			throw new CompilationError("no main method present in program", NOPOS);
		}
		
		// patch in call to main
		Machine.patch(patch_callmain, _main_method.entity.address.offset);
		
		return null;
	}

	@Override
	public Object visitClassDecl(ClassDecl cd, Object arg) throws CompilationError {
		
		_cd = cd;
		
		//cd.entity = new UnknownValue(Reg.SB, _static_ptr);
		//cd.entity.size = 0;
		
		for (FieldDecl fd : cd.fieldDeclList) {
			fd.visit(this, null);
		}
		
		for (MethodDecl md : cd.methodDeclList) {
			md.visit(this, null);
		}
		
		return null;
	}

	@Override
	public Object visitFieldDecl(FieldDecl fd, Object arg) {
		
		if (fd.isStatic) {
			
			// push space onto the stack for the field
			//Machine.emit(Op.PUSH,1);
			//fd.entity = new UnknownValue(Reg.SB, _static_ptr++);
		} else {
			
			// increment the instance size by 1
			//_cd.entity.size += 1;
			//fd.entity = new UnknownValue(Reg.OB, _inst_ptr++);
		}
		
		return null;
	}

	@Override
	public Object visitMethodDecl(MethodDecl md, Object arg) {
		
		_md = md;
		_method_return = false;
		_argsizestack.push(md.parameterDeclList.size());
				
		// is this the main method?
		if (md.name.contentEquals("main") && 
			md.isStatic &&
			! md.isPrivate &&
			md.type.typeKind == TypeKind.VOID &&
			md.parameterDeclList.size() == 1) 
		{
			ParameterDecl pd = md.parameterDeclList.get(0);
			if (pd.type.typeKind == TypeKind.ARRAY) {
				// FIXME: generalize this function
				ArrayType arg0 = (ArrayType) pd.type;
				if (arg0.eltType.typeKind == TypeKind.CLASS) {
					ClassType arg0_class = (ClassType) arg0.eltType;
					
					if (arg0_class.className.spelling.contentEquals("String")) {
						
						if (_main_method != null) {
							
							// this will not be executed, duplicate methods 
							// will throw an IdError earlier in compilation
							throw new CompilationError(
									"duplicate main method not allowed",
									"first declaration was at " + _main_method.posn.toString(), 
									_main_method.posn
								);
						} else {
							_main_method = md;
						}
					}
					
				}
			}
		}
		
		// add entity with address to the declaration
		md.entity.address.offset = Machine.nextInstrAddr();
	
		MethodEntity me = (MethodEntity) md.entity;
		
		for (Integer i : me.callers) {
			Machine.patch(i, md.entity.address.offset);
		}
		me.ismade = true;
		
		//md.address = new Address(Reg.CB, Machine.nextInstrAddr());
		
		int num_parameters = md.parameterDeclList.size();
		
		// point the entity descriptors to the addresses behind LB
		for (int i = 0 - num_parameters; i < 0; i++) {
			md.parameterDeclList.get(i + num_parameters).entity = new UnknownValue(Reg.LB, i);
		}
		
		// initialize local offset to the first free
		// frame address (OB,DL,RA,__)
		_local_ptr = 3;
		
		
		// if the method is provided by the environment, we patch it here and exit
		if (emitPatch(md.patchkey)) return null;
		
		
		// otherwise, visit all the statements
		for (Statement s : md.statementList) {
			s.visit(this, null);
		}
		
		// insert a return statement if none was present
		if (md.type.typeKind == TypeKind.VOID && ! _method_return) {
			Machine.emit(Op.RETURN,0,0,md.parameterDeclList.size());
		}
		
		_argsizestack.pop();
		
		return null;
	}

	@Override
	public Object visitParameterDecl(ParameterDecl pd, Object arg) {
		
		// we don't use this, parameters are initialized by visiting the 
		// respective expressions
		
		return null;
	}

	@Override
	public Object visitVarDecl(VarDecl decl, Object arg) {
		
		Machine.emit(Op.PUSH,1);
		decl.entity = new UnknownValue(Reg.LB,_local_ptr++);
		
		return null;
	}

	@Override
	public Object visitBaseType(BaseType type, Object arg) {
		return null;
	}

	@Override
	public Object visitClassType(ClassType type, Object arg) {
		return null;
	}

	@Override
	public Object visitArrayType(ArrayType type, Object arg) {
		return null;
	}

	@Override
	public Object visitBlockStmt(BlockStmt stmt, Object arg) {
		_vardecl_count = 0;
		for (Statement s : stmt.sl) {
			
			if (s instanceof VarDeclStmt) {
				_vardecl_count++;
			}
			
			s.visit(this, null);
		}
		
		if (_vardecl_count > 0) {
			_local_ptr -= _vardecl_count;
			Machine.emit(Op.POP,_vardecl_count);
		}
		
		return null;
	}

	@Override
	public Object visitVardeclStmt(VarDeclStmt stmt, Object arg) {
		
		stmt.varDecl.visit(this, null);
		stmt.initExp.visit(this, null);
				
		emitOpAddr(Op.STORE,stmt.varDecl.entity.address);
		return null;
	}
	
	@Override
	public Object visitAssignStmt(AssignStmt stmt, Object arg) {
		
		boolean is_qualref = stmt.ref instanceof QualRef;
		
		if (is_qualref) {
			
			QualRef qr = (QualRef) stmt.ref;
			if (qr.id.spelling.contentEquals("length")) {
				if (qr.ref.decl.type instanceof ArrayType) {
					throw new CompilationError("cannot assign to an array's length field", qr.posn);
				}
			}
			
			//System.out.println(stmt.ref.decl.name + ": " + target);
			
			qr.ref.visit(this, null);
			//System.out.println("assn to qual addr " + stmt.ref.decl.name + ": " + a);
			//Machine.emit(Op.LOAD,a.reg, a.offset);
			Machine.emit(Op.LOADL,qr.id.decl.entity.address.offset);
			
		}
		
		stmt.val.visit(this, null);

		Address target = stmt.ref.decl.entity.address;
		boolean is_instf = stmt.ref.decl.entity.address.reg != Reg.SB;
		
		if (is_qualref && is_instf) {
			Machine.emit(Prim.fieldupd);
		} else {
			emitOpAddr(Op.STORE, target);
		}
		return null;
	}

	@Override
	public Object visitIxAssignStmt(IxAssignStmt stmt, Object arg) {
		stmt.ref.visit(this, null);
		stmt.ix.visit(this, null);
		stmt.exp.visit(this, null);
		Machine.emit(Prim.arrayupd);
		return null;
	}

	@Override
	public Object visitCallStmt(CallStmt stmt, Object arg) {
		
		// visit arg expressions and leave them on the stack in order
		for (int i = 0; i < stmt.argList.size(); i++) {
			stmt.argList.get(i).visit(this, null);
		}
		
		
				
		boolean is_static = ((MethodDecl) stmt.methodRef.decl).isStatic;
		
		
				
		MethodEntity me;
		me = (MethodEntity) stmt.methodRef.decl.entity;
		boolean patch = me.address.offset == PATCHME;
		
		// if there's a patch for this method, we can short circuit the whole
		// discovery phase and go straight to the patched code segment.
		if (patchExists(((MethodDecl) stmt.methodRef.decl).patchkey)) {
			Machine.emit(Op.CALL,Reg.CB,me.address.offset);
			if (stmt.methodRef.decl.type.typeKind != TypeKind.VOID) {
				Machine.emit(Op.POP,1);
			}
			return null;
		}
		
		if (is_static) {
			if (patch)	me.callers.add(Machine.nextInstrAddr());
			Machine.emit(Op.CALL,Reg.CB,me.address.offset);
		} else {
			
			Address ob_address;
			
			if (stmt.methodRef instanceof QualRef) {
				/*
				((QualRef) stmt.methodRef).ref.visit(this, null);
				ob_address = ((QualRef) stmt.methodRef).ref.decl.entity.address;
				System.out.println("call statement setting OB: " + ob_address.toString());
				Machine.emit(Op.LOAD, ob_address.reg, ob_address.offset);
				QualRef funcqref = (QualRef) expr.functionRef;
				*/
				//System.out.println("call expr object ref: " + ((QualRef) funcqref.ref).id.decl.name);
				((QualRef) stmt.methodRef).ref.visit(this, null);
				//ob_address = ((QualRef) expr.functionRef).ref.decl.entity.address;
				//System.out.println("call statement setting OB: " + ob_address.toString());
				//Machine.emit(Op.LOAD, ob_address.reg, ob_address.offset);
				//Machine.emit(Op.STOREI,Reg.OB,0);
			} else {
				// the OB is already set, so push it onto stack
				Machine.emit(Op.LOADA,Reg.OB,0);
			}
					
			if (patch) me.callers.add(Machine.nextInstrAddr());
			Machine.emit(Op.CALLI,Reg.CB,me.address.offset);
		}
		
		if (stmt.methodRef.decl.type.typeKind != TypeKind.VOID) {
			Machine.emit(Op.POP,1);
		}
						
		return null;
	}

	@Override
	public Object visitReturnStmt(ReturnStmt stmt, Object arg) {
		
		_method_return = true;
		
		if (stmt.returnExpr != null) {
			stmt.returnExpr.visit(this, null);
			Machine.emit(Op.RETURN,1,0,_argsizestack.peek());
			//System.out.println("args for " +_md.name + ": " + _md.parameterDeclList.size());
		} else {
			Machine.emit(Op.RETURN,0,0,_argsizestack.peek());
		}
		
		return null;
	}

	@Override
	public Object visitIfStmt(IfStmt stmt, Object arg) {
				
		int code_after,jump_to;
		int patch_if, patch_endif;
		
		stmt.cond.visit(this, null);
		patch_if = Machine.nextInstrAddr();
		Machine.emit(Op.JUMPIF,0,Reg.CB,PATCHME); 	// PATCHME cond_unmet
		stmt.thenStmt.visit(this, null);
		
		if (stmt.elseStmt != null) {
			patch_endif = Machine.nextInstrAddr();
			Machine.emit(Op.JUMP,Reg.CB,PATCHME); 						// PATCHME after
			jump_to = Machine.nextInstrAddr();
			stmt.elseStmt.visit(this, null);
			Machine.patch(patch_endif, Machine.nextInstrAddr());	// PATCHED after
			Machine.patch(patch_if, jump_to);						// PATCHED cond_unmet
		} else {
			Machine.patch(patch_if, Machine.nextInstrAddr());
		}
		
		return null;
	}

	@Override
	public Object visitWhileStmt(WhileStmt stmt, Object arg) {

		
		int while_begin = Machine.nextInstrAddr();
		_breakpatches.add(new BlockPatcher(while_begin));
		stmt.cond.visit(this, null);
		
		int patch_to_end = Machine.nextInstrAddr();
		Machine.emit(Op.JUMPIF,0,Reg.CB,PATCHME);
		stmt.body.visit(this, null);
		
		Machine.emit(Op.JUMP,Reg.CB,while_begin);
		int while_end = Machine.nextInstrAddr();
		Machine.patch(patch_to_end, while_end);
		_breakpatches.pop().applyPatches(while_end);
		
		return null;
	}

	public Object visitForStmt(ForStmt stmt, Object arg ) {

		for (Statement s : stmt.declList) {
			s.visit(this, null);
		}

		int patch_to_cond = Machine.nextInstrAddr();
		Machine.emit(Op.JUMP, Reg.CB,PATCHME);
		int for_begin = Machine.nextInstrAddr();
		_breakpatches.add(new BlockPatcher(for_begin));

		for (Statement s : stmt.inc) {
			s.visit(this,null);
		}

		int patch_to_end = -1;

		Machine.patch(patch_to_cond, Machine.nextInstrAddr());

		if (stmt.cond != null) {
			stmt.cond.visit(this, null);
			patch_to_end = Machine.nextInstrAddr();
			Machine.emit(Op.JUMPIF,0,Reg.CB,PATCHME);
		}

		stmt.body.visit(this, null);
		
		Machine.emit(Op.JUMP,Reg.CB,for_begin);
		int for_end = Machine.nextInstrAddr();
		if (patch_to_end > -1) {
			Machine.patch(patch_to_end, for_end);
		}
		_breakpatches.pop().applyPatches(for_end);

		Machine.emit(Op.POP, stmt.declList.size());

		return null;
	}

	@Override
	public Object visitBreakStmt(BreakStmt stmt, Object arg) {
		_breakpatches.peek().patchToEnd(Machine.nextInstrAddr());
		Machine.emit(Op.JUMP,0,Reg.CB, PATCHME);
		return null;
	}

	@Override
	public Object visitContinueStmt(ContinueStmt stmt, Object arg) {
		int while_begin = _breakpatches.peek().startAddr;
		System.out.println(while_begin);
		Machine.emit(Op.JUMP,0,Reg.CB, while_begin);
		return null;
	}

	@Override
	public Object visitUnaryExpr(UnaryExpr expr, Object arg) {
		expr.expr.visit(this, null);
		switch (expr.operator.type) {
		case Minus:
			Machine.emit(Prim.neg);
			break;
		case Not:
			Machine.emit(Prim.not);
		}
		return null;
	}

	@Override
	public Object visitBinaryExpr(BinaryExpr expr, Object arg) {


		_shortcircuitpatches = new BlockPatcher(-1);

		_binopstack.add(expr.operator.type);
		expr.left.visit(this, null);
		
		int patch_shortcircuit = 0;

		boolean match_parent = expr.operator.type == _binopstack.pop();
		
		// add in short circuiting for || expression 
		if (expr.operator.type == TokenType.BarBar) {
			Machine.emit(Op.LOAD,1,Reg.ST,-1);
			patch_shortcircuit = Machine.nextInstrAddr();
			if (match_parent) {
				_shortcircuitpatches.patchToEnd(patch_shortcircuit);
			}
			Machine.emit(Op.JUMPIF,1,Reg.CB,PATCHME);
		} 

		if (expr.operator.type == TokenType.AmpAmp) {
			Machine.emit(Op.LOAD,1,Reg.ST,-1);
			patch_shortcircuit = Machine.nextInstrAddr();
			if (match_parent) {
				_shortcircuitpatches.patchToEnd(patch_shortcircuit);
			}
			Machine.emit(Op.JUMPIF,0,Reg.CB,PATCHME);
		} 

		if (_lastop == null) _lastop = expr.operator.type;
		else if (_lastop != expr.operator.type) {
			_shortcircuitpatches.applyPatches(Machine.nextInstrAddr());
			_lastop = expr.operator.type;
		} 
		
		expr.right.visit(this, null);
		
		switch (expr.operator.type) {
			case Plus:
				Machine.emit(Prim.add); 
				break;
			case Minus:
				Machine.emit(Prim.sub);
				break;
			case Star:
				Machine.emit(Prim.mult);
				break;
			case FSlash:
				Machine.emit(Prim.div);
				break;
			case Modulo:
				Machine.emit(Prim.mod);
				break;
			case AmpAmp:
				Machine.emit(Prim.and);
				Machine.patch(patch_shortcircuit, Machine.nextInstrAddr());
				break;
			case BarBar:
				Machine.emit(Prim.or);
				Machine.patch(patch_shortcircuit, Machine.nextInstrAddr());
				break;
			case LessEqual:
				Machine.emit(Prim.le);
				break;
			case GreaterEqual:
				Machine.emit(Prim.ge);
				break;
			case LCaret:
				Machine.emit(Prim.lt);
				break;
			case RCaret:
				Machine.emit(Prim.gt);
				break;
			case EqualEqual:
				Machine.emit(Prim.eq);
				break;
			case NotEqual:
				Machine.emit(Prim.ne);
				break;
		}

		if (_binopstack.size() == 0) {
			_shortcircuitpatches.applyPatches(Machine.nextInstrAddr());
		}

		return null;
	}

	@Override
	public Object visitTernaryExpr(TernaryExpr expr, Object arg) {
		expr.cond.visit(this, null);

		int patch_to_else = Machine.nextInstrAddr();
		Machine.emit(Op.JUMPIF,0,Reg.CB, PATCHME);

		expr.trueExpr.visit(this, null);
		int patch_to_end = Machine.nextInstrAddr();
		Machine.emit(Op.JUMP,0,Reg.CB, PATCHME);

		Machine.patch(patch_to_else, Machine.nextInstrAddr());
		expr.falseExpr.visit(this, null);
		Machine.patch(patch_to_end, Machine.nextInstrAddr());

		return null;
	}

	@Override
	public Object visitRefExpr(RefExpr expr, Object arg) {
		expr.ref.visit(this, null);
		return null;
	}

	@Override
	public Object visitIxExpr(IxExpr expr, Object arg) {
		expr.ref.visit(this, null);
		expr.ixExpr.visit(this, null);
		Machine.emit(Prim.arrayref);
		return null;
	}

	@Override
	public Object visitCallExpr(CallExpr expr, Object arg) {
		// visit arg expressions and leave them on the stack in order
		for (int i = 0; i < expr.argList.size(); i++) {
			expr.argList.get(i).visit(this, null);
		}
						
		boolean is_static = ((MethodDecl) expr.functionRef.decl).isStatic;
		
		Address proc_addr = ((MethodDecl) expr.functionRef.decl).entity.address;
		
		MethodEntity me = (MethodEntity) expr.functionRef.decl.entity;
		boolean patch = me.address.offset == PATCHME;
		
		if (is_static) {
			if (patch) me.callers.add(Machine.nextInstrAddr());
			Machine.emit(Op.CALL,proc_addr.reg,proc_addr.offset);
		} else {
			
			Address ob_address;
									
			if (expr.functionRef instanceof QualRef) {
				QualRef funcqref = (QualRef) expr.functionRef;
				//System.out.println("call expr object ref: " + ((QualRef) funcqref.ref).id.decl.name);
				funcqref.ref.visit(this, null);
				//ob_address = ((QualRef) expr.functionRef).ref.decl.entity.address;
				//System.out.println("call statement setting OB: " + ob_address.toString());
				//Machine.emit(Op.LOAD, ob_address.reg, ob_address.offset);
				//Machine.emit(Op.STOREI,Reg.OB,0);
			} else {
				// the OB is already set, so push it onto the stack again
				Machine.emit(Op.LOADA,Reg.OB,0);
			}
			
			if (patch) me.callers.add(Machine.nextInstrAddr());
			Machine.emit(Op.CALLI,proc_addr.reg,proc_addr.offset);
		}
						
		return null;
	}

	@Override
	public Object visitLiteralExpr(LiteralExpr expr, Object arg) {
		expr.lit.visit(this, null);
		return null;
	}

	@Override
	public Object visitNewObjectExpr(NewObjectExpr expr, Object arg) {
		ClassDecl cd = (ClassDecl) expr.classtype.className.decl;
		
		Machine.emit(Op.LOADL,-1); 					// no class object
		Machine.emit(Op.LOADL, cd.entity.size);		// number of instance fields necessary
		Machine.emit(Prim.newobj);				
		return null;
	}

	@Override
	public Object visitNewArrayExpr(NewArrayExpr expr, Object arg) {
		expr.sizeExpr.visit(this, null);
		Machine.emit(Prim.newarr);
		return null;
	}

	@Override
	public Object visitThisRef(ThisRef ref, Object arg) {
		Machine.emit(Op.LOADA,Reg.OB,0);
		return null;
	}

	@Override
	public Object visitIdRef(IdRef ref, Object arg) {
		//System.out.println("## ref " + ref.id.spelling + "\t" + ref.decl.entity.address);
		//System.out.println("id ref: " + ref.id.spelling + " @ " + ref.id.posn);
		emitOpAddr(Op.LOAD,ref.decl.entity.address);
		return null;
	}

	@Override
	public Object visitQRef(QualRef ref, Object arg) {
		ref.ref.visit(this, null);
		//System.out.println("qref names: " + ref.ref.decl.name + "   " + ref.id.decl.name);
		//System.out.println("qref addrs: " + ref.ref.decl.entity.address + "   " + ref.id.decl.entity.address);
		if (ref.ref.decl.type instanceof ArrayType && ref.id.spelling.contentEquals("length")) {
			Machine.emit(Prim.arraylen);
			return null;
		}
		Address target = ref.id.decl.entity.address;
		//emitOpAddr(Op.LOAD, ob_address);		// push value of object address
		//System.out.println("qref id offset: " + target);
		
		//System.out.println(target);
		
		if (target.reg == Reg.SB) {
			Machine.emit(Op.LOAD, Reg.SB, target.offset);
		} else {
			Machine.emit(Op.LOADL, target.offset);	// push field offset
			Machine.emit(Prim.fieldref);
		}
		
		
		return null;
	}

	@Override
	public Object visitIdentifier(Identifier id, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitOperator(Operator op, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitIntLiteral(IntLiteral num, Object arg) {
		int value = Integer.valueOf(num.spelling);
		Machine.emit(Op.LOADL,value);
		return null;
	}

	@Override
	public Object visitBooleanLiteral(BooleanLiteral bool, Object arg) {
		
		if (bool.type == TokenType.True) {
			Machine.emit(Op.LOADL,1);
		} else {
			Machine.emit(Op.LOADL,0);
		}
		
		return null;
	}

	@Override
	public Object visitNullLiteral(NullLiteral expr, Object arg) {
		Machine.emit(Op.LOADL,Machine.nullRep);
		return null;
	}
	
	public Object visitStringLiteral(StringLiteral lit, Object arg) {
		
		Machine.emit(Op.LOADL, lit.content.length());
		Machine.emit(Prim.newarr);

		for (int i = 0; i < lit.content.length(); i++) {
			Machine.emit(Op.LOAD,Reg.ST,-1);
			Machine.emit(Op.LOADL,i);
			Machine.emit(Op.LOADL, lit.content.charAt(i));
			Machine.emit(Prim.arrayupd);
		}
		
		return null;
	}


	/**
	 * Helper methods
	 */

	private void emitOpAddr(Op op, Address addr) {
		Machine.emit(op, addr.reg, addr.offset);
	}
	
	/*
	 * PATCHKEY methods
	 */

	private Patchkey[] patchkeys = new Patchkey[] 
		{
			new Patchkey("System.out.println", new BaseType(TypeKind.INT, SourcePosition.NOPOS)),
			new Patchkey("System.out.println"), 
			new Patchkey("System.out.println", new BaseType(TypeKind.BOOLEAN, SourcePosition.NOPOS)),
			new Patchkey("System.out.println", new ClassType(new Identifier(new Token(TokenType.Ident, SourcePosition.NOPOS, "String")), SourcePosition.NOPOS)),
			new Patchkey("String.length")
	};

	private boolean patchExists(Patchkey p) {

		for (Patchkey key : patchkeys) {
			if (p.equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean emitPatch(Patchkey key) {		
		
		if (key.equals(patchkeys[0])) {			// println(int n)
			Machine.emit(Op.LOAD,Reg.LB,-1);	// load single int arg from LB
			Machine.emit(Prim.putintnl);		// print it 
			Machine.emit(Op.RETURN,0,0,1);		// pop the arg
			return true;
		} else if (key.equals(patchkeys[1])) {	// println(void)
			Machine.emit(Prim.puteol);
			Machine.emit(Op.RETURN,0);
			return true;
		} else if (key.equals(patchkeys[2])) {	// println(boolean b)
			Machine.emit(Op.LOAD,Reg.LB,-1);	// load boolean value from LB
			int patch_tfjump = Machine.nextInstrAddr();
			Machine.emit(Op.JUMPIF,0,Reg.CB,PATCHME);	
			emitPrintString("true");
			Machine.emit(Op.RETURN,1);
			Machine.patch(patch_tfjump, Machine.nextInstrAddr());
			emitPrintString("false");
			Machine.emit(Prim.puteol);
			Machine.emit(Op.RETURN,1);
			return true;
		} else if (key.equals(patchkeys[3])) {	// println(String s)

			// test if the reference is null

			Machine.emit(Op.LOAD, Reg.LB, -1);
			int patch_to_nullprint = Machine.nextInstrAddr();
			Machine.emit(Op.JUMPIF,0,Reg.CB,PATCHME);
			
			Machine.emit(Op.PUSH,1);			// arr_len (LB[3]),    
			Machine.emit(Op.LOADL,0); 			// i (LB[4]) = 0
			Machine.emit(Op.LOAD, Reg.LB, -1);	// load char array address to stack
			Machine.emit(Prim.arraylen);
			Machine.emit(Op.STORE,Reg.LB,3);

			/* WHILE BEGIN */
			int while_begin = Machine.nextInstrAddr(); // while (i < arr_len) {....
			Machine.emit(Op.LOAD,Reg.LB,3);
			Machine.emit(Op.LOAD,Reg.LB,4);
			Machine.emit(Prim.sub);
			int patch_to_end = Machine.nextInstrAddr();
			Machine.emit(Op.JUMPIF,0,Reg.CB, PATCHME);

			Machine.emit(Op.LOAD, Reg.LB,-1);	 // ch = s.charAt(i)
			Machine.emit(Op.LOAD,Reg.LB,4);		
			Machine.emit(Prim.arrayref);

			Machine.emit(Prim.put);				// print(ch)

			Machine.emit(Prim.succ);			// i++

			Machine.emit(Op.JUMP,Reg.CB, while_begin);	// ...}

			// code to print 'null'
			// note that if we enter the while loop, this code is never
			// executed
			Machine.patch(patch_to_nullprint, Machine.nextInstrAddr());
			emitPrintString("null");

			/* WHILE END */
			Machine.patch(patch_to_end, Machine.nextInstrAddr());

			Machine.emit(Prim.puteol);
			Machine.emit(Op.RETURN,1);		// pop the arg
			return true;
		} else if (key.equals(patchkeys[4])) {

			Machine.emit(Op.LOAD,Reg.OB,0);
			Machine.emit(Prim.arraylen);
			Machine.emit(Op.RETURN,1,0,0);
			return true;

		}else {
			return false;
		}
	}

	public void emitPrintString(String s) {
		for (int i = 0; i < s.length(); i++) {
			Machine.emit(Op.LOADL,s.charAt(i));
			Machine.emit(Prim.put);
		}
	}
}

class BlockPatcher {
	public Stack<Integer> patch_to_end;
	public int startAddr;

	public BlockPatcher(int startAddr) {
		this.startAddr = startAddr;
		this.patch_to_end = new Stack<Integer>();
	}

	public void patchToEnd(int patch) {
		patch_to_end.add(patch);
	}

	public void patchToStart(int patch) {
		Machine.patch(patch, startAddr);
	}

	public void applyPatches(int endAddr) {
		while (! patch_to_end.empty()) {
			Machine.patch(patch_to_end.pop(), endAddr);
		}
	}

}