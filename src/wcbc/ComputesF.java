package wcbc;

import java.io.IOException;

/**
 * SISO program ComputesF.java
 * 
 * This is an APPROXIMATE version of solving the computational problem
 * computesF, which is itself an uncomputable problem. Given a program P, we
 * attempt to return "yes" if P computes a particular predefined function F, and
 * "no" otherwise.
 * 
 * progString: A Java program P.
 * 
 * returns: "yes" if P appears to compute a particular predefined function F
 * (based on some random sampling of inputs and outputs), and "no" otherwise.
 * 
 */
public class ComputesF implements Siso {

	@Override
	public String siso(String progString) throws WcbcException, IOException {
		Universal universal = new Universal();
		F f = new F();
		int iters = 100;
		for (int i = 0; i < iters; i++) {
			String inString = utils.randomLenAlphanumericString();
			String val = universal.siso(progString, inString);
			if (!val.equals(f.siso(inString))) {
				return "no";
			}
		}
		return "yes";
	}

    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		ComputesF computesF = new ComputesF();
		String result = computesF.siso(inString);
		System.out.println(result);
	}


}
