package syntacticAnalyzer;
public class SyntaxError extends Error {
    private static final long serialVersionUID = 1L;	

    private String message;

    public SyntaxError(String s) {
        message = s;
    }

    public SyntaxError() {
        message = "";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
