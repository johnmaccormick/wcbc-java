package wcbc;

import java.io.IOException;

/**
 * SISO program ContainsNANA.java
 * 
 * Return "yes" if the input contains any of the substrings "CACA", "GAGA",
 * "TATA", "AAAA". Otherwise return "no".
 * 
 * inString: the string to be searched
 * 
 * returns: "yes" if the input contains any of the substrings "CACA", "GAGA",
 * "TATA", "AAAA". Otherwise return "no".
 * 
 */
public class ContainsNANA implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		final String[] strings = {"CACA", "GAGA", "TATA", "AAAA"};
		for(String s: strings) {
			if(inString.contains(s)) {
				return "yes";
			}
		}
		return "no";
	}


    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		ContainsNANA containsNANA = new ContainsNANA();
		String result = containsNANA.siso(inString);
		System.out.println(result);
	}


    
}
