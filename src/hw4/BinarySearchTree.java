package hw4;

import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;


/**
 * Creates a Binary Search Tree
 * @author Robert Clabough
 *
 */
public class BinarySearchTree {
	private Node root;
	private Map<String, Node> dictionary;
	private Short currentIndex;
	
	public BinarySearchTree(){
		root = new Node(null);
		dictionary = new HashMap<String, Node>();
		currentIndex = 0;
		root.item = "";
		//Add root to the dictionary
		root.key = String.valueOf(currentIndex++);
		dictionary.put(root.key, root);
	}
	
	public String add(String item){
		Node added = add(item, root, null);
		String key = String.valueOf(currentIndex++);
		added.key = key;
		dictionary.put(key, added);
		return key;
	}
	
	private Node add(String item, Node current, Node parent){
		if(item == null || item.length() == 0){
			return current;
		}

		char nextChar = item.charAt(0);
		
		if(left(nextChar)){
			if(current.left == null){
				current.left = new Node(current, String.valueOf(nextChar));
			}
			return add(item.substring(1), current.left, current);
		} else if (right(nextChar)){
			if(current.right == null){
				current.right = new Node(current, String.valueOf(nextChar));
			}
			return add(item.substring(1), current.right, current);
		}
		return null;
	}

	public boolean contains(String findItem){
		return findNode(findItem) != null;
	}
	
	
	public String retrieveKey(String bitString){
		Node found = findNode(bitString);
		if(found == null){
			return null;
		}
		return found.key;
	}
	
	public String prefixKey(String findItem){
		if(findItem == null || findItem.length() == 0){
			return "";
		} else if (findItem.length() == 1){
			return root.key;
		}
		String prefixString = findItem.substring(0, findItem.length() - 1);
		return retrieveKey(prefixString);
	}
	
	private Node findNode(String findItem){
		return findNode(findItem, root);
	}
	
	private Node findNode(String findItem, Node current){
		if(current == null){
			return null; //Haven't found it
		} 
		if(findItem.length() == 0){
			return current;
		}
		char searchChar = findItem.charAt(0);
		findItem = findItem.substring(1);
		if(left(searchChar)){
			return findNode(findItem, current.left);
		} else if (right(searchChar)){
			return findNode(findItem, current.right);
		}
		return null;
	}
	
	
	/** Allows languages {0,1}, {a,b}, {A, B} **/
	/** If there is another language, provide it here **/
	private boolean left(char c){
		return (c == '0' || c == 'a' || c == 'A');
	}
	private boolean right(char c){
		return (c == '1' || c == 'b' || c == 'B');
	}
	
	private class Node{
		private String key;
		private String item;
		private Node left;
		private Node right;
		private Node parent;
		public Node(Node parent){
			this.parent = parent;
		}
		public Node(Node parent, String item){
			this.parent = parent;
			this.item = item;
		}
	}
}
