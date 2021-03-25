package miniJava;

import miniJava.AbstractSyntaxTrees.ClassDecl;
import miniJava.AbstractSyntaxTrees.FieldDeclList;
import miniJava.AbstractSyntaxTrees.MethodDeclList;
import miniJava.SyntacticAnalyzer.IdTable;
import miniJava.SyntacticAnalyzer.SourcePosition;

public class Harness {

    static ClassDecl genClassDecl() {
        return new ClassDecl("cn", new FieldDeclList(), new MethodDeclList(), new SourcePosition(1,1));
    }

    public static void main(String[] args) {
        IdTable idt = new IdTable(new ErrorReporter());

        idt.insert("hello", genClassDecl());
        idt.ascend();
        idt.ascend();
        idt.insert("butt", genClassDecl());
        idt.insert("booty", genClassDecl());
        idt.ascend();
        idt.ascend();
        System.out.println(idt.insert("butt", genClassDecl()));
    }
}
