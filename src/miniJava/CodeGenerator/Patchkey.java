package miniJava.CodeGenerator;

import javax.sound.midi.Patch;

import miniJava.AbstractSyntaxTrees.TypeDenoter;

public class Patchkey {

    public static final Patchkey NONE = new Patchkey("", new TypeDenoter[] {});

    public String name;
    public TypeDenoter[] args;

    public Patchkey(String name, TypeDenoter[] args) {
        this.name = name;
        this.args = args;
    }

    public Patchkey(String name, TypeDenoter type) {
        this.name = name;
        this.args = new TypeDenoter[] {type};
    }

    public Patchkey(String name) {
        this.name = name;
        this.args = new TypeDenoter[] {};
    }

    @Override
    public int hashCode() {
        int hash = 17;

        hash = hash * 31 + name.hashCode();

        for (int i = 0; i < args.length; i++) {
            hash = hash*31 + args[i].hashCode();
        }

        return hash;
    }

    public boolean equals(Object o) {

        Patchkey p;

        if (o instanceof Patchkey) {
            p = (Patchkey) o;
        } else return false;

        return (this.name.contentEquals(p.name) && argsEqual(this.args, p.args));

    }

    private static boolean argsEqual(TypeDenoter[] a, TypeDenoter[] b) {
        if (a == null || b == null) return false;

        if (a.length != b.length) return false;

        for (int i = 0; i < b.length; i++) {
            if (! a[i].equals(b[i])) return false;
        }

        return true;
    }
    
}
