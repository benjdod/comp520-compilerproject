package miniJava.AbstractSyntaxTrees;

import miniJava.SyntacticAnalyzer.SourcePosition;

public class TernaryExpr extends Expression
{
    public TernaryExpr(Expression cond, Expression trueExpr, Expression falseExpr, SourcePosition posn){
        super(posn);
        this.cond = cond;
        this.trueExpr = trueExpr;
        this.falseExpr = falseExpr;
    }
        
    public <A,R> R visit(Visitor<A,R> v, A o) {
        return v.visitTernaryExpr(this, o);
    }
    
    public Expression cond;
    public Expression trueExpr;
    public Expression falseExpr;
}