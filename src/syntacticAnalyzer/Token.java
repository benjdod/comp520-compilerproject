package syntacticAnalyzer;

import syntacticAnalyzer.TokenType;
import syntacticAnalyzer.SourceMark;

public class Token {
	
	public long index;
	public int line;
	public int column;
	public SourceMark mark;
	
	public int length;
	public TokenType type;

	public Token(TokenType type, SourceMark mark) {
		this.type = type;
		this.mark = mark;
	}
}
