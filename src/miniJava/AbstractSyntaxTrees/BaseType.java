/**
 * miniJava Abstract Syntax Tree classes
 * @author prins
 * @version COMP 520 (v2.2)
 */
package miniJava.AbstractSyntaxTrees;

import miniJava.SyntacticAnalyzer.SourcePosition;

public class BaseType extends TypeDenoter
{
    public BaseType(TypeKind t, SourcePosition posn){
        super(t, posn);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BaseType) {
            BaseType b = (BaseType) o;
            return this.typeKind == b.typeKind;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String s = typeKind.toString();
        return s.charAt(0) + s.substring(1).toLowerCase();
    }
    
    public <A,R> R visit(Visitor<A,R> v, A o) {
        return v.visitBaseType(this, o);
    }

    public BaseType copy() {
        return new BaseType(typeKind, posn.makeCopy());
    }
}
