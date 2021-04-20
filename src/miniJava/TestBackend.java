package miniJava;

import mJAM.ObjectFile;
import mJAM.Machine;
import mJAM.Machine.*;
import mJAM.Disassembler;
import mJAM.Interpreter;

public class TestBackend {
	public static void main(String[] args) {
		
		/* call main method and then halt */
		Machine.emit(Op.LOADL,0);
		Machine.emit(Prim.newarr);
		int patch_callMain = Machine.nextInstrAddr();
		Machine.emit(Op.CALL,Reg.CB,-1);	// patchme
		Machine.emit(Op.HALT,0,0,0);
		
		
		/* static int incByOne(int x) {return x + 1;} */
		int codeAddr_Incbyone = Machine.nextInstrAddr();
		Machine.emit(Op.LOAD, Reg.LB, -1);
		//Machine.emit(Prim.putintnl);
		Machine.emit(Machine.Op.LOADL,1);
		Machine.emit(Machine.Prim.add);
		Machine.emit(Op.RETURN, 1,0,1);
		
		/* main(String[] args) {...} */
		int codeAddr_main = Machine.nextInstrAddr();
		
		Machine.emit(Op.PUSH,3);
		
		// initialize a new object 
		Machine.emit(Op.LOADL,-1);
		Machine.emit(Op.LOADL,1);
		Machine.emit(Prim.newobj);
		
		Machine.emit(Op.LOADL,5);
		Machine.emit(Op.CALL, Reg.CB,codeAddr_Incbyone);   // patchme
		Machine.emit(Prim.putintnl);
		Machine.emit(Op.RETURN);
		
		Machine.patch(patch_callMain, codeAddr_main);
		
		
		ObjectFile o_file = new ObjectFile("code/test.mjam");
		boolean failed = false;
		
		failed = o_file.write();

		Disassembler d = new Disassembler("code/test.mjam");
		failed = d.disassemble();
		
		Interpreter.debug("code/test.mjam", "code/test.mjam.asm");
		//Interpreter.interpret("code/test.mjam");
	}
}
