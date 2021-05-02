package miniJava.ContextualAnalysis;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;

import miniJava.ErrorReporter;
import miniJava.AbstractSyntaxTrees.Declaration;
import miniJava.AbstractSyntaxTrees.Identifier;
import miniJava.SyntacticAnalyzer.SourcePosition;

public class IdTable {

    /* this is not the ideal way of doing it, but we forge on */
    public static final int CLASS_LEVEL = 1;
    public static final int MEMBER_LEVEL = 2;
    public static final int PARAM_LEVEL = 3;
    public static final int LOCAL_LEVEL = 4;

    private ArrayDeque<HashMap<DeclSignature, Declaration>> _stack;
    private int _level;

    public IdTable(ErrorReporter reporter) {
        _stack = new ArrayDeque<HashMap<DeclSignature, Declaration>>();


        // FIXME: omitting initialization of the stack could yield 
        // some nasty problems in the form of null pointer exceptions!

        //_stack.push(new HashMap<DeclSignature, Declaration>());


        // start at level 0 so the Identification traversal can climb 
        // into 1 with visitPackage
        _level = 0;
    }

    public int getLevel() {
        return _level;
    }

    @Override
    public String toString() {
        Iterator<HashMap<DeclSignature, Declaration>> i = _stack.descendingIterator();

        int lvl = 0;

        HashMap<DeclSignature, Declaration> map;

        StringBuffer sb = new StringBuffer();

        sb.append("--- table ---\n");

        while (i.hasNext()) {
            map = i.next();

            sb.append("level " + lvl++ + " (" + map.size() + ")\n");
            Iterator<DeclSignature> keys = map.keySet().iterator();

            while (keys.hasNext()) {
                sb.append("- " + keys.next() + "\n");
            }
        }

        sb.append("------\n");

        return sb.toString();
    }

    public void insert(Declaration d) throws IdError {

        validate(d);

        currentMap().put(new DeclSignature(d.name), d);
    }

    public Declaration get(String s) throws IdError {

        Iterator<HashMap<DeclSignature, Declaration>> i = _stack.iterator();

        HashMap<DeclSignature, Declaration> map;

        while (i.hasNext()) {
            map = i.next();

            DeclSignature search = new DeclSignature(s);
            if (map.containsKey(search)) return map.get(search);
        }
        
        throw new IdError("Identifier '" + s + "'' is not declared", new SourcePosition(1, 1));
    }

    public Declaration get(Identifier id) throws IdError {

        try {
            return this.get(id.spelling);
        } catch (IdError e) {
            throw new IdError("Identifier '" + id.spelling + "' is not declared", id.posn);
        }
    }

    public Declaration getAtLevel(String s, int level) throws IdError {
        int lvl = 1;

        Iterator<HashMap<DeclSignature, Declaration>> i = _stack.iterator();

        HashMap<DeclSignature, Declaration> map;

        DeclSignature search = new DeclSignature(s);

        while (i.hasNext()) {
            map = i.next();

            if (lvl == level) {
                if (map.containsKey(search)) {
                    return map.get(search);
                }
            } 
        }

        throw new IdError("No declaration at level " + level + "!", new SourcePosition(1, 1));
    }

    public boolean contains(String s) {
        try {
            get(s);
            return true;
        } catch (IdError e) {
            return false;
        }
    }

    public boolean containsAtLevel(String s, int level) {
        try {
            getAtLevel(s, level);
            return true;
        } catch (IdError e) {
            return false; 
        }
    }
    
    private void validate(Declaration decl) throws IdError {

        DeclSignature search = new DeclSignature(decl);

        // must be descending iterator!
        Iterator<HashMap<DeclSignature, Declaration>> i = _stack.descendingIterator();

        HashMap<DeclSignature, Declaration> map;
        int lvl = 1;

        while (i.hasNext()) {
            map = i.next();

            /*
             * "However, declarations at level 4 or higher may not hide declarations at levels 3 or higher."
             * */
            if (lvl == IdTable.PARAM_LEVEL && map.containsKey(search)) {
                throw new IdError("Invalid declaration", "Variables declared in parameter declarations cannot be hidden", decl.posn);
            } else if (lvl >= IdTable.LOCAL_LEVEL && map.containsKey(search)) {
                throw new IdError("Duplicate declaration in local scope", "nested inner local scopes cannot override outer declarations (previous declaration  at " + map.get(search).posn.toString() + ")", decl.posn);
            } else if (lvl == _level && map.containsKey(search)) {
                throw new IdError("Duplicate declaration in scope", "only one unique declaration is allowed per scope (previous declaration was at " + map.get(search).posn.toString() + ")", decl.posn);
            }

            lvl++;
        }
    }

    private HashMap<DeclSignature, Declaration> currentMap() {
        return _stack.peek();
    }

    public void openScope() {
        _stack.push(new HashMap<DeclSignature, Declaration>());
        _level += 1;
    }

    public void closeScope() {

        if (_level == 0) return;

        _stack.pop();
        _level -= 1;
    }
}
