package wcbc;

import java.io.IOException;

/**
 * SISO program YesOnSome.java
 * 
 * This is an APPROXIMATE version of a program solving the computational problem
 * yesOnSome, which is itself uncomputable.
 * 
 * progString: A Java program P
 * 
 * returns: the program attempts to return "yes" if P(I)=="yes" for some I, and
 * "no" otherwise, but it in fact tests only a few random values of I.
 * 
 */
public class YesOnSome implements Siso {

	@Override
	public String siso(String progString) throws WcbcException, IOException {
		final int numTests = 1000;
		Universal universal = new Universal();
		for (int i = 0; i < numTests; i++) {
			String s = utils.randomLenAlphanumericString();
			String val = universal.siso(progString, s);
			if (val.equals("yes")) {
				return "yes";
			}
		}
		// special check for "GAGA", since that is used often as an example
		if (universal.siso(progString, "GAGA").equals("yes")) {
			return "yes";
		}
		// special check for empty string, since that is used often as an example
		if (universal.siso(progString, "").equals("yes")) {
			return "yes";
		}
		// nothing returned "yes", so guess that it never returns "yes"
		return "no";
	}
}
