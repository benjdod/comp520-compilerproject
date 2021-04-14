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
        return equals(td, this);
    }

    public static boolean equals(TypeDenoter a, TypeDenoter b) {
        if (a.typeKind == TypeKind.UNSUPPORTED || b.typeKind == TypeKind.UNSUPPORTED) return false;
        if (a.typeKind == TypeKind.ERROR || b.typeKind == TypeKind.ERROR) return true;

        if (a.typeKind == TypeKind.INT && b.typeKind == TypeKind.INT) {
            return true;
        }

        if (a.typeKind == TypeKind.BOOLEAN && b.typeKind == TypeKind.BOOLEAN) return true;
        if (a.typeKind == TypeKind.VOID && b.typeKind == TypeKind.VOID) return true;

        if (a.typeKind == TypeKind.ARRAY || b.typeKind == TypeKind.ARRAY) {

            if (a.typeKind == TypeKind.ARRAY && b.typeKind == TypeKind.ARRAY) {
                ArrayType adt = (ArrayType) a;
                ArrayType bdt = (ArrayType) b;

                if (equals(adt.eltType, bdt.eltType)) return true;
            }

            if (a.typeKind == TypeKind.NULL || b.typeKind == TypeKind.NULL) {
                return true;
            }
        }

        if (a.typeKind == TypeKind.CLASS || b.typeKind == TypeKind.CLASS) {

            if (a.typeKind == TypeKind.CLASS && b.typeKind == TypeKind.CLASS) {
                ClassType act = (ClassType) a;
                ClassType bct = (ClassType) b;

                if (act.className.spelling.contentEquals(bct.className.spelling)) return true;
            }

            if (a.typeKind == TypeKind.NULL || b.typeKind == TypeKind.NULL) {
                return true;
            }
            
        }

        return false;
    }

}

        