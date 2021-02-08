package miniJava;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;

import syntacticAnalyzer.Token;
import syntacticAnalyzer.Scanner;
import syntacticAnalyzer.Parser;
import syntacticAnalyzer.SourceReader;
public class Compiler {
	
	static final int PARSE_SUCCESS = 0;
	static final int PARSE_FAILURE = 4;

	static void runCompiler(String filepath) {
		FileReader f = null;
		ErrorReporter reporter = new ErrorReporter();

		try {
			// this is the most surefire way I know to check for ASCII only outside of a raw byte
			// stream, which seems spartan. Idk how it'll handle eastern encodings 
			// (shift-JIS, GB, etc.) though...
			f = new FileReader(filepath, Charset.forName("utf-8"));	
		} catch (Exception e) {
			System.err.println(e);
			System.exit(PARSE_FAILURE);
		}

		Scanner s = new Scanner(f, reporter);
		Parser p = new Parser(s, reporter);

		p.parse();

		if (reporter.hasErrors()) {
			System.out.println("### Parse failed - check below for errors ###");
			reporter.print();
			System.exit(PARSE_FAILURE);
		} else {
			System.out.println("Parse successful");
		}

		try {
			if (f != null)
				f.close();
		} catch (Exception e) {
			System.err.println(e);
			System.exit(PARSE_FAILURE);
		}
		
		System.exit(PARSE_SUCCESS);
	}
	
	public static void main(String[] args) {
		
		/*
		ErrorReporter r = new ErrorReporter();
		SourceReader s = new SourceReader("class Test {}\nclass Thing");
		
		int c; 

		try {
			while ((c = s.read()) != '\0') {
				System.out.println(String.format("%d\t%c", c,c));
			}
		} catch (Exception e) { ; }

		System.out.println("print complete!");
		*/

		runCompiler("../minijava_tests/parsing/test3.mjava");
		
	}

}