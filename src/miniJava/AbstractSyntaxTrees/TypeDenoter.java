/**
 * miniJava Abstract Syntax Tree classes
 * @author prins
 * @version COMP 520 (v2.2)
 */
package miniJava.AbstractSyntaxTrees;

import miniJava.SyntacticAnalyzer.SourcePosition;

abstract public class TypeDenoter extends AST {
    
    public TypeDenoter(TypeKind type, SourcePosition posn){
        super(posn);
        typeKind = type;
    }
    
    public TypeKind typeKind;

    public boolean equals(TypeDenoter td) {

        if (td.typeKind == TypeKind.UNSUPPORTED || this.typeKind == TypeKind.UNSUPPORTED) return false;
        if (td.typeKind == TypeKind.ERROR || this.typeKind == TypeKind.ERROR) return true;

        if (td.typeKind == TypeKind.INT && this.typeKind == TypeKind.INT) return true;

        if (td.typeKind == TypeKind.BOOLEAN && this.typeKind == TypeKind.BOOLEAN) return true;

        if (td.typeKind == TypeKind.ARRAY && this.typeKind == TypeKind.ARRAY) {
            ArrayType adt = (ArrayType) td;
            ArrayType tdt = (ArrayType) this;

            if (adt.eltType.equals(tdt.eltType)) return true;
        }

        if (td.typeKind == TypeKind.CLASS && this.typeKind == TypeKind.CLASS) {
            ClassType cdt = (ClassType) td;
            ClassType tdt = (ClassType) td;

            if (cdt.className.spelling.contentEquals(tdt.className.spelling)) return true;
        }

        return false;
    }
    
}

        