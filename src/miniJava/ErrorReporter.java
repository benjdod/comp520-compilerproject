package miniJava;

import java.util.ArrayList;
import java.util.Iterator;

public class ErrorReporter {

    private boolean _has_errors;
    private int _num_errors;
    private ArrayList<String> _errors;

    public ErrorReporter() {
        _num_errors = 0;
        _errors = new ArrayList<String>();
    }

    public void report(String s) {
        _errors.add(s);
        _num_errors++;
        _has_errors = true;
    }

    public boolean hasErrors() {
        return _has_errors;
    }

    public int getErrorCount() {
        return _num_errors;
    }

    public Iterator<String> getErrors() {
        return _errors.iterator();
    }

    public void print() {
        for (String s : _errors) {
            System.err.println(s);
        }
    }
}
