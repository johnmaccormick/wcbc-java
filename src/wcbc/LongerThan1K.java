package wcbc;

/**
 * SISO program LongerThan1K.java
 * 
 * Returns "yes" if the input is longer than 1000 characters and "no" otherwise.
 *
 * Example: > java wcbc/LongerThan1K abcdefghijkl
 *
 * no
 */
public class LongerThan1K implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {
		if (inString.length() > 1000) {
			return "yes";
		} else {
			return "no";
		}
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		LongerThan1K longerThan1K = new LongerThan1K();
		String result = longerThan1K.siso(inString);
		System.out.println(result);
	}

}
