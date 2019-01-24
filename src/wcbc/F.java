package wcbc;

import java.io.IOException;

/**
 * SISO program F.java
 * 
 * This function is a placeholder for a generic computable function F. This
 * particular choice of F returns the length of the input string.
 * 
 */
public class F implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		int len = inString.length();
		return Integer.toString(len);
	}

    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		F f = new F();
		String result = f.siso(inString);
		System.out.println(result);
	}


}
