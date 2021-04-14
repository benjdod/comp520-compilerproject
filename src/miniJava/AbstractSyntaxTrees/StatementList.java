/**
 * miniJava Abstract Syntax Tree classes
 * @author prins
 * @version COMP 520 (v2.2)
 */
package miniJava.AbstractSyntaxTrees;

import java.util.*;

public class StatementList implements Iterable<Statement>
{
    public StatementList() {
        slist = new ArrayList<Statement>();
    }
    
    public void add(Statement s){
        slist.add(s);
    }
    
    public Statement get(int i){
        return slist.get(i);
    }
    
    public int size() {
        return slist.size();
    }

    public Statement getLast() {
        int last_idx = 0;

        if ((slist.size() - 1) > 0) {
            last_idx = slist.size()  - 1;
        }
        return slist.size() > 0 ? slist.get(last_idx) : null;
    }
    
    public Iterator<Statement> iterator() {
    	return slist.iterator();
    }
    
    private List<Statement> slist;
}
