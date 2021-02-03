package syntacticAnalyzer;

import utility.CharTrie;
import syntacticAnalyzer.TokenType;
import syntacticAnalyzer.Token;

public class Scanner {
	
	private String _inputstring;
	private int _inputlen;
	private int _index;
	
	private static CharTrie<TokenType> _keywordtree = null;
	
	private static void initKeywordtree() {
		_keywordtree = new CharTrie<TokenType>();
		
		_keywordtree.insert("while", TokenType.While);
		_keywordtree.insert("class", TokenType.Class);
		_keywordtree.insert("public", TokenType.Public);
		_keywordtree.insert("private", TokenType.Private);
		_keywordtree.insert("static", TokenType.Static);
		_keywordtree.insert("int", TokenType.Int);
		_keywordtree.insert("boolean", TokenType.Boolean);
		_keywordtree.insert("void", TokenType.Void);
		_keywordtree.insert("return", TokenType.Return);		
		_keywordtree.insert("if", TokenType.If);
		_keywordtree.insert("else", TokenType.Else);
		_keywordtree.insert("while", TokenType.While);
		_keywordtree.insert("this", TokenType.This);
		_keywordtree.insert("new", TokenType.New);
		_keywordtree.insert("true", TokenType.True);
		_keywordtree.insert("false", TokenType.False);
	}
	
	public Scanner() {
		if (_keywordtree == null) {
			initKeywordtree();
		}
	}
	
	public Scanner(String s) {
		this();
		this.load(s);
	}
	
	public void load(String s) {
		this._inputstring = s;
		this._index = 0;
		this._inputlen = this._inputstring.length();
	}
	
	private void advanceChar() {
		_index++;
	}
	
	private char currentChar() {
		if (_index >= _inputlen) {
			return '\0';
		} else {
			return _inputstring.charAt(_index);
		}
	}
	
	private char nextChar() {
		if (_index >= _inputlen - 1) {
			return '\0';
		} else {
			return _inputstring.charAt(_index + 1);
		}
	}
	
	public boolean hasNext() {
		int temp_index = _index;
		int last_index = _inputstring.length() - 1;
		while (
			temp_index <= last_index
			) {
			if (Character.isWhitespace(_inputstring.charAt(temp_index))) {
				temp_index++;
			} else return true;
		}
		return false;
	}
	
	public Token next() {
		
		char current = currentChar();
		char next = nextChar();
		int start_index = _index;
		Token out = null;

		
		while (true) {

			if (currentChar() == ' ' || currentChar() == '\t') {
				_index++;
			} else if (currentChar() == '/') {
				if (nextChar() == '/') {
					this._index += 2;
					while (currentChar() != '\n' && currentChar() != '\r' && currentChar() != '\0') _index++;
				} else if (nextChar() == '*') {
					this._index += 2;
					while ((current = currentChar()) != '*' && (next = nextChar()) != '/') { 
						if (current == '\0') {
							// if a multiline comment is unterminated before eof, 
							// it's an error
							return new Token(TokenType.Error, start_index);
						}
						_index++; 
					}
					_index += 2;
				}
			} else {
				break;
			}
		}
		
		if (current == '\0') return new Token(TokenType.Eot, _index);
				
			// this accepts \n and \r\n but not \r 
		if (current == '\n') {
			out = new Token(TokenType.Eol, _index);
		} else if (current == '\r' && next == '\n') {
			out = new Token(TokenType.Eol, _index);
			_index++;
		}
		
		// have we found the token?
		if (out != null) {
			_index++;
			return out;
		}
		
		current = currentChar();
		next = nextChar();
		start_index = _index;
		
		// one-character tokens
		switch (current) {
			case '(': out =  new Token(TokenType.LParen, _index); break;
			case ')': out =  new Token(TokenType.RParen,  _index); break;
			case '[': out =  new Token(TokenType.LBracket, _index); break;
			case ']': out =  new Token(TokenType.RBracket, _index); break;
			case '{': out =  new Token(TokenType.LBrace, _index); break;
			case '}': out =  new Token(TokenType.RBrace, _index); break;
			case '.': out =  new Token(TokenType.Dot, _index); break;
			case ',': out =  new Token(TokenType.Comma, _index); break;
			case ';': out =  new Token(TokenType.Semicolon, _index); break;
			
			// these might need to be moved if support is added 
			// for +=, -= and family
			case '+': out =  new Token(TokenType.Plus, _index); break;
			case '-': out =  new Token(TokenType.Minus, _index); break;
			case '*': out =  new Token(TokenType.Star, _index); break;
			case '/': out =  new Token(TokenType.FSlash, _index); break;
		}
		
		// have we found the token?
		if (out != null) {
			_index++;
			return out;
		}
		
		// one or two character tokens...
		
		if (current == '=') {
			if (next == '=') {
				out = new Token(TokenType.EqualEqual, start_index);
				_index++;
			} else {
				out = new Token(TokenType.Equal, start_index);
			}
		} else if (current == '<') {
			if (next == '=') {	
				out = new Token(TokenType.LessEqual, _index);
				_index++;
			} else {	
				out = new Token(TokenType.LCaret, _index);
			}
		} else if (current == '>') {
			if (next == '=') {
				out = new Token(TokenType.GreaterEqual, _index);
				_index++;
			} else {
				out = new Token(TokenType.RCaret, _index);
			}
		} else if (current == '!') {
			if (next == '=') {
				out = new Token(TokenType.NotEqual, _index);
				_index++;
			} else {
				out = new Token(TokenType.Not, _index);
			}
		}
		
		// token?
		if (out != null) {
			_index++;
			return out;
		}
		
		// OK, onto the keywords, ident, and num...
		// first, slice out the token 
		
		boolean keyword_possible = true;
		
		if (Character.isDigit(_inputstring.charAt(_index))) {
			while (Character.isDigit((current = currentChar()))) {
				_index++;
			}
			if (Character.isAlphabetic(current)) {
				return new Token(TokenType.Error, start_index);
			} else {
				return new Token(TokenType.Num, start_index);
			}
		} else if (Character.isAlphabetic(currentChar())) {
			while (true) {
				if (Character.isAlphabetic((current = currentChar()))) {
					advanceChar();
				} else if (Character.isDigit(current) || current == '_') {
					keyword_possible = false;
					advanceChar();
				} else {
					break;
				}
			}
		}

		if (_index - start_index > _keywordtree.getMaxLength()) {
			keyword_possible = false;
		} 

		String slice = _inputstring.substring(start_index, _index);
		
		if (!keyword_possible) {
			return new Token(TokenType.Ident, start_index);
		}
		
		TokenType kwtype = _keywordtree.get(slice);
		
		if (kwtype != null) {
			out =  new Token(kwtype, start_index);
		} else {
			out = new Token(TokenType.Ident, start_index);
		}
		
		//_index += slice.length();
		return out;
			
		// if we've made it here, there's an error
	}
	
}