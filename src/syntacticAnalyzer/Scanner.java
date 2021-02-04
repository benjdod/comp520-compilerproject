package syntacticAnalyzer;

import miniJava.ErrorReporter;
import utility.CharTrie;
import syntacticAnalyzer.TokenType;
import syntacticAnalyzer.Token;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class Scanner {
	
	private Reader _reader;
	private int _index;
	private int _line;
	private int _column;
	private char _current;
	private char _next;
	private boolean _errorstate;
	private ErrorReporter _reporter;
	
	private static CharTrie<TokenType> _keywordtree = null;
	private static int INDEX_NOT_READY = -2;
	
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
	
	public Scanner(Reader r, ErrorReporter reporter) {
		if (_keywordtree == null) {
			initKeywordtree();
		}
		this._reporter = reporter;
		this.load(r);
	}
	
	public Scanner(String s, ErrorReporter reporter) {
		this(new StringReader(s), reporter);
	}
	
	public void reset() {
		this._reader = null;
		this._current = this._next = '\0';
		this._index = -2;
		this._line = 1;
		this._column = 1;
		this._errorstate = false;
	}
	
	// loads and primes the scanner for getting tokens
	public void load(Reader r) {
		/* set the Reader r as the Scanner's stream reader.
		 * Then set the index to -2, and prime it twice to set 
		 * the _current and _next char fields.  */
		this.reset();
		this._reader = r;
		this._index = -2;
		this.advance(2);
		this._errorstate = false;
	}
	
	// probably not necessary
	public boolean ready() {
		try {
			return this._index != INDEX_NOT_READY && this._reader.ready();
		} catch (IOException e) {
			System.err.println(e);
			return false;
		}
	}

	// courtesy method over the reporter to add "Scan error: " before the string
	private void reportError(String s) {
		_reporter.report("Scan error: " + s);
	}	

	// take snapshot of the current scanner position
	private ScannerMark getMark() {
		return new ScannerMark(this._index, this._line, this._column);
	}

	// courtesy method to fill in line and column
	private Token makeToken(TokenType type, ScannerMark mark) {
		return new Token(type, mark.index, mark.line, mark.column);
	}
	
	private void advance() {
		advance(1);
	}
	
	private void advance(int times) {

		if (this._errorstate) {
			_next = _current = '\0';
			return;
		}

		while (times-- > 0) {
			_current = _next;
			try {
				
				int readchar = _reader.read();
				
				// if readchar is not in the ASCII range, 
				// report the error and brickwall the scanner
				if (readchar > 127) {
					reportError("bad input encoding.");
					_current = '\0';
					_next = '\0';
					this._errorstate = true;
				}
				_next = readchar > -1 ? (char) readchar : '\0';
			} catch (IOException e) {
				System.err.println(e);
				_next = '\0';
			}
			_index++;
			
			if (_current == '\n' || (_current == '\r' && _next == '\n')) {
				this._line++;
				this._column = 1;
			} else {
				this._column++;
			}
		}
	}
	
	private char currentChar() {
		return _current;
	}
	
	private char nextChar() {
		return _next;
	}
	
	public boolean hasNext() {
		return _next != '\0';
	}
	
	public Token next() {

		char current = currentChar();
		char next = nextChar();
		ScannerMark mark = getMark();
		int start_index = mark.index;
		Token out = null;
		
		while (true) {

			if (Character.isWhitespace(currentChar())) {
				advance();
			} else if (currentChar() == '/') {
				if (nextChar() == '/') {
					advance(2);
					while (currentChar() != '\n' && currentChar() != '\r' && currentChar() != '\0') advance();
					if (currentChar() == '\n') advance();
					else if (currentChar() == '\r' && nextChar() == '\n') advance(2);
				} else if (nextChar() == '*') {
					advance(2);
					while ((current = currentChar()) != '*' || (next = nextChar()) != '/') { 
						if (current == '\0') {
							// if a multiline comment is unterminated before eof, 
							// it's an error
							return new Token(TokenType.Error, start_index);
						}
						advance(); 
					}
					advance(2);
				} else {
					break;
				}
			} else {
				break;
			}
		}

		current = currentChar();
		next = nextChar();
		start_index = _index;
		
		if (current == '\0') return makeToken(TokenType.Eot, mark);
				
		/*
			// this accepts \n and \r\n but not \r 
		if (current == '\n') {
			out = makeToken(TokenType.Eol, mark);
		} else if (current == '\r' && next == '\n') {
			out = makeToken(TokenType.Eol, mark);
			advance();
		}
		
		// have we found the token?
		if (out != null) {
			advance();
			return out;
		}
		*/
		
		current = currentChar();
		next = nextChar();
		mark = getMark();
		start_index = mark.index;
		
		// one-character tokens
		switch (current) {
			case '(': out =  makeToken(TokenType.LParen, mark); break;
			case ')': out =  makeToken(TokenType.RParen, mark); break;
			case '[': out =  makeToken(TokenType.LBracket, mark); break;
			case ']': out =  makeToken(TokenType.RBracket, mark); break;
			case '{': out =  makeToken(TokenType.LBrace, mark); break;
			case '}': out =  makeToken(TokenType.RBrace, mark); break;
			case '.': out =  makeToken(TokenType.Dot, mark); break;
			case ',': out =  makeToken(TokenType.Comma, mark); break;
			case ';': out =  makeToken(TokenType.Semicolon, mark); break;
			
			// these might need to be moved if support is added 
			// for +=, -= and family
			case '+': out =  makeToken(TokenType.Plus, mark); break;
			case '-': out =  makeToken(TokenType.Minus, mark); break;
			case '*': out =  makeToken(TokenType.Star, mark); break;
			case '/': out =  makeToken(TokenType.FSlash, mark); break;
		}
		
		// have we found the token?
		if (out != null) {
			advance();
			return out;
		}
		
		// one or two character tokens...
		
		if (current == '=') {
			if (next == '=') {
				out = makeToken(TokenType.EqualEqual, mark);
				advance();
			} else {
				out = makeToken(TokenType.Equal, mark);
			}
		} else if (current == '<') {
			if (next == '=') {	
				out = makeToken(TokenType.LessEqual, mark);
				advance();
			} else {	
				out = makeToken(TokenType.LCaret, mark);
			}
		} else if (current == '>') {
			if (next == '=') {
				out = makeToken(TokenType.GreaterEqual, mark);
				advance();
			} else {
				out = makeToken(TokenType.RCaret, mark);
			}
		} else if (current == '!') {
			if (next == '=') {
				out = makeToken(TokenType.NotEqual, mark);
				advance();
			} else {
				out = makeToken(TokenType.Not, mark);
			}
		}
		
		// token?
		if (out != null) {
			advance();
			return out;
		}
		
		// OK, onto the keywords, ident, and num...
		// first, slice out the token 
		
		StringBuilder sb = new StringBuilder();
		
		boolean keyword_possible = true;
		
		if (Character.isDigit(currentChar())) {
			while (Character.isDigit((current = currentChar()))) {
				advance();
			}
			if (Character.isAlphabetic(current)) {
				return makeToken(TokenType.Error, mark);
			} else {
				return makeToken(TokenType.Num, mark);
			}
		} else if (Character.isAlphabetic(currentChar())) {
			while (true) {
				if (Character.isAlphabetic((current = currentChar()))) {
					sb.append(current);
					advance();
				} else if (Character.isDigit(current) || current == '_') {
					keyword_possible = false;
					sb.append(current);
					advance();
				} else {
					break;
				}
			}
		}

		String slice = sb.toString();
		
		if (!keyword_possible) {
			return makeToken(TokenType.Ident, mark);
		}
		
		TokenType keywordtype = _keywordtree.get(slice);
		
		if (keywordtype != null) {
			out =  makeToken(keywordtype, mark);
		} else {
			out = makeToken(TokenType.Ident, mark);
		}
		
		return out;
		// if we've made it here, there's an error
	}
	
}

class ScannerMark {
	public int line;
	public int column;
	public int index;

	public ScannerMark(int index, int line, int column) {
		this.index = index;
		this.line = line;
		this.column = column;
	}
}