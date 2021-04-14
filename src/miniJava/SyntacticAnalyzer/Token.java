package miniJava.SyntacticAnalyzer;

import miniJava.SyntacticAnalyzer.SourcePosition;
import miniJava.SyntacticAnalyzer.TokenType;

public class Token {
	
	public long index;
	public int line;
	public int column;
	public SourcePosition mark;
	public String spelling;
	
	public int length;
	public TokenType type;

	public Token(TokenType type, SourcePosition mark, String spelling) {
		this.type = type;
		this.mark = mark;
		this.spelling = spelling;
	}
	
	public Token(TokenType type, SourcePosition mark) {
		this(type, mark, null);
	}
	
	public String getSpelling() {
		if (spelling != null) {
			return spelling;
		} else return "";
	}
	
	public SourcePosition getMark() {
		return this.mark.makeCopy();
	}
}
