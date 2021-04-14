package miniJava;

import mJAM.ObjectFile;
import mJAM.Machine;
import mJAM.Disassembler;
import mJAM.Interpreter;

public class Backend {
	public static void main(String[] args) {
		/*
		Machine.emit(Machine.Op.LOADL, 0, 0, 0);
		Machine.emit(Machine.Prim.newarr);
		Machine.emit(Machine.Op.HALT, 0, 0, 0);
		*/
		Machine.emit(Machine.Op.LOADL, 5);
		Machine.emit(Machine.Op.STORE,1,Machine.Reg.SB,0);
		Machine.emit(Machine.Op.LOAD,1,Machine.Reg.SB,0);
		Machine.emit(Machine.Op.LOADL,3);
		Machine.emit(Machine.Prim.add);
		Machine.emit(Machine.Prim.putintnl);
		Machine.emit(Machine.Op.LOADL,2);
		Machine.emit(Machine.Prim.newarr);
		Machine.emit(Machine.Op.HALT, 0, 0, 0);
		
		ObjectFile o_file = new ObjectFile("code/test.mjam");
		boolean failed = false;
		
		failed = o_file.write();

		Disassembler d = new Disassembler("code/test.mjam");
		failed = d.disassemble();
		
		//Interpreter.debug("code/test.mjam", "code/test.mjam.asm");
		Interpreter.interpret("code/test.mjam");
	}
}
