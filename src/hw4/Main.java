package hw4;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String encrypted = LZEncryption.encode("aabaaabaaaaaabababbbbaba");
		String correctEncrypted = "0a1b1a2a3a3b4b0b8b4";
		System.out.println(encrypted);
		System.out.println(correctEncrypted);
		System.out.println(encrypted.equals(correctEncrypted));
		
		
		
		
		/**
		String unencrypted = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		encrypted = LZEncryption.encode(unencrypted);
		System.out.println(unencrypted);
		System.out.println(encrypted);
		
		**/
	}

}
