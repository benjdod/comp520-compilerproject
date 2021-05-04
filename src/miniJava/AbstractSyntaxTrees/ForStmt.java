package miniJava.AbstractSyntaxTrees;

import miniJava.SyntacticAnalyzer.SourcePosition;


public class ForStmt extends Statement
{
    public ForStmt(StatementList declList, Expression cond, Statement body, StatementList inc, SourcePosition posn){
        super(posn);
        this.declList = declList;
        this.cond = cond;
        this.body = body;
        this.inc = inc; 
    }
        
    public <A,R> R visit(Visitor<A,R> v, A o) {
        return v.visitForStmt(this, o);
    }

    public StatementList declList;
    public Expression cond;
    public Statement body;
    public StatementList inc;
}
