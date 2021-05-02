package miniJava.ContextualAnalysis;

import miniJava.AbstractSyntaxTrees.*;

public class DeclSignature {
    public String name;
    //public TypeDenoter type;
    public TypeDenoter[] argtypes;

    public DeclSignature(Declaration d) {

        this.name = d.name;
        //this.type = d.type;

        if (d instanceof MethodDecl) {
            MethodDecl md = (MethodDecl) d;
            argtypes = new TypeDenoter[md.parameterDeclList.size()];
            for (int i = 0; i < argtypes.length; i++) {
                argtypes[i] = md.parameterDeclList.get(i).type;
            }
        } else {

            // leave an empty list for any declaration other than a MethodDecl
            argtypes = null;
        }
    }

    public DeclSignature(String name) {
        this.name = name;
        this.argtypes = null;
    }

    public DeclSignature(String name, TypeDenoter[] argtypes) {
        this.name = name;
        this.argtypes = argtypes;
    }

    /**
     * Tests for equality by comparing the name String and optional arg types.
     */
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;

        if (! (o instanceof DeclSignature)) return false;

        DeclSignature ds = (DeclSignature) o;

        if (! this.name.contentEquals(ds.name)) return false;

        if (this.argtypes != null && ds.argtypes != null) {

            if (this.argtypes.length != ds.argtypes.length 
            /* || ! this.type.equals(ds.type)*/ 
                ) return false;


            for (int i = 0; i < this.argtypes.length; i++) {
                if (! this.argtypes[i].equals(ds.argtypes[i])) return false;
            }

            return true;
        } else if (this.argtypes == null && ds.argtypes == null) {
            return true;    // both aren't methods, so they match
        } else {
            return false;   // one is a method, the other isn't 
        }
    }

    /**
     * Hashes based on name String and optional arg types.
     */
    @Override
    public int hashCode() {

        int hash = 17;
        hash = hash * 31 + name.hashCode();
		
        if (argtypes != null) {
            hash = ~hash;
            for (int i = 0; i < argtypes.length; i++) {
                hash = hash * 31 + argtypes[i].hashCode();
            }
        }
        

        return hash;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(this.name);

        if (argtypes == null) return sb.toString();

        sb.append(" (");

        for (int i = 0; i < argtypes.length-1; i++) {
            sb.append(argtypes[i].toString() + ", ");
        }

        sb.append(argtypes[argtypes.length-1].toString() + ")");
        return sb.toString();
    }
}
