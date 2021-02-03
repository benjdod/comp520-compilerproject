package syntacticAnalyzer;

import syntacticAnalyzer.TokenType;

public class Token {
	
	public long index;
	
	public int length;
	public TokenType type;
	
	public Token(TokenType type, int source_index) {
		this.index = source_index;
		this.type = type;

	}
	
	public Token(TokenType type) {
		this(type,0);
	}
	
	public Token() {
		this(TokenType.None,  0);
	}
}
