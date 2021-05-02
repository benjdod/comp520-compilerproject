package miniJava.SyntacticAnalyzer;

public class SourcePosition {
    public int line;
    public int column;

    public static final SourcePosition NOPOS = new SourcePosition(0, 0);

    public SourcePosition(int line, int column) {
        this.line = line;
        this.column = column;
    }
    
    public String toString() {
    	return "(" + (this.line > 0 ? this.line : "_") + ", " + (this.column > 0 ? this.column : "_") + ")";
    }
    
    public SourcePosition makeCopy() {
    	return new SourcePosition(this.line, this.column);
    }
}
