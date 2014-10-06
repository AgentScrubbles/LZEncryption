package hw4;


public class LZEncryption {
	
	private static ADT tree;
	
	public static String encode(String uncompressed){
		String retString = "";
		tree = new BinarySearchTree();
		for(int i = 0; i < uncompressed.length();){
			boolean itemAdded = false;
			boolean endOfString = false;
			int subsLength = 1;
			while(!itemAdded && !endOfString){
				int subEnd = i + subsLength;
				if(subEnd  >= uncompressed.length()){
					endOfString = true;
					subEnd = uncompressed.length();
				}
				String subs = uncompressed.substring(i, subEnd);
				String key = tree.retrieveKey(subs);
				if(endOfString){
					/**
					 * Final condition, either appends a key, or the bit, depending on 
					 * what is available
					 */
					if(key != null){
						return retString + key; //This string exists, add the key
					}
					subs = uncompressed.substring(i, subEnd);
					return retString + build(tree, subs); //Key doesn't exist, add the prefix and this bit
				}
				else if(key == null){
					
					/**
					Since this one wasn't found, add this one,
					and find the prefix key.  Then append the
					prefix key to the remaining bit
					**/
					retString += build(tree, subs);
					tree.add(subs);					
					i += subsLength;
					itemAdded = true;
				} else {
					/**
					 * Found a key in the tree, we keep going until we don't find a key
					 */
					subsLength++;
				}
			}
		}
		return retString;
	}
	
	public static String decode(String compressed){

		
		if(tree == null) return "";
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < compressed.length(); i++){
			if(i % 2 == 0){ //Key
				sb.append(tree.getBitStringFromKey(compressed.substring(i, i+1)));
			} else { //Digit
				sb.append(compressed.substring(i,i+1));
			}
		}
		
		return sb.toString();
		
	}
	
	
	/**
	 * Takes in the trie and substring where the key was NOT found
	 * It then finds the prefix string key for subs' prefix.  It then
	 * appends the final bit onto the key
	 * @param adt
	 * 		ADT: tree/(upcoming hashtable)
	 * @param subs
	 * 		Substring where prefix exists, but subs doesn't in ADT
	 * @return
	 * 		Prefix Key + last bit
	 */
	private static String build(ADT adt, String subs){
		String prefixKey = tree.prefixKey(subs);
		String finalBit = subs.substring(subs.length() - 1, subs.length());
		String combined = prefixKey + finalBit;
		return combined;
	}
}
