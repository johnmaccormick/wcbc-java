package wcbc;

import java.io.IOException;

/**
 * SISO program YesOnAll.java
 * 
 * This is an APPROXIMATE version of a program solving the computational problem
 * yesOnAll, which is itself uncomputable.
 * 
 * progString: A Java program P
 * 
 * returns: the program attempts to return "yes" if P(I)=="yes" for all I, and
 * "no" otherwise, but it in fact tests only a few random values of I.
 */
public class YesOnAll implements Siso {

	@Override
	public String siso(String progString) throws WcbcException, IOException {
		final int numTests = 1000;
		Universal universal = new Universal();
		for (int i = 0; i < numTests; i++) {
			String s = utils.randomLenAlphanumericString();
			String val = universal.siso(progString, s);
			if (!val.equals("yes")) {
				return "no";
			}
		}
		return "yes";
	}

    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		YesOnAll yesOnAll = new YesOnAll();
		String result = yesOnAll.siso(inString);
		System.out.println(result);
	}


}
