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
					if(key != null){
						return retString + key; //This string exists, add the key
					}
					subs = uncompressed.substring(i, subEnd);
					String prefixKey = tree.prefixKey(subs);
					String finalBit = subs.substring(subs.length() - 1, subs.length());
					String combined = prefixKey + finalBit;
					return retString + combined; //Key doesn't exist, add the prefix and this bit
				}
				else if(key == null){
					
					/**
					Since this one wasn't found, add this one,
					and find the prefix key.  Then append the
					prefix key to the remaining bit
					**/
					String prefixKey = tree.prefixKey(subs);
					String finalBit = subs.substring(subs.length() - 1, subs.length());
					String combined = prefixKey + finalBit;
					retString += combined;
					tree.add(subs);					
					i += subsLength;
					itemAdded = true;
				} else {
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
				sb.append(tree.keyLookup(compressed.substring(i, i+1)));
			} else { //Digit
				sb.append(compressed.substring(i,i+1));
			}
		}
		
		return sb.toString();
		
	}
}
