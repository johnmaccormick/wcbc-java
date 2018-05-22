package wcbc;

import java.io.IOException;

/**
 * SISO programs P1, P2, P3, P4
 * 
 * These programs are used as examples in Chapter 7, demonstrating the
 * difficulty in general of the computational problem ComputesIsEven.
 * 
 */
public class P1 implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		long n = Long.parseLong(inString);
		if (n % 2 == 0) {
			return "yes";
		} else {
			return "no";
		}
	}

}
