package wcbc;

import java.io.IOException;

/**
 * SISO programs P1, P2, P3, P4
 * 
 * These programs are used as examples in Chapter 7, demonstrating the
 * difficulty in general of the computational problem ComputesIsEven.
 * 
 */
public class P4 implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		long n = Long.parseLong(inString);
		if ((3 * n + 1) % 5 == 1) {
			return "yes";
		} else {
			return "no";
		}
	}

    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		P4 p4 = new P4();
		String result = p4.siso(inString);
		System.out.println(result);
	}


}
