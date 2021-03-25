package miniJava.SyntacticAnalyzer;

import miniJava.ErrorReporter;
import miniJava.SyntacticAnalyzer.ScanError;
import miniJava.utility.CharTrie;

import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class Scanner {
	
	private SourceReader _reader;
	private int _index;
	private int _line;
	private int _column;
	private char _current;
	private char _next;
	private boolean _spacebefore;
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
		_keywordtree.insert("null", TokenType.Null);
	}
	
	public Scanner(Reader r, ErrorReporter reporter) {
		if (_keywordtree == null) {
			initKeywordtree();
		}
		this._reporter = reporter;
		this.load(r);
	}
	
	public Scanner(String s, ErrorReporter reporter) {
		this(new SourceReader(new StringReader(s)), reporter);
	}
	
	public void reset() {
		this._reader = null;
		this._current = this._next = '\0';
		this._index = -2;
		this._line = 1;
		this._column = 1;
		this._errorstate = false;
		this._spacebefore = false;
	}
	
	// loads and primes the scanner for getting tokens
	public void load(Reader r) {
		/* set the Reader r as the Scanner's stream reader.
		 * Then set the index to -2, and prime it twice to set 
		 * the _current and _next char fields.  */
		this.reset();
		this._reader = new SourceReader(r);
		advance(2);
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

	// courtesy method to fill in line and column
	private Token makeToken(TokenType type, String spelling, SourcePosition mark) {
		return new Token(type, mark, spelling);
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

				_next = (char) readchar;

				// replace \r\n line terminators with \n
				if ((_current == '\r' && _next == '\n')) {
					advance();
				} else if (_current == '\n') {
					this._line++;
					this._column = 1;
				} else {
					this._column++;
				}
			} catch (IOException e) {
				_reporter.report(e.toString());
				_current = _next = '\0';
			} catch (SourceError e) {
				_reporter.report(e);
			}

			_index++;
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
		SourcePosition mark = _reader.getMark();
		Token out = null;
		
		_spacebefore = false;
		
		while (true) {

			if (Character.isWhitespace(currentChar())) {
				advance();
				this._spacebefore = true;
			} else if (currentChar() == '/') {
				SourcePosition comment_start = _reader.getMark();
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
							_reporter.report(new ScanError("Unterminated multi-line comment starting at "
									+ comment_start.toString(), 
									mark));
							return new Token(TokenType.Error, mark);
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
		
		if (currentChar() == '\0') return makeToken(TokenType.Eot, "\0", _reader.getMark());
					
		current = currentChar();
		next = nextChar();
		mark = _reader.getMark();
		
		// one-character tokens
		switch (current) {
			case '(': out =  makeToken(TokenType.LParen, "(", mark); break;
			case ')': out =  makeToken(TokenType.RParen, ")", mark); break;
			case '[': out =  makeToken(TokenType.LBracket, "[", mark); break;
			case ']': out =  makeToken(TokenType.RBracket, "]", mark); break;
			case '{': out =  makeToken(TokenType.LBrace, "{", mark); break;
			case '}': out =  makeToken(TokenType.RBrace, "}", mark); break;
			case '.': out =  makeToken(TokenType.Dot, ".", mark); break;
			case ',': out =  makeToken(TokenType.Comma, ",", mark); break;
			case ';': out =  makeToken(TokenType.Semicolon, ";", mark); break;
			
			// these might need to be moved if support is added 
			// for +=, -= and family
			case '+': out =  makeToken(TokenType.Plus, "+", mark); break;
			case '-': out =  makeToken(TokenType.Minus, "-", mark); break;
			case '*': out =  makeToken(TokenType.Star, "*", mark); break;
			case '/': out =  makeToken(TokenType.FSlash, "/", mark); break;
		}
		
		// have we found the token?
		if (out != null) {
			advance();
			return out;
		}
		
		// one or two character tokens...
		
		if (current == '=') {
			if (next == '=') {
				out = makeToken(TokenType.EqualEqual, "==", mark);
				advance();
			} else {
				out = makeToken(TokenType.Equal, "=", mark);
			}
		} else if (current == '<') {
			if (next == '=') {	
				out = makeToken(TokenType.LessEqual, "<=", mark);
				advance();
			} else {	
				out = makeToken(TokenType.LCaret, "<", mark);
			}
		} else if (current == '>') {
			if (next == '=') {
				out = makeToken(TokenType.GreaterEqual, ">=", mark);
				advance();
			} else {
				out = makeToken(TokenType.RCaret, ">", mark);
			}
		} else if (current == '!') {
			if (next == '=') {
				out = makeToken(TokenType.NotEqual, "!=", mark);
				advance();
			} else {
				out = makeToken(TokenType.Not, "!", mark);
			}
		} else if (current == '|') {
			if (next == '|') {
				out = makeToken(TokenType.BarBar, "||", mark);
				advance();
			} else {
				_reporter.report(new ScanError("Bitwise '|' operator is not supported", mark));
				return new Token(TokenType.Error, mark);
			}
			
		} else if (current == '&') {
			if (next == '&') {
				out = makeToken(TokenType.AmpAmp, "&&", mark);
				advance();
			} else {
				_reporter.report(new ScanError("Bitwise '&' operator is not supported", mark));
				return new Token(TokenType.Error, mark);
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
				sb.append(current);
				advance();
			}
			if (Character.isAlphabetic(current)) {
				return makeToken(TokenType.Error, "", mark);
			} else {
				out = makeToken(TokenType.Num, sb.toString(), mark);
				return out;
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
		} else {
			_reporter.report(new ScanError(String.format("character %c is not valid", current), mark));
		}

		String slice = sb.toString();
		
		if (!keyword_possible) {
			out = makeToken(TokenType.Ident, sb.toString(), mark);
			return out;
		}
		
		TokenType keywordtype = _keywordtree.get(slice);
		
		if (keywordtype != null) {
			out =  makeToken(keywordtype, sb.toString(), mark);
		} else {
			out = makeToken(TokenType.Ident, sb.toString(), mark);
		}
		
		return out;
		// if we've made it here, there's an error
	}
	
	public boolean spaceBefore() {
		return this._spacebefore;
	}
	
	public String getBufferLines() {
		return _reader.getBufferLines();
	}
	
}