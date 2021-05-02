package miniJava;

import java.util.HashMap;

import miniJava.AbstractSyntaxTrees.ArrayType;
import miniJava.AbstractSyntaxTrees.BaseType;
import miniJava.AbstractSyntaxTrees.TypeDenoter;
import miniJava.AbstractSyntaxTrees.TypeKind;
import miniJava.SyntacticAnalyzer.SourcePosition;

public class Driver {

	public static void testDeclSig() {
		HashMap<DeclSignature, Integer> map  = new HashMap<DeclSignature, Integer>();

		DeclSignature put_ds = new DeclSignature();

		SourcePosition nopos = new SourcePosition(0, 0);

		put_ds.name = "hello";
		put_ds.type = new BaseType(TypeKind.VOID, nopos);
		put_ds.argtypes = new TypeDenoter[] {new BaseType(TypeKind.INT, nopos)};

		map.put(put_ds, 69);

		DeclSignature get_ds = new DeclSignature();
		get_ds.name = "hello";
		get_ds.type = new BaseType(TypeKind.VOID, nopos);
		get_ds.argtypes = new TypeDenoter[] {new BaseType(TypeKind.INT, nopos)};

		System.out.println(map.get(get_ds));
	}

	public static void sing(int x) {
		;
	}

	public static int sing() {
		return 3;
	}

	public static void main(String[] args) {

		
	}
}

class DeclSignature {
	public String name;
	public TypeDenoter type;
	public TypeDenoter[] argtypes;

	@Override
	public boolean equals(Object o) {

		if (o == this) return true;

		if (! (o instanceof DeclSignature)) return false;

		DeclSignature ds = (DeclSignature) o;

		if (this.argtypes.length != ds.argtypes.length || ! this.type.equals(ds.type)) return false;


		for (int i = 0; i < this.argtypes.length; i++) {
			if (! this.argtypes[i].equals(ds.argtypes[i])) return false;
		}

		if (! this.name.contentEquals(ds.name)) return false;

		return true;
	}

	@Override
	public int hashCode() {

		int hash = 17;
		hash = hash * 31 + name.hashCode();
		hash = hash * 31 + type.hashCode();
		
		for (int i = 0; i < argtypes.length; i++) {
			hash = hash * 31 + argtypes[i].hashCode();
		}

		return hash;
	}
}