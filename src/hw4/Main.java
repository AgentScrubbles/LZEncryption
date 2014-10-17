package hw4;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String input = "aabaaabaaaaaabababbbbaba";
		String input = "Hello, World!";
		String encrypted = LZEncryption.encode(input);
		String correctEncrypted = "0a1b1a2a3a3b4b0b8b4";
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
