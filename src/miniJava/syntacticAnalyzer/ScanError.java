package miniJava.SyntacticAnalyzer;

public class ScanError extends SourceError {
    private static final long serialVersionUID = 1L;	

    public ScanError(String message, SourcePosition location) {
        super(message, location);
    }

    public ScanError(String message, String hint, SourcePosition location) {
        super(message, hint, location);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
