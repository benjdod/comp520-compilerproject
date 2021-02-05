package miniJava;

import java.io.FileReader;
import java.nio.charset.Charset;

import syntacticAnalyzer.Scanner;
import syntacticAnalyzer.Parser;
public class Compiler {
	
	static final int PARSE_SUCCESS = 0;
	static final int PARSE_FAILURE = 4;
	
	public static void main(String[] args) {
		
		FileReader f = null;
		ErrorReporter reporter = new ErrorReporter();

		try {
			// this is the most surefire way I know to check for ASCII only outside of a raw byte
			// stream, which seems spartan. Idk how it'll handle eastern encodings 
			// (shift-JIS, GB, etc.) though...
			f = new FileReader("test/parsing/test1.mjava", Charset.forName("cp1252"));	
		} catch (Exception e) {
			System.err.println(e);
			System.exit(PARSE_FAILURE);
		}

		Scanner s = new Scanner(f, reporter);
		Parser p = new Parser(s, reporter);

		p.parse();

		if (reporter.hasErrors()) {
			System.out.println("Parse failed");
			reporter.print();
		} else {
			System.out.println("Parse successful");
		}

		try {
			if (f != null)
				f.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		
		System.exit(PARSE_SUCCESS);
	}

}
