package miniJava;

import java.io.FileReader;

import miniJava.ContextualAnalysis.ContextualAnalyzer;
import miniJava.SyntacticAnalyzer.Parser;
import miniJava.SyntacticAnalyzer.Scanner;
import miniJava.AbstractSyntaxTrees.*;
import miniJava.AbstractSyntaxTrees.Package;
import miniJava.CodeGenerator.CodeGenerator;
import mJAM.ObjectFile;
public class Compiler {
	
	static final int PARSE_SUCCESS = 0;
	static final int FILE_FAILURE = 1;
	static final int COMMAND_FAILURE = 1;
	static final int PARSE_FAILURE = 4;

	static ErrorReporter _reporter = new ErrorReporter();

	static boolean verbose = false;
	static boolean quiet = false;
	static boolean color = false;
	
	static void exitProgram(int exitcode) {
		// System.out.println("Exiting with code " + exitcode);
		System.exit(exitcode);
	}

	static void printGreen(String s) {
		if (! quiet) System.out.println((color ? "\u001B[0;92m" : "") + s + (color ? "\u001B[0m" : ""));
	}

	static void printRed(String s) {
		if (! quiet) System.out.println((color ? "\u001B[0;91m" : "") + s + (color ? "\u001B[0m" : ""));
	}

	static void printVerboseGreen(String s) {
		if (verbose) printGreen(s);
	}

	static void printVerboseRed(String s) {
		if (verbose) printRed(s);
	}

	static void printVerbose(String s) {
		if (verbose) System.out.println(s);
	}

	static void checkForErrors() {
		if (! quiet)
			if (_reporter.hasErrors()) {
				printRed("failed");
				_reporter.prinsOutput();
				exitProgram(PARSE_FAILURE);
			}
	}

	static void checkForError() {
		if (! quiet)
			if (_reporter.hasErrors()) {
				System.out.print("failed ---\n");
				_reporter.prinsOutput(_reporter.getFirst());
				exitProgram(PARSE_FAILURE);
			}
	}

	/**
	 * Compiles a miniJava program
	 * @param filepath path to the miniJava source code
	 * @return path to the resulting object file
	 */

	public static String compile(String filepath) {
				
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

		//printVerbose("--- parsing ---");
		Package tree = p.parse();

		checkForError();
		printVerbose("AST generation complete.");

		ContextualAnalyzer ca = new ContextualAnalyzer(tree, _reporter);

		checkForErrors();
		printVerbose("contextual analysis complete.");
		
		CodeGenerator cg = new CodeGenerator(tree, _reporter);	
		
		checkForErrors();
		printVerbose("code generation complete.");
		
		String object_filepath = filepath.substring(0, filepath.lastIndexOf(".")) + ".mJAM";
		
		ObjectFile ofile = new ObjectFile(object_filepath);
		boolean failed = ofile.write();
		
		if (failed) {
			System.out.println("Error: could not write object file!");
			exitProgram(FILE_FAILURE);
		} 

		printVerbose("object file written.");

		try {
			if (f != null)
				f.close();
		} catch (Exception e) {
			System.err.println(e);
			System.exit(COMMAND_FAILURE);
		}

		printVerboseGreen("Compilation successful.");

		return object_filepath;
		
		// System.exit(PARSE_SUCCESS);
	}

	public static String compile(String[] args) {
	
		return compile(parseArgs(args));
	}

	private static void argFail(String msg, String optStr) {
		printRed(msg + " '" + optStr + "'");
		System.exit(FILE_FAILURE);
	}

	private static String parseArgs(String[] args) {

		String target = null;

		for (String arg : args) {

			if (arg.charAt(0) == '-') {

				if (arg.length() <= 1) argFail("bad option", arg);

				String a = arg.substring(1);

				if (a.contentEquals("q") || a.contentEquals("-quiet")) {
					quiet = true;
				}  else if (a.contentEquals("v") || a.contentEquals("-verbose")) {
					verbose = true;
					quiet = false;
				} else if (a.contentEquals("c") || a.contentEquals("-color")) {
					color = true;
				}
			} 

			else {
				if (target == null) target = arg;
				else argFail("duplicate filepath" ,arg);
			}
		}

		if (target == null ) argFail("no filepath specified", "");

		return target;
	}
	
	public static void main(String[] args) {
		
		/*
		if (args.length != 1) {
			System.out.println("Error: please enter a single path to a valid miniJava source file");
			exitProgram(COMMAND_FAILURE);
		} */
		
		parseArgs(args);
		
		String target = "";
		
		if (args.length < 1) {
			System.out.println("must provide a path to a miniJava program");
			System.exit(FILE_FAILURE);
		} else {
			target = args[0];
		}

		
		
		
		compile(target);
		printVerboseGreen("Compilation successful");
		System.exit(PARSE_SUCCESS);
	}

}
