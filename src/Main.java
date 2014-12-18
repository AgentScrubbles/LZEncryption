import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		long systemStartTime = System.currentTimeMillis();
		

		
		
//
//		ArrayList<Long> treeRunTimes = new ArrayList<Long>();
//		ArrayList<Long> hashRunTimes = new ArrayList<Long>();
//
//		//Scanner keyboard = new Scanner(System.in);
//		
//		BufferedReader inputStream;
//		try {
//			inputStream = new BufferedReader(new FileReader("src/wordsEn.txt"));
//		} catch (FileNotFoundException e1) {
//			System.out.println("Could not open the file");
//			return;
//		}
//
//		for (int i = 1; i < 2000; i++) {
//			String str = new RandomString(i).nextString();
//			treeRunTimes.add(treeTimedTest(str));
//			hashRunTimes.add(hashTimedTest(str));
//			
//		}
//
//		try {
//			inputStream.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		try {
//			BufferedWriter outputStream = new BufferedWriter(new FileWriter(
//					"output.csv"));
//			outputStream.write("Robert Clabough: CS311 LZ Homework part 2");
//			outputStream.newLine();
//			outputStream.write("Total time: "
//					+ (System.currentTimeMillis() - systemStartTime));
//			outputStream.newLine();
//
//			for (int i = 0; i < Math.min(treeRunTimes.size(),
//					hashRunTimes.size()); i++) {
//				outputStream.write(treeRunTimes.get(i) + ","
//						+ hashRunTimes.get(i));
//				outputStream.newLine();
//			}
//			outputStream.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public static Long treeTimedTest(String str) {
		long start = System.currentTimeMillis();
		LZ.tree_encode(str);
		return System.currentTimeMillis() - start;
	}

	public static Long hashTimedTest(String str) {
		long start = System.currentTimeMillis();
		LZ.hash_encode(str);
		return System.currentTimeMillis() - start;
	}

	/**
	 * 
	 * @author by the community wiki at stackoverflow
	 *         http://stackoverflow.com/questions
	 *         /41107/how-to-generate-a-random-alpha-numeric-string
	 *
	 */
	private static class RandomString {

		private static final char[] symbols;

		static {
			StringBuilder tmp = new StringBuilder();
			for (char ch = '0'; ch <= '9'; ++ch)
				tmp.append(ch);
			for (char ch = 'a'; ch <= 'z'; ++ch)
				tmp.append(ch);
			symbols = tmp.toString().toCharArray();
		}

		private final Random random = new Random();

		private final char[] buf;

		public RandomString(int length) {
			if (length < 1)
				throw new IllegalArgumentException("length < 1: " + length);
			buf = new char[length];
		}

		public String nextString() {
			for (int idx = 0; idx < buf.length; ++idx)
				buf[idx] = symbols[random.nextInt(symbols.length)];
			return new String(buf);
		}
	}
}
