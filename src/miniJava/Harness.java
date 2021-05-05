package miniJava;

import mJAM.Disassembler;
import mJAM.Interpreter;

public class Harness {
    public static void main(String[] args) {

        //String filepath = "./tests/test.java";
        String filepath = "./tests/forloop/pass106.java";

        String object_filepath = Compiler.compile(new String[] { "-c", filepath});

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
