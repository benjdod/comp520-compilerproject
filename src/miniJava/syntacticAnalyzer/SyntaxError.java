package miniJava.syntacticAnalyzer;
public class SyntaxError extends SourceError {

    private static final long serialVersionUID = 1L;	

    public SyntaxError(String message, SourceMark location) {
        super(message, location);
    }

    public SyntaxError(String message, String hint, SourceMark location) {
        super(message, hint, location);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
