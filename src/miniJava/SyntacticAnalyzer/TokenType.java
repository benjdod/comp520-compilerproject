package miniJava.SyntacticAnalyzer;

import jdk.internal.module.IllegalAccessLogger.Mode;

public enum TokenType {
	
	/* text and file things */
	
	None,
	Error,
	Eol,		// \n or \r\n
	Eot,		// probably \0
	
	/* class and access keywords */
	
	Class,
	Public,
	Private,
	Static,
	
	/* data types and boolean values */
	
	Int,
	Boolean,
	Void,
	True,
	False,

	/* program control flow */
	
	Return,
	If,
	Else,
	While,
	Continue,
	Break,
	
	This,
	New,
	Null,
	
	/* operator type things */
	
	RCaret,			// >
	LCaret,			// <
	EqualEqual,		// ==
	LessEqual,		// <=
	GreaterEqual,	// >=
	NotEqual,		// !=
	
	AmpAmp,			// &&
	BarBar,			// ||
	Not,			// !
	
	/* Not supported yet, but hopefully they 
	 * won't be too hard to implement.
	 * 
	PlusPlus,		// ++
	MinusMinus,		// --
	*/
	PlusEqual,		// +=
	MinusEqual,		// -=
	SlashEqual,		// /=
	StarEqual,		// *=
	ModEqual,		// %=
	
	/*
	Amp,			// &
	Bar,			// |
	Tilde,			// ~
	*/
	
	Plus,			// +
	Minus,			// -
	Star,			// *
	FSlash,			// /
	Modulo,			// %
	
	Equal,			// =
	
	/* punctuation and structure */
	
	Dot,			// .
	Comma,			// ,
	Semicolon,		// ;
	Colon,			// :
	QuestionMark,	// ?
	DoubleQuote,	// "
	
	LBracket,		// [
	RBracket,		// ]
	LBrace,			// {
	RBrace,			// }
	LParen,			// (
	RParen,			// )
	
	/* id, number */
	
	Ident,
	Num
}
