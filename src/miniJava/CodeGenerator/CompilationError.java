package miniJava.CodeGenerator;

import miniJava.SyntacticAnalyzer.SourceError;
import miniJava.SyntacticAnalyzer.SourcePosition;

public class CompilationError extends SourceError {

	private static final long serialVersionUID = 1L;

	public CompilationError(String message, SourcePosition location) {
		super(message,location);
	}

	
	public CompilationError(String message, String hint, SourcePosition location) {
		super(message,hint,location);
	}

}
