package miniJava.SyntacticAnalyzer;

public class SourcePosition {
    public int line;
    public int column;

    public SourcePosition(int line, int column) {
        this.line = line;
        this.column = column;
    }
    
    public String toString() {
    	return String.format("(%d, %d)", this.line, this.column);
    }
    
    public SourcePosition makeCopy() {
    	return new SourcePosition(this.line, this.column);
    }
}
