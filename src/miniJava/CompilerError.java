package miniJava;

public class CompilerError extends Error {

    private static final long serialVersionUID = 1L;	

    protected String _errormessage;
    protected String _hint;

    public CompilerError(String message, String hint) {
        this._errormessage = message;
        this._hint = hint;
    }

    public CompilerError(String message) {
        this._errormessage = message;
        this._hint = "";
    }

    @Override
    public String getMessage() {
        return  _errormessage + (_hint.length() > 0 ? ("\nHint: " + _hint) : "");
    }

}

