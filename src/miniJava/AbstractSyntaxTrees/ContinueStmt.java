package miniJava.AbstractSyntaxTrees;

import miniJava.SyntacticAnalyzer.SourcePosition;

public class ContinueStmt extends LoopControlStmt {
    public ContinueStmt(SourcePosition posn) {
        super(posn);
    }

    @Override
    public <A, R> R visit(Visitor<A, R> v, A o) {
        return v.visitContinueStmt(this, o);
    }
}