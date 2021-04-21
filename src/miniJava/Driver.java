package miniJava;

import mJAM.Interpreter;
import mJAM.Disassembler;

public class Driver {
	public static void main(String[] args) {
		String filepath;
		
		if (args.length < 1) {
			//target = "../tests/pa3_tests/fail351.java";
			filepath = "./test/codegen/prinstest.java";
		} else {
			filepath = args[0];
		}
		
		Compiler.compile(filepath);
		
		String object_filepath = filepath.substring(0, filepath.lastIndexOf(".")) + ".mJAM";
		
		System.out.println(object_filepath);
		
		Disassembler d = new Disassembler(object_filepath);
		d.disassemble();
		
		boolean debug = true;
		
		if (debug) {
			Interpreter.debug(object_filepath, filepath);
		} else {
			Interpreter.interpret(object_filepath);
		}
	}
}
