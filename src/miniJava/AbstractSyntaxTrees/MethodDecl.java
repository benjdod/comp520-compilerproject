/**
 * miniJava Abstract Syntax Tree classes
 * @author prins
 * @version COMP 520 (v2.2)
 */
package miniJava.AbstractSyntaxTrees;

import miniJava.SyntacticAnalyzer.SourcePosition;
import miniJava.CodeGenerator.Address;
import miniJava.CodeGenerator.Patchkey;
import miniJava.ContextualAnalysis.DeclSignature;

public class MethodDecl extends MemberDecl {
	
	public MethodDecl(MemberDecl md, ParameterDeclList pl, StatementList sl, SourcePosition posn){
    super(md,posn);
    parameterDeclList = pl;
    statementList = sl;
    patchkey = Patchkey.NONE;
	}
	
	public <A, R> R visit(Visitor<A, R> v, A o) {
        return v.visitMethodDecl(this, o);
    }
	
	public ParameterDeclList parameterDeclList;
	public StatementList statementList;
	public Address address;
	public Patchkey patchkey;

	/**
	 * Determines whether a method matches a decl signature
	 * @param ds
	 * @return
	 */
	public boolean matchSignature(DeclSignature ds) {

		// names don't match
		if (! this.name.contentEquals(ds.name)) return false;

		// the decl signature is not for a method
		if (ds.argtypes == null ) return false;

		// number of args doesn't match
		if (this.parameterDeclList.size() != ds.argtypes.length) return false;

		for (int i = 0; i < this.parameterDeclList.size(); i++) {
			// type of positional arg i doesn't match
			if (! this.parameterDeclList.get(i).type.equals(ds.argtypes[i])) return false;
		}

		return true;
	}
}
