package hw4;


public class LZEncryption {
	
	private static ADT tree;
	
	public static String encode(String uncompressed){
		StringBuilder retString = new StringBuilder();
		tree = new BinarySearchTree();
		
		/**
		 * Runs through every bit of the string, 
		 */
		for(int i = 0; i < uncompressed.length();){
			boolean itemAdded = false;
			boolean endOfString = false;
			int subsLength = 1;
			while(!itemAdded && !endOfString){
				int subEnd = i + subsLength; //How long the next substring *will be
				if(subEnd  >= uncompressed.length()){ //Makes sure we aren't stepping out of bounds
					endOfString = true; //Trigger the endOfString block
					subEnd = uncompressed.length(); //Max length
				}
				String subs = uncompressed.substring(i, subEnd); //Substrings now with the 'safe' subEnd item
				String key = tree.retrieveKey(subs);
				if(endOfString){
					/**
					 * Final condition, either appends a key, or the bit, depending on 
					 * what is available
					 */
					if(key != null){
						retString.append(key);
						return retString.toString(); //This string exists, add the key
					}
					subs = uncompressed.substring(i, subEnd);
					retString.append(build(tree, subs));
					return retString.toString(); //Key doesn't exist, add the prefix and this bit
				}
				else if(key == null){
					
					/**
					Since this one wasn't found, add this one,
					and find the prefix key.  Then append the
					prefix key to the remaining bit
					**/
					retString.append(build(tree, subs));
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
		return retString.toString();
	}
	
	public static String decode(String compressed){

		
		if(tree == null) return ""; //TODO: encode tree / dictionary into string
		
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

public static String ToBinary(String str)
{
	final char[] masks = {0x8000,0x4000,0x2000,0x1000,0x800,0x400,0x200,0x100,0x80,0x40,0x20,0x10,0x8,0x4,0x2,0x1};
	String ret = "";
	
	for(int i = 0;i < str.length();i++)
	{
		char c = str.charAt(i);
		
		for(int j = 0;j < 16;j++)
			if((c & masks[j]) == 0)
				ret += "0";
			else
				ret += "1";
	}
	
	return ret;
}	

public static String FromBinary(String str)
{
	final char[] bits = {0x8000,0x4000,0x2000,0x1000,0x800,0x400,0x200,0x100,0x80,0x40,0x20,0x10,0x8,0x4,0x2,0x1};
	String ret = "";
	
	for(int i = 0;i < str.length();i += 16)
	{
		char c = 0x0000;
		
		for(int j = 0;j < 16;j++)
			if(str.charAt(i + j) == '1')
				c |= bits[j];
		
		ret += c;
	}
	
	return ret;
}
}
