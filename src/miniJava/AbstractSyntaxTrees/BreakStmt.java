package miniJava.AbstractSyntaxTrees;

import miniJava.SyntacticAnalyzer.SourcePosition;

public class BreakStmt extends LoopControlStmt {
    public BreakStmt(SourcePosition posn) {
        super(posn);
    }

    @Override
    public <A, R> R visit(Visitor<A, R> v, A o) {
        return v.visitBreakStmt(this, o);
    }
}
