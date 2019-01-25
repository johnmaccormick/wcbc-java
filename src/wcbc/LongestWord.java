package wcbc;

/**
 * SISO program LongestWord.java
 * 
 * Returns the longest word in the input string. Here, a "word" is a sequence of
 * characters surrounded by whitespace.
 *
 * Example: 
 *
 * > java wcbc/LongestWord "apple banana orange"
 *
 * banana
 */
public class LongestWord implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {
		String[] words = utils.splitOnWhitespace(inString);
		String longest = "";
		int lengthOfLongest = 0;
		for (String word : words) {
			if (word.length() > lengthOfLongest) {
				longest = word;
				lengthOfLongest = word.length();
			}
		}
		return longest;
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		LongestWord longestWord = new LongestWord();
		String result = longestWord.siso(inString);
		System.out.println(result);
	}

}
