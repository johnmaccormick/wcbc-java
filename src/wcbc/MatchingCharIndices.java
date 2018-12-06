package wcbc;

import java.io.IOException;
import java.util.ArrayList;

/**
 * SISO program MatchingCharIndices.java
 * 
 * This program computes solutions to the computational problem
 * MatchingCharIndices.
 * 
 * inString: A single string consisting of two alphanumeric ASCII words w1, w2
 * separated by a space.
 * 
 * returns: A list of all pairs of indices in the input words that match. More
 * precisely, writing w_m[i] for the ith character of word m, the solution is a
 * list of pairs i, j such that w_1[i] = w_2[ j]. Pairs are separated by space
 * characters, and the indices in a pair are written in decimal notation
 * separated by a comma.
 * 
 * example: > java wcbc/MatchingCharIndices "hello world"
 * 
 * "2,3 3,3 4,1"
 */
public class MatchingCharIndices implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		// split the input into a list of 2 words
		String[] words = utils.splitOnWhitespace(inString);
		String word1 = words[0];
		String word2 = words[1];

		// create an empty list that will store pairs of indices as strings
		ArrayList<String> pairs = new ArrayList<>();

		// append every relevant pair of indices to the pairs list
		for (int i = 0; i < word1.length(); i++) {
			for (int j = 0; j < word2.length(); j++) {
				if (word1.charAt(i) == word2.charAt(j)) {
					String thisPair = Integer.toString(i) + "," + Integer.toString(j);
					pairs.add(thisPair);
				}
			}
		}
		
	    // concatenate all the pairs together, separated by space characters
	    return utils.join(pairs, " "); 
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		MatchingCharIndices matchingCharIndices = new MatchingCharIndices();
		String result = matchingCharIndices.siso(inString);
		System.out.println(result);
	}
}
