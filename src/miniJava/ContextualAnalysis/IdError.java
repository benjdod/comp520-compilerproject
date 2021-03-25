package miniJava.ContextualAnalysis;

import miniJava.AbstractSyntaxTrees.Identifier;
import miniJava.SyntacticAnalyzer.*;

public class IdError extends SourceError {
    
    private static final long serialVersionUID = 1L;	

    protected Identifier id;

    public IdError(String message, Identifier id) {
        super(message, id.posn);
        this.id = id;
    }

    public IdError(String message, SourcePosition location) {
        super(message, location);
    }

    public IdError(String message, String hint, SourcePosition location) {
        super(message, hint, location);
    }
    
}
