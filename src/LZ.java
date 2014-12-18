

public class LZ {

	private LZ(){
		
	}
	
	

	public static String encode(String uncompressed, ADT adt) {
		// Convert uncompressed into a bit string
		String bitString = ToBinary(uncompressed);

		StringBuilder retString = new StringBuilder();

		/**
		 * Runs through every bit of the string,
		 */
		for (int i = 0; i < bitString.length();) {
			boolean itemAdded = false;
			boolean endOfString = false;
			int subsLength = 1;
			while (!itemAdded && !endOfString) {
				int subEnd = i + subsLength; // How long the next substring
												// *will be
				if (subEnd >= bitString.length()) { // Makes sure we aren't
													// stepping out of bounds
					endOfString = true; // Trigger the endOfString block
					subEnd = bitString.length(); // Max length
				}
				String subs = bitString.substring(i, subEnd); // Substrings now
																// with the
																// 'safe' subEnd
																// item
				String key = adt.retrieveKey(subs);
				if (endOfString) {
					/**
					 * Final condition, either appends a key, or the bit,
					 * depending on what is available
					 */
					if (key != null) {
						retString.append(key);
						return prepareFinalEncoding(retString.toString(), adt);// This
																			// string
																			// exists,
																			// add
																			// the
																			// key
					}
					subs = bitString.substring(i, subEnd);
					retString.append(build(adt, subs));
					return prepareFinalEncoding(retString.toString(), adt); 
						// Key doesn't exist,
														// add the prefix and
														// this bit
				} else if (key == null) {

					/**
					 * Since this one wasn't found, add this one, and find the
					 * prefix key. Then append the prefix key to the remaining
					 * bit
					 **/
					retString.append(build(adt, subs));
					adt.add(subs);
					i += subsLength;
					itemAdded = true;
				} else {
					/**
					 * Found a key in the tree, we keep going until we don't
					 * find a key
					 */
					subsLength++;
				}
			}
		}
		return prepareFinalEncoding(retString.toString(), adt);
	}

	public static String decode(String compressed, ADT adt) {

		int indexSize = Integer.parseInt(compressed.substring(0, 32), 2);
		adt = new BinarySearchTree(compressed.substring(0, 32), indexSize);

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < compressed.length(); i++) {
			if (i % 2 == 0) { // Key
				sb.append(adt.getBitStringFromKey(compressed.substring(i,
						i + 1)));
			} else { // Digit
				sb.append(compressed.substring(i, i + 1));
			}
		}

		return sb.toString();

	}
	
	public static String hash_encode(String uncompressed)
	{
		ADT hashVersion = new BinaryHash();
		return encode(uncompressed, hashVersion);
	}
	
	public static String tree_encode(String uncompressed){
		ADT treeVersion = new BinarySearchTree();
		return encode(uncompressed, treeVersion);
	}

	/**
	 * Takes in the trie and substring where the key was NOT found It then finds
	 * the prefix string key for subs' prefix. It then appends the final bit
	 * onto the key
	 * 
	 * @param adt
	 *            ADT: tree/(upcoming hashtable)
	 * @param subs
	 *            Substring where prefix exists, but subs doesn't in ADT
	 * @return Prefix Key + last bit
	 */
	private static String build(ADT adt, String subs) {
		String prefixKey = ToBinary(adt.prefixKey(subs));
		String finalBit = subs.substring(subs.length() - 1, subs.length());
		String combined = prefixKey + finalBit;
		return combined;
	}
	
	
	private static String prepareFinalEncoding(String keyEncoded, ADT adt){
		String dictionary = adt.getDictionary();
		//Get the binary representation of the dictionary index size
		String indexSize = padLeft(Integer.toBinaryString(adt.getDictionaryIndexSize()), 32, '0');
		String fullyencoded = indexSize + dictionary + keyEncoded;
		//Check to see if we need to pad the end with '0'
		if(fullyencoded.length() % 16 != 0){
			fullyencoded = pad16bit(fullyencoded);
		}
		return fullyencoded;
	}
	
	private static String pad16bit(String encoded){
		while(encoded.length() % 16 != 0){
			encoded += '0';
		}
		return encoded;
	}
	
	public static String ToBinary(String str) {
		final char[] masks = { 0x8000, 0x4000, 0x2000, 0x1000, 0x800, 0x400,
				0x200, 0x100, 0x80, 0x40, 0x20, 0x10, 0x8, 0x4, 0x2, 0x1 };
		String ret = "";

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			for (int j = 0; j < 16; j++)
				if ((c & masks[j]) == 0)
					ret += "0";
				else
					ret += "1";
		}

		return ret;
	}

	public static String FromBinary(String str) {
		final char[] bits = { 0x8000, 0x4000, 0x2000, 0x1000, 0x800, 0x400,
				0x200, 0x100, 0x80, 0x40, 0x20, 0x10, 0x8, 0x4, 0x2, 0x1 };
		String ret = "";

		for (int i = 0; i < str.length(); i += 16) {
			char c = 0x0000;

			for (int j = 0; j < 16; j++)
				if (str.charAt(i + j) == '1')
					c |= bits[j];

			ret += c;
		}

		return ret;
	}

	/**
	 * Duplicated code, yes, this is also in BinarySearchTree.  I didn't want to create a circular dependency
	 * @param in
	 * @param totalLength
	 * @param paddingChar
	 * @return
	 */
	public static String padLeft(String in, int totalLength, char paddingChar){
		char[] arr = new char[totalLength];
		if(in.length() > totalLength){
			return in;
		}
		for(int i = 0; i < totalLength - in.length(); i++){
			arr[i] = paddingChar;
		}
		int j = 0;
		for(int i = totalLength - in.length(); i < totalLength; i++){
			arr[i] = in.charAt(j++);
		}
		return String.valueOf(arr);
	}
}
