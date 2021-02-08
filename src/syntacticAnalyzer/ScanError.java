package syntacticAnalyzer;

public class ScanError extends SourceError {
    private static final long serialVersionUID = 1L;	

    public ScanError(String message, SourceMark location) {
        super(message, location);
    }

    public ScanError(String message, String hint, SourceMark location) {
        super(message, hint, location);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
