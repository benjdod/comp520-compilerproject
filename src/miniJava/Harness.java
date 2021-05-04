package miniJava;

import mJAM.Disassembler;
import mJAM.Interpreter;

public class Harness {
    public static void main(String[] args) {

        //String filepath = "./test/test.java";
        String filepath = "./test/features/forloop/fail101.java";

        String object_filepath = Compiler.compile(new String[] {"-v", "-c", filepath});

        Disassembler d = new Disassembler(object_filepath);
        d.disassemble();
		
        boolean debug = false;
		
        if (debug) {
            Interpreter.debug(object_filepath, filepath);
        } else {
            Interpreter.interpret(object_filepath);
        }
    }
}
