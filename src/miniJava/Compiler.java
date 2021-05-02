package miniJava;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;

import miniJava.ContextualAnalysis.ContextualAnalyzer;
import miniJava.ContextualAnalysis.Identification;
import miniJava.ContextualAnalysis.TypeChecker;
import miniJava.SyntacticAnalyzer.Parser;
import miniJava.SyntacticAnalyzer.Scanner;
import miniJava.AbstractSyntaxTrees.*;
import miniJava.AbstractSyntaxTrees.Package;
import miniJava.CodeGenerator.CodeGenerator;
import mJAM.Interpreter;
import mJAM.ObjectFile;
import mJAM.Disassembler;

public class Compiler {
	
	static final int PARSE_SUCCESS = 0;
	static final int FILE_FAILURE = 1;
	static final int COMMAND_FAILURE = 1;
	static final int PARSE_FAILURE = 4;

	static ErrorReporter _reporter = new ErrorReporter();

	// a comment
	
	static void exitProgram(int exitcode) {
		// System.out.println("Exiting with code " + exitcode);
		System.exit(exitcode);
	}

	static void checkForErrors() {
		if (_reporter.hasErrors()) {
			//System.out.println("\u001B[0;91m----- Compilation failed! -----\u001B[0m");
			_reporter.prinsOutput();
			//System.out.print("\u001B[0;91m          ----------\u001B[0m");
			exitProgram(PARSE_FAILURE);
		}
	}

	static void checkForError() {
		if (_reporter.hasErrors()) {
			_reporter.prinsOutput(_reporter.getFirst());
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

			f = new FileReader(filepath/*, StandardCharsets.UTF_8*/);	
		} catch (Exception e) {
			System.err.println(e);
			exitProgram(COMMAND_FAILURE);
		}
		

		Scanner s = new Scanner(f, _reporter);
		Parser p = new Parser(s, _reporter);

		System.out.println("--- parsing ---");
		Package tree = p.parse();

		checkForError();

		
		ASTDisplay adt = new ASTDisplay();
		adt.showTree(tree);
		

		System.out.println("--- identification ---");
		//Identification idn = new Identification(tree, _reporter);

		//checkForError();

		System.out.println("--- type checking ---");
		//TypeChecker tc = new TypeChecker(tree, _reporter);

		ContextualAnalyzer ca = new ContextualAnalyzer(tree, _reporter);

		checkForErrors();
		
		System.out.println("--- code generation ---");
		CodeGenerator cg = new CodeGenerator(tree, _reporter);	
		
		checkForErrors();
		
		String object_filepath = filepath.substring(0, filepath.lastIndexOf(".")) + ".mJAM";
		
		ObjectFile ofile = new ObjectFile(object_filepath);
		boolean failed = ofile.write();
		
		if (failed) {
			System.out.println("Error: could not write object file!");
			exitProgram(FILE_FAILURE);
		} else {
			//System.out.println("Wrote object file to " + object_filepath);
		}
		

		
		
		
		Disassembler d = new Disassembler(object_filepath);
		d.disassemble();
		
		boolean debug = false;
		
		if (debug) {
			Interpreter.debug(object_filepath, filepath);
		} else {
			Interpreter.interpret(object_filepath);
		}
		

		System.out.println("\u001B[0;32mCompilation successful.\u001B[0m");
		

		/*
		ASTDisplay adt = new ASTDisplay();
		adt.showTree(tree); 
		*/

		try {
			if (f != null)
				f.close();
		} catch (Exception e) {
			System.err.println(e);
			System.exit(COMMAND_FAILURE);
		}
		
		System.exit(PARSE_SUCCESS);
	}
	
	public static void compile(String filepath) {
		runCompiler(filepath);
	}
	
	public static void main(String[] args) {
		
		/*
		if (args.length != 1) {
			System.out.println("Error: please enter a single path to a valid miniJava source file");
			exitProgram(COMMAND_FAILURE);
		} */
		
		
		String target;
		
		if (args.length < 1) {
			target = "../tests/pa4_tests/pass403.java";
			//target = "./test/test.java";
		} else {
			target = args[0];
		}
		
		
		runCompiler(target);
	}

}
