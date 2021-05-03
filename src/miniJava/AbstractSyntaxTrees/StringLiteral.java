package miniJava.AbstractSyntaxTrees;
import miniJava.SyntacticAnalyzer.Token;


public class StringLiteral extends Terminal {

    public String content;

    public StringLiteral(Token t, String content) {
        super(t);
        this.content = content;
    }

    @Override
    public <A, R> R visit(Visitor<A, R> v, A o) {
        return v.visitStringLiteral(this,o);
    }
    
}
