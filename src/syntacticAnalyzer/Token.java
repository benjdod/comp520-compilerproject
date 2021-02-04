package syntacticAnalyzer;

import syntacticAnalyzer.TokenType;

public class Token {
	
	public long index;
	public int line;
	public int column;
	
	public int length;
	public TokenType type;

	public Token(TokenType type, int source_index, int line, int column) {
		this.index = source_index;
		this.type = type;
		this.line = line;
		this.column = column;
	}
	
	public Token(TokenType type, int source_index) {
		this(type, source_index, 0,0);
	}
	
	public Token(TokenType type) {
		this(type,0,0,0);
	}
	
	public Token() {
		this(TokenType.None,  0,0,0);
	}
}
