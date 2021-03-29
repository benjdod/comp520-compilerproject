package miniJava;

import java.util.ArrayList;
import java.util.Iterator;

public class ErrorReporter {

    private boolean _has_errors;
    private int _num_errors;
    private ArrayList<Error> _errors;

    public ErrorReporter() {
        _num_errors = 0;
        _errors = new ArrayList<Error>();
    }

    public void report(String message) {
        _errors.add(new CompilerError(message));
        _num_errors++;
        _has_errors |= true;
    }

    public void report(String message, String hint) {
        _errors.add(new CompilerError(message, hint));
        _num_errors++;
        _has_errors |= true;
    }

    public void report(Error e) {
        _errors.add(e);
        _num_errors++;
        _has_errors |= true;
    }

    public boolean hasErrors() {
        return _has_errors;
    }

    public int getErrorCount() {
        return _num_errors;
    }

    public Iterator<Error> getErrors() {
        return _errors.iterator();
    }

    public Error getFirst() {
        try {
            return _errors.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void print() {
        for (Error e : _errors) {
            System.err.println(e.getMessage());
        }
    }
    
    public void printFirst() {
        try {
            System.out.println(_errors.get(0).getMessage());
        } catch (Exception e) {;}
    }
}
