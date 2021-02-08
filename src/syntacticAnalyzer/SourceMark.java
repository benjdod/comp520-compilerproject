package syntacticAnalyzer;

public class SourceMark {
    public int line;
    public int column;
    public int index;

    public SourceMark(int line, int column, int index) {
        this.line = line;
        this.column = column;
        this.index = index;
    }
}
