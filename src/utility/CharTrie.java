package utility;

import java.util.ArrayList;

/*
 * A little hand-rolled trie... hopefully it has good enough performance
 * It doesn't have remove, so don't count on it.
 */

public class CharTrie<T> {
	
	private CharTrieNode<T> _head;
	private int _num_nodes;
	private int _num_terminals;
	private int _max_length;
	
	public CharTrie() {
		_head = new CharTrieNode<T>();
		_num_nodes = 0;
		_num_terminals = 0;
	}
	
	public int getTerminalCount() {
		return _num_terminals;
	}
	
	public int getNodeCount() {
		return _num_nodes;
	}

	public int getMaxLength() {
		return _max_length;
	}
	
	public void insert(String s, T terminal) {
		char[] c_array = s.toCharArray();
		
		if (c_array.length > _max_length) {
			_max_length = c_array.length;
		}
		
		int i;
		
		CharTrieNode<T> current = _head;
		CharTrieNode<T> next = null;
		
		for (i = 0; i < c_array.length; i++) {
			if ((next = current.getChild(c_array[i])) != null) {
				current = next;
			} else {
				if (i == c_array.length - 1) {
					current = current.addChild(c_array[i], terminal);
				} else {
					current = current.addChild(c_array[i], null);
				}
				_num_nodes++;
			}
		}
		
		_num_terminals++;
	}

	public T get(String s) {
		
		char[] c_array = s.toCharArray();
		
		int i;
		
		CharTrieNode<T> current = _head;
		CharTrieNode<T> next = null;
		
		for (i = 0; i < c_array.length; i++) {
			if ((next = current.getChild(c_array[i])) != null) {
				current = next;
			} else {
				return null;
			}
		}
		
		if (current.terminal != null) 
			return current.terminal;
		else return null;
	}
	
}

class CharTrieNode<T> {
	public char c;
	public T terminal;
	public int depth;
	public ArrayList<CharTrieNode<T>> next;
	
	public CharTrieNode (char c, T terminal, int depth) {
		this.c = c;
		this.terminal = terminal;
		this.depth = depth;
		this.next = new ArrayList<CharTrieNode<T>>();
	}
	
	public CharTrieNode() {
		this('\0', null, 0);
	}
	
	public CharTrieNode<T> addChild(CharTrieNode<T> node) {
		next.add(node);
		return node;
	}
	
	public CharTrieNode<T> addChild(char ch, T terminal) {
		CharTrieNode<T> new_node = new CharTrieNode<T>(ch, terminal, this.depth + 1);
		next.add(new_node);
		return new_node;
	}
	
	public CharTrieNode<T> getChild(char ch) {
		
		CharTrieNode<T> target = null;
		
		for (CharTrieNode<T> node : next) {
			if (node.c == ch) {
				target = node;
			}
		}
		
		return target;
	}
}