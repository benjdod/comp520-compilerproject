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
    public static final int CLASS_LEVEL = 0;
    public static final int MEMBER_LEVEL = 1;
    public static final int PARAM_LEVEL = 2;
    public static final int LOCAL_LEVEL = 3;

    private ArrayDeque<HashMap<String, Declaration>> _stack;
    private int _level;

    public IdTable(ErrorReporter reporter) {
        _stack = new ArrayDeque<HashMap<String, Declaration>>();


        // FIXME: omitting initialization of the stack could yield 
        // some nasty problems in the form of null pointer exceptions!

        //_stack.push(new HashMap<String, Declaration>());


        // start at level -1 so the Identification traversal can climb 
        // into 0 with visitPackage
        _level = -1;
    }

    public int getLevel() {
        return _level;
    }

    @Override
    public String toString() {
        Iterator<HashMap<String, Declaration>> i = _stack.descendingIterator();

        int lvl = 0;

        HashMap<String, Declaration> map;

        StringBuffer sb = new StringBuffer();

        sb.append("--- table ---\n");

        while (i.hasNext()) {
            map = i.next();

            sb.append("level " + lvl++ + " (" + map.size() + ")\n");
            Iterator<String> keys = map.keySet().iterator();

            while (keys.hasNext()) {
                sb.append("- " + keys.next() + "\n");
            }
        }

        sb.append("------\n");

        return sb.toString();
    }

    public void insert(Declaration d) throws IdError {

        validate(d);

        currentMap().put(d.name, d);
    }

    public Declaration get(String s) throws IdError {

        // TODO: make sure this doesn't go backwards...
        Iterator<HashMap<String, Declaration>> i = _stack.iterator();

        HashMap<String, Declaration> map;

        while (i.hasNext()) {
            map = i.next();

            if (map.containsKey(s)) return map.get(s);
        }
        
        throw new IdError("Identifier " + s + " is not declared", new SourcePosition(1, 1));
    }

    public Declaration get(Identifier id) throws IdError {

        try {
            return this.get(id.spelling);
        } catch (IdError e) {
            throw new IdError("Identifier '" + id.spelling + "' is not declared", id.posn);
        }
    }

    private void validate(Declaration decl) throws IdError {

        String s = decl.name;

        // must be descending iterator!
        Iterator<HashMap<String, Declaration>> i = _stack.descendingIterator();

        HashMap<String, Declaration> map;
        int lvl = 0;

        while (i.hasNext()) {
            map = i.next();

            /*
             * "However, declarations at level 4 or higher may not hide declarations at levels 3 or higher."
             * */
            if (lvl == IdTable.PARAM_LEVEL && map.containsKey(s)) {
                throw new IdError("Invalid declaration", "Variables declared in parameter declarations cannot be hidden", decl.posn);
            } else if (lvl >= IdTable.LOCAL_LEVEL && map.containsKey(s)) {
                throw new IdError("Duplicate declaration in local scope", "nested inner local scopes cannot override outer declarations (previous declaration is at " + map.get(s).posn.toString() + ")", decl.posn);
            }

            lvl++;
        }
    }

    private HashMap<String, Declaration> currentMap() {
        return _stack.peek();
    }

    public void openScope() {
        _stack.push(new HashMap<String, Declaration>());
        _level += 1;
    }

    public void closeScope() {

        if (_level == 0) return;

        _stack.pop();
        _level -= 1;
    }
}
