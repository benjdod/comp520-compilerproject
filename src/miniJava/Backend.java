package miniJava;

import mJAM.ObjectFile;
import mJAM.Machine;
import mJAM.Disassembler;
import mJAM.Interpreter;

public class Backend {
	public static void main(String[] args) {
		
		Machine.emit(Machine.Op.LOADL, 69);
		Machine.emit(Machine.Op.LOADL, 1);
		Machine.emit(Machine.Prim.alloc);
		Machine.emit(Machine.Op.STOREI, 1);
		Machine.emit(Machine.Op.LOAD,1,Machine.Reg.HT, 0);
		Machine.emit(Machine.Op.LOAD,1,Machine.Reg.HT, 0);
		Machine.emit(Machine.Prim.add);
		Machine.emit(Machine.Prim.putintnl);
		
		ObjectFile o_file = new ObjectFile("code/test.mjam");
		boolean failed = false;
		
		failed = o_file.write();

		Disassembler d = new Disassembler("code/test.mjam");
		failed = d.disassemble();
		
		Interpreter.debug("code/test.mjam", "code/test.mjam.asm");
		//Interpreter.interpret("code/test.mjam");
	}
}
