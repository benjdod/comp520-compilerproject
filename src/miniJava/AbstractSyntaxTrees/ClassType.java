/**
 * miniJava Abstract Syntax Tree classes
 * @author prins
 * @version COMP 520 (v2.2)
 */
package miniJava.AbstractSyntaxTrees;

import miniJava.SyntacticAnalyzer.SourcePosition;

public class ClassType extends TypeDenoter
{
    public ClassType(Identifier cn, SourcePosition posn){
        super(TypeKind.CLASS, posn);
        className = cn;
    }

    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + super.hashCode();
        hash = hash * 31 + className.spelling.hashCode();
        return hash;
    }

    public boolean equals(Object o) {
        if (o instanceof ClassType) {
            ClassType ct = (ClassType) o;

            return (
                this.className.spelling.contentEquals(ct.className.spelling) 
            );
        } else {
            return false;
        }
    }
            
    public <A,R> R visit(Visitor<A,R> v, A o) {
        return v.visitClassType(this, o);
    }

    public Identifier className;
}
