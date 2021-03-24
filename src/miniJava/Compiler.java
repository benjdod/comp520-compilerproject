package miniJava;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;

import miniJava.SyntacticAnalyzer.Parser;
import miniJava.SyntacticAnalyzer.Scanner;
import miniJava.AbstractSyntaxTrees.*;

public class Compiler {
	
	static final int PARSE_SUCCESS = 0;
	static final int COMMAND_FAILURE = 3;
	static final int PARSE_FAILURE = 4;
	
	static void exitProgram(int exitcode) {
		// System.out.println("Exiting with code " + exitcode);
		System.exit(exitcode);
	}

	static void runCompiler(String filepath) {
		
		int l = filepath.lastIndexOf('.');
		
		try {
			String ext = filepath.substring(l + 1);
			if ( !(ext.contentEquals("java") || ext.contentEquals("mjava"))) {
				System.out.println("Incorrect file extension. (saw " + ext + ")");
				exitProgram(COMMAND_FAILURE);
			}
		} catch (Exception e) {;}
		
		FileReader f = null;
		ErrorReporter reporter = new ErrorReporter();

		try {
			// this is the most surefire way I know to check for ASCII only outside of a raw byte
			// stream, which seems spartan. Idk how it'll handle eastern encodings 
			// (shift-JIS, GB, etc.) though...
			f = new FileReader(filepath, StandardCharsets.UTF_8);	
		} catch (Exception e) {
			System.err.println(e);
			exitProgram(COMMAND_FAILURE);
		}

		Scanner s = new Scanner(f, reporter);
		Parser p = new Parser(s, reporter);

		AST tree = p.parse();

		if (reporter.hasErrors()) {
			System.out.println("### Parse failed ###");
			reporter.printFirst();
			exitProgram(PARSE_FAILURE);
		} 

		ASTDisplay ad = new ASTDisplay();
		ad.showTree(tree);

		try {
			if (f != null)
				f.close();
		} catch (Exception e) {
			System.err.println(e);
			System.exit(COMMAND_FAILURE);
		}
		
		System.exit(PARSE_SUCCESS);
	}
	
	public static void main(String[] args) {
		
		/*
		if (args.length != 1) {
			System.out.println("Error: please enter a single path to a valid miniJava source file");
			exitProgram(COMMAND_FAILURE);
		} */
		
		String target;
		
		if (args.length < 1) {
			target = "../tests/pa2_tests/pass290.java";
			//target = "./test/test1.java";
		} else {
			target = args[0];
		}
		
		runCompiler(target);
	}

}