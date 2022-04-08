package Trie;

import java.util.HashMap;

public class Trie<t> {

	public int size;			// Trie size
	private Node<t> root;		// Trie root
	
	public Trie() {
		// constructor to assign values for root and size of Trie
		this.root = new Node<t>();
		this.size = 0;
	}
	
	public void put(String key, t value)			// insert key value pair in Trie
	{
		HashMap<Character, Node<t>> child = this.root.children; 
		Node<t> node = null;
		for(int i= 0;i<= (key.length()-1); ++i)			 
		{
			char ch= key.charAt(i);
			
			if(child.containsKey(ch))
			{
				node=child.get(ch);
			}
			else {
				node= new Node<t> (ch);
				child.put(ch,node);
			}
			
			if(i== (key.length()-1))
			{
				node.value = value;
			}
			
			child = node.children;
		}	
		this.size += 1;
	}

	//searches word in the Trie and returns page index if the word is present
	public t wordSearch(String w)			
	{
		HashMap<Character, Node<t>> child = this.root.children;
		Node<t> node =null;
		t pageIndex = null;
		
		for(int i = 0; i<= (w.length()-1); ++i)
		{
			char ch= w.charAt(i);
			
			if(child.containsKey(ch))
			{
				node = child.get(ch);
			}
			else
				return null;
			
			if(i == (w.length()-1))
					{
				pageIndex= node.value;
					}
			child = node.children;
		}
		return pageIndex;
		}
		
	}
