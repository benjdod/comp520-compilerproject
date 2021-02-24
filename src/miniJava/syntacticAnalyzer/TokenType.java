package miniJava.SyntacticAnalyzer;

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
	
	This,
	New,
	
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
	PlusEqual,		// +=
	MinusEqual,		// -=
	SlashEqual,		// /=
	StarEqual,		// *=
	
	
	Amp,			// &
	Bar,			// |
	Tilde,			// ~
	*/
	
	Plus,			// +
	Minus,			// -
	Star,			// *
	FSlash,			// /
	
	Equal,			// =
	
	/* punctuation and structure */
	
	Dot,			// .
	Comma,			// ,
	Semicolon,		// ;
	Colon,			// :
	
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
