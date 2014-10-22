



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String input = "aabaaabaaaaaabababbbbaba";
		String input = "The world is full of sugar!";
		String encrypted = LZEncryption.encode(input);
		String correctEncrypted = "00000000000000000000000000000101000000000000001010100000000000000001101000000000000000001100101000000000000000100000000000000000001110111000000000000001101111000000000000001110010000000000000001101100000000000000001100100001000000000001101001000000000000001110011001000000000001100110000000000000001110101010000000000001101100001000000000001101111000000000000001100110001000000000001110011011010000000001100111000000000000001100001001110000000000100001000000000000";
		System.out.println(encrypted);
		System.out.println(correctEncrypted);
		System.out.println(encrypted.equals(correctEncrypted));
		
		String decrypted = LZEncryption.decode(encrypted);
		System.out.println(input);
		System.out.println(decrypted);
		System.out.println(input.equals(decrypted));
		
		
		/**
		String unencrypted = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		encrypted = LZEncryption.encode(unencrypted);
		System.out.println(unencrypted);
		System.out.println(encrypted);
		
		**/
	}

}
