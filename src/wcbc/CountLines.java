package wcbc;

/**
 * SISO program CountLines.java
 * 
 * Return the number of lines in the input.
 * 
 * inString: a string S
 * 
 * returns: The number of lines in S.
 *
 * 
 */
public class CountLines implements Siso {

	@Override
	public String siso(String inString) {
		// split on newlines
		String[] lines = inString.split("\n");
		// return the number of lines, as a string
		return Integer.toString(lines.length);
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		CountLines countLines = new CountLines();
		String result = countLines.siso(inString);
		System.out.println(result);
	}
}
