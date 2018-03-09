package wcbc;

/**
 * SISO program containsGAGAandCACAandTATA.py
 * 
 * Return "yes" if the input contains all three of the strings "GAGA", "CACA",
 * "TATA". Otherwise return "no".
 * 
 * inString: the string to be searched
 * 
 * returns: "yes" if the input contains all three of the strings "GAGA", "CACA",
 * "TATA". Otherwise return "no"
 * 
 * Example: 
 * 
 * > java wcbc/ContainsGAGAandCACAandTATA CCCCCCGAGATTTATA 
 * 
 * no
 *
 * 
 */
public class ContainsGAGAandCACAandTATA implements Siso {

	@Override
	public String siso(String inString) {
		ContainsGAGA containsGAGA = new ContainsGAGA();

		if (containsGAGA.siso(inString).equals("yes") && containsCACA(inString) && containsTATA(inString)) {
			return "yes";
		} else {
			return "no";
		}
	}

	private boolean containsCACA(String searchString) {
		return containsSubstring(searchString, "CACA");
	}

	private boolean containsTATA(String searchString) {
		return containsSubstring(searchString, "TATA");
	}

	private boolean containsSubstring(String searchString, String subString) {
		return searchString.contains(subString);
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		ContainsGAGAandCACAandTATA containsGAGAandCACAandTATA = new ContainsGAGAandCACAandTATA();
		String result = containsGAGAandCACAandTATA.siso(inString);
		System.out.println(result);
	}

}
