package miniJava.CodeGenerator;

import java.util.Stack;
import miniJava.ErrorReporter;
import miniJava.AbstractSyntaxTrees.*;
import miniJava.AbstractSyntaxTrees.Package;
import miniJava.SyntacticAnalyzer.SourcePosition;
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
	private Stack<Integer> _argsizestack;
	
	private final SourcePosition NOPOS = new SourcePosition(0,0);
	
	public CodeGenerator(Package tree, ErrorReporter reporter) {
		_tree = tree;
		_reporter = reporter;
		_main_method = null;
		_argsizestack = new Stack();
		
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

	@Override
	public Object visitPackage(Package prog, Object arg) throws CompilationError {
		
		_main_method = null;
		_static_ptr = 0;
		_inst_ptr = 0;
		
		Machine.emit(Op.LOADL,0);
		Machine.emit(Prim.newarr);
		int patch_callmain = Machine.nextInstrAddr();
		Machine.emit(Op.CALL, Reg.CB, -1);
		Machine.emit(Op.HALT);
		
		for (ClassDecl cd : prog.classDeclList) {
			cd.visit(this, null);
		}
		

		// is there a main method?
		if (_main_method == null) {
			throw new CompilationError("no main method present in program", NOPOS);
		}
		
		// patch in call to main
		Machine.patch(patch_callmain, _main_method.address.offset);
		
		return null;
	}

	@Override
	public Object visitClassDecl(ClassDecl cd, Object arg) {
		
		_cd = cd;
		
		cd.entity = new UnknownValue(Reg.SB, _static_ptr);
		cd.entity.size = 0;
		
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
			Machine.emit(Op.PUSH,1);
			fd.entity = new UnknownValue(Reg.SB, _static_ptr++);
		} else {
			
			// increment the instance size by 1
			_cd.entity.size += 1;
			fd.entity = new UnknownValue(Reg.OB, _inst_ptr++);
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
		md.entity = new UnknownValue(Reg.CB, Machine.nextInstrAddr());
		md.address = new Address(Reg.CB, Machine.nextInstrAddr());
		
		/* add addresses relative to frame for parameters, so forexample:
		 * 		
		 *  someMethod(int x, int y, Object z)
		 * 	
		 *	LB 	(0)
		 * 	z	(-1)
		 * 	y	(-2)
		 * 	x	(-3)
		 * */
		
		int num_parameters = md.parameterDeclList.size();
		
		// point the entity descriptors to the addresses behind LB
		for (int i = 0 - num_parameters; i < 0; i++) {
			md.parameterDeclList.get(i + num_parameters).entity = new UnknownValue(Reg.LB, i);
		}
		
		// initialize local offset to the first free
		// frame address (OB,DL,RA,__)
		_local_ptr = 3;
		
		
		// if the method is provided by the environment, we need to patch it here
		switch (md.patchkey) {
			case "System.out.println":
				Machine.emit(Op.LOAD,Reg.LB,-1);	// load single int arg from LB
				Machine.emit(Prim.putintnl);		// print it 
				Machine.emit(Op.RETURN,0,0,1);		// pop the arg
				return null;
			default:
				;	// otherwise, pass through to the code generation
		}
		
		for (Statement s : md.statementList) {
			s.visit(this, null);
		}
		
		if (md.type.typeKind == TypeKind.VOID && ! _method_return) {
			Machine.emit(Op.RETURN);
		}
		
		_argsizestack.pop();
		
		return null;
	}

	@Override
	public Object visitParameterDecl(ParameterDecl pd, Object arg) {
		
		// we don't use this
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitClassType(ClassType type, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitArrayType(ArrayType type, Object arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitBlockStmt(BlockStmt stmt, Object arg) {
		for (Statement s : stmt.sl) {
			s.visit(this, null);
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
		Address target = stmt.ref.decl.entity.address;
		stmt.val.visit(this, null);
		emitOpAddr(Op.STORE, target);
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
		
		Address proc_addr = ((MethodDecl) stmt.methodRef.decl).address;
		
		if (is_static) {
			Machine.emit(Op.CALL,proc_addr.reg,proc_addr.offset);
		} else {
			
			Address ob_address;
			
			if (stmt.methodRef instanceof QualRef) {
				ob_address = ((QualRef) stmt.methodRef).ref.decl.entity.address;
				System.out.println("call statement setting OB: " + ob_address.toString());
				Machine.emit(Op.LOAD, ob_address.reg, ob_address.offset);
			} else {
				// the OB is already set
			}
			
			Machine.emit(Op.CALLI,proc_addr.reg,proc_addr.offset);
		}
		
		if (stmt.methodRef.decl.type.typeKind != TypeKind.VOID) {
			Machine.emit(Op.POP);
		}
				
		return null;
	}

	@Override
	public Object visitReturnStmt(ReturnStmt stmt, Object arg) {
		
		_method_return = true;
		
		if (stmt.returnExpr != null) {
			stmt.returnExpr.visit(this, null);
			Machine.emit(Op.RETURN,1,0,_argsizestack.peek());
			System.out.println("args for " +_md.name + ": " + _md.parameterDeclList.size());
		} else {
			Machine.emit(Op.RETURN,0,0,_argsizestack.peek());
		}
		
		return null;
	}

	@Override
	public Object visitIfStmt(IfStmt stmt, Object arg) {
				
		int patch_branch, jump_to;
		
		stmt.cond.visit(this, null);
		patch_branch = Machine.nextInstrAddr();
		Machine.emit(Op.JUMPIF,0,Reg.CB,-1); 	// patchme!
		stmt.thenStmt.visit(this, null);

		
		if (stmt.elseStmt != null) {
			jump_to = Machine.nextInstrAddr();
			Machine.emit(Op.JUMP,Reg.CB,-1); 	// patchme!
			stmt.elseStmt.visit(this, null);
			Machine.patch(jump_to, Machine.nextInstrAddr());
		}
		
		Machine.patch(patch_branch, Machine.nextInstrAddr());
		
		return null;
	}

	@Override
	public Object visitWhileStmt(WhileStmt stmt, Object arg) {
		
		int while_begin = Machine.nextInstrAddr();
		stmt.cond.visit(this, null);
		
		int patch_branch = Machine.nextInstrAddr();
		Machine.emit(Op.JUMPIF,0,Reg.CB,-1); 	// patchme!
		stmt.body.visit(this, null);
		
		Machine.emit(Op.JUMP,Reg.CB,while_begin);
		Machine.patch(patch_branch, Machine.nextInstrAddr());
		
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
		expr.left.visit(this, null);
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
			case AmpAmp:
				Machine.emit(Prim.and);
				break;
			case BarBar:
				Machine.emit(Prim.or);
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
		}
		
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
		
		Address proc_addr = ((MethodDecl) expr.functionRef.decl).address;
		
		if (is_static) {
			Machine.emit(Op.CALL,proc_addr.reg,proc_addr.offset);
		} else {
			
			Address ob_address;
			
			if (expr.functionRef instanceof QualRef) {
				ob_address = ((QualRef) expr.functionRef).ref.decl.entity.address;
				System.out.println("call statement setting OB: " + ob_address.toString());
				Machine.emit(Op.LOAD, ob_address.reg, ob_address.offset);
			} else {
				// the OB is already set
			}
			
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visitIdRef(IdRef ref, Object arg) {
		//System.out.println("## ref " + ref.id.spelling + "\t" + ref.decl.entity.address);
		emitOpAddr(Op.LOAD,ref.decl.entity.address);
		return null;
	}

	@Override
	public Object visitQRef(QualRef ref, Object arg) {
		Address target = ref.decl.entity.address;
		Address ob_address = ref.ref.decl.entity.address;
		emitOpAddr(Op.LOAD, ob_address);		// push value of object address
		Machine.emit(Op.LOADL, target.offset);	// push field offset
		Machine.emit(Prim.fieldref);
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
		// TODO Auto-generated method stub
		return null;
	}
	
	private void emitOpAddr(Op op, Address addr) {
		Machine.emit(op, addr.reg, addr.offset);
	}

}
