package miniJava.syntacticAnalyzer;

public class SourceMark {
    public int line;
    public int column;

    public SourceMark(int line, int column) {
        this.line = line;
        this.column = column;
    }
    
    public String toString() {
    	return String.format("(%d, %d)", this.line, this.column);
    }
}
