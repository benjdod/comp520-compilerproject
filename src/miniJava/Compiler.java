package miniJava;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;

import miniJava.ContextualAnalysis.Identification;
import miniJava.SyntacticAnalyzer.Parser;
import miniJava.SyntacticAnalyzer.Scanner;
import miniJava.AbstractSyntaxTrees.*;
import miniJava.AbstractSyntaxTrees.Package;

public class Compiler {
	
	static final int PARSE_SUCCESS = 0;
	static final int COMMAND_FAILURE = 3;
	static final int PARSE_FAILURE = 4;

	static ErrorReporter _reporter = new ErrorReporter();
	
	static void exitProgram(int exitcode) {
		// System.out.println("Exiting with code " + exitcode);
		System.exit(exitcode);
	}

	static void checkForErrors() {
		if (_reporter.hasErrors()) {
			System.out.println("\u001B[0;91m----- Compilation failed! -----\u001B[0m");
			_reporter.printFirst();
			System.out.print("\u001B[0;31m          ----------\u001B[0m");
			exitProgram(PARSE_FAILURE);
		}
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

		try {
			// this is the most surefire way I know to check for ASCII only outside of a raw byte
			// stream, which seems spartan. Idk how it'll handle eastern encodings 
			// (shift-JIS, GB, etc.) though...
			f = new FileReader(filepath, StandardCharsets.UTF_8);	
		} catch (Exception e) {
			System.err.println(e);
			exitProgram(COMMAND_FAILURE);
		}

		Scanner s = new Scanner(f, _reporter);
		Parser p = new Parser(s, _reporter);

		Package tree = p.parse();

		checkForErrors();

		Identification idn = new Identification(_reporter);
		idn.validate(tree);

		checkForErrors();

		System.out.println("\u001B[0;32mCompilation successful.\u001B[0m");

		/*
		ASTDisplay adt = new ASTDisplay();
		adt.showTree(tree); */

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
			target = "./test/test1.java";
		} else {
			target = args[0];
		}
		
		runCompiler(target);
	}

}