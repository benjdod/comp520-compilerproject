package miniJava;

import syntacticAnalyzer.Scanner;
import syntacticAnalyzer.Token;
import syntacticAnalyzer.TokenType;

public class Compiler {
	
	static final int PARSE_SUCCESS = 0;
	static final int PARSE_FAILURE = 4;
	
	public static void main(String[] args) {
		
		Scanner s = new Scanner("/*hnello\nthis(654+8)-tea;");
		Token t;
		
		while ((t = s.next()).type != TokenType.Eot) {
			System.out.println(t.type + "\t" + t.index);
		}
		
		System.exit(PARSE_SUCCESS);
	}

}
