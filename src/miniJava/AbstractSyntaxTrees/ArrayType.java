/**
 * miniJava Abstract Syntax Tree classes
 * @author prins
 * @version COMP 520 (v2.2)
 */

package miniJava.AbstractSyntaxTrees;

import miniJava.SyntacticAnalyzer.SourcePosition;

public class ArrayType extends TypeDenoter {

	    public ArrayType(TypeDenoter eltType, SourcePosition posn){
	        super(TypeKind.ARRAY, posn);
	        this.eltType = eltType;
	    }

		@Override
		public int hashCode() {
			int hash = 17;
			hash *= 31;
			hash += super.hashCode();
			hash *= 31;
			hash += this.eltType.hashCode();
			return hash;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof ArrayType) {
				ArrayType a = (ArrayType) o;
				return this.eltType.equals(a.eltType);
			} else {
				return false;
			}
		}

		@Override
		public String toString() {
			return this.eltType.toString() + "[]";
		}
	        
	    public <A,R> R visit(Visitor<A,R> v, A o) {
	        return v.visitArrayType(this, o);
	    }

		public ArrayType copy() {
			return new ArrayType(eltType.copy(), posn.makeCopy());
		}

	    public TypeDenoter eltType;
	}

