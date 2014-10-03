package hw4;

import java.util.HashMap;
import java.util.Map;


/**
 * Creates a Binary Search Tree
 * @author Robert Clabough
 *
 */
public class BinarySearchTree implements ADT{
	private Node root;
	private Map<String, Node> dictionary;
	private Short currentIndex;
	
	/**
	 * Initializes dictionary and tree internals.
	 * Automatically adds lambda as the root
	 */
	public BinarySearchTree(){
		root = new Node(null);
		dictionary = new HashMap<String, Node>();
		currentIndex = 0;
		root.item = "";
		//Add root to the dictionary
		root.key = String.valueOf(currentIndex++);
		dictionary.put(root.key, root);
	}
	
	/**
	 * Adds item to the tree and dictionary
	 * @param item
	 * @return
	 * 	Key of the item for quick retrieval
	 */
	public String add(String item){
		Node added = add(item, root, null);
		String key = String.valueOf(currentIndex++);
		added.key = key;
		dictionary.put(key, added);
		return key;
	}
	
	
	/**
	 * Recursive add operation.  Adds 'item' in the correct
	 * spot according to BST rules
	 * @param item
	 * 		Item to be added
	 * @param current
	 * 		Current node to check against (start with root)
	 * @param parent
	 * 		Parent of the current node (If new node is added,
	 * 		current.parent will access parent.
	 * @return
	 * 		Node that has been added
	 */
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

	/**
	 * Contains operation
	 * @param findItem
	 * 		Item to search for
	 * @return
	 * 		True if found, false otherwise
	 */
	public boolean contains(String findItem){
		return findNode(findItem) != null;
	}
	
	/**
	 * Retrieves they key of the bitstring, reverse lookup of
	 * value -> key
	 */
	public String retrieveKey(String bitString){
		Node found = findNode(bitString);
		if(found == null){
			return null;
		}
		return found.key;
	}
	
	/**
	 * Prefix function, returns the key of the parent of finditem.
	 * Internally:
	 * 		Node(findItem).parent.key
	 */
	public String prefixKey(String findItem){
		if(findItem == null || findItem.length() == 0){
			return "";
		} else if (findItem.length() == 1){
			return root.key;
		}
		String prefixString = findItem.substring(0, findItem.length() - 1);
		return retrieveKey(prefixString);
	}
	
	/**
	 * Searches for the node itself with string findItem
	 * @param findItem
	 * 		bitstring to be looking for
	 * @return
	 * 		Node containing bitstring if found, otherwise null
	 */
	private Node findNode(String findItem){
		return findNode(findItem, root);
	}
	
	/**
	 * Basic recursive operation for contains, retrieve key,
	 * prefix, and others not involving modifying the ADT.
	 * @param findItem
	 * 		Bitstring to search for
	 * @param current
	 * 		Current node we are searching at.  
	 * @return
	 * 		Node containing found item, otherwise null
	 */
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
	
	@Override
	public String keyLookup(String key) {
		return dictionary.get(key).item;
	}
	
	/** Allows languages {0,1}, {a,b}, {A, B} **/
	/** If there is another language, provide it here **/
	private boolean left(char c){
		return (c == '0' || c == 'a' || c == 'A');
	}
	private boolean right(char c){
		return (c == '1' || c == 'b' || c == 'B');
	}
	
	/**
	 * Internal node class to keep structure of binary search tree
	 * @author Robert Clabough
	 *
	 *	While working on internals of decode, I will be supressing all unused
	 *	warnings.  After decode is completed, these unused variables should be
	 *	used.
	 */
	@SuppressWarnings("unused") 
	private class Node{
		private String key;
		private String item;
		private Node left;
		private Node right;
		private Node parent;
		Node(Node parent){
			this.parent = parent;
		}
		Node(Node parent, String item){
			this.parent = parent;
			this.item = item;
		}
	}

	
}
