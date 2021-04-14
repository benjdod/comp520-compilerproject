package miniJava.ContextualAnalysis;

import miniJava.SyntacticAnalyzer.SourceError;
import miniJava.SyntacticAnalyzer.SourcePosition;

public class TypeError extends SourceError {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    public TypeError(String message, SourcePosition location) {
        super(message, location);
    }

    public TypeError(String message, String hint, SourcePosition location) {
        super(message, hint, location);
    }
    

}
