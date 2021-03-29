package miniJava.SyntacticAnalyzer;

import miniJava.CompilerError;

public class SourceError extends CompilerError {

    public SourcePosition _location;

    private static final long serialVersionUID = 1L;	
    
    public SourceError(String message, SourcePosition location) {
        super(message);
        _location = location;
    }

    public SourceError(String message, String hint, SourcePosition location) {
        super(message, hint);
        _location = location;
    }

    @Override
    public String getMessage() {
        return this.getClass().getSimpleName() + " at " + _location.toString() + ":\n" + _errormessage + (_hint.length() > 0 ? ("\n" + _hint) : "");
    }
}
