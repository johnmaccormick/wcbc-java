package wcbc;

/**
 * SISO program ContainsGAGA.java
 * 
 * Return 'yes' if the input contains the substring 'GAGA'. Otherwise return
 * 'no'.
 * 
 * inString: the string to be searched
 * 
 * returns: 'yes' if the input contains 'GAGA'. Otherwise return 'no'
 * 
 * Example: 
 *
 * > java wcbc/ContainsGAGA CCCCCCGAGATTTATA
 *
 * yes
 * 
 */
public class ContainsGAGA implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {
		if (inString.contains("GAGA")) {
			return "yes";
		} else {
			return "no";
		}
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		ContainsGAGA containsGAGA = new ContainsGAGA();
		String result = containsGAGA.siso(inString);
		System.out.println(result);
	}

}
