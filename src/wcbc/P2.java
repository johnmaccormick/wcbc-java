package wcbc;

import java.io.IOException;

/**
 * SISO programs P1, P2, P3, P4
 * 
 * These programs are used as examples in Chapter 7, demonstrating the
 * difficulty in general of the computational problem ComputesIsEven.
 * 
 */
public class P2 implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		int len = inString.length();
		String lastDigit = inString.substring(len-1, len);
		final String evens = "02468";
		if (evens.contains(lastDigit)) {
			return "yes";
		} else {
			return "no";
		}
	}

}
