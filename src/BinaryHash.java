import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



public class BinaryHash implements ADT {
	
	private Map<String, String> dictionary = new HashMap<String, String>();
	private int currentIndex;
	private int MAX_DICTIONARY_SIZE = 10;
	
	public BinaryHash(){
		currentIndex = 0;
		add(""); //lambda
	}

	@Override
	public String add(String item) {
		if(dictionary.size() > MAX_DICTIONARY_SIZE){
			return "";
		}
		
		String key = String.valueOf(currentIndex++);
		dictionary.put(key, item);
		return key;
	}

	@Override
	public boolean contains(String findItem) {
		return dictionary.containsKey(findItem);
	}

	@Override
	public String retrieveKey(String bitString) {
		if(!dictionary.containsValue(bitString)){
			return null;
		}
		Iterator<String> iter = dictionary.keySet().iterator();
		while(iter.hasNext()){
			String next = iter.next();
			if(dictionary.get(next).equals(bitString)){
				return next;
			}
		}
		return null;
	}

	@Override
	public String prefixKey(String findItem) {
		String ret = dictionary.get(findItem.substring(0, findItem.length() - 1)); //Null if not found
		if(ret == null){
			return "";
		}
		return ret;
	}

	@Override
	public String keyLookup(String key) {
		return dictionary.get(key);
	}

	@Override
	public String getBitStringFromKey(String key) {
		return dictionary.get(key);
	}

	@Override
	public String getDictionary() {
		StringBuilder sb = new StringBuilder();
		
		//Keys are 1 to 10
		for(int i = 0; i < MAX_DICTIONARY_SIZE; i++){			
			sb.append(getBitStringFromKey(String.valueOf(i)));
		}
		return sb.toString();
	}

	@Override
	public void buildDictionary(String bitString) {
		// TODO Auto-generated method stub
		// Not needed for encode
		
	}

	@Override
	public int getDictionaryIndexSize() {
		return currentIndex;
	}

}
