package wcbc;

import java.io.IOException;

/**
 * SISO program YesOnSelf.java
 * 
 * This is an APPROXIMATE solution to the computational problem YesOnSelf, which
 * is in fact uncomputable. As an extra convenience when running from the
 * command line, if the first argument is "-f" then the following argument will
 * be interpreted as a filename whose contents should be used as the string P.
 * 
 * progString: a Python program P
 * 
 * returns: if this program did in fact solve YesOnSelf, it would return "yes"
 * if P(P) is "yes", and "no" otherwise. However, this is an approximate version
 * that relies on simulating P(P), so it only returns in the cases where P halts
 * on input P. See yesOnString.java for details.
 * 
 * 
 */
public class YesOnSelf implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) throws WcbcException {
		YesOnString yesOnString = new YesOnString();
		String result = yesOnString.siso(inString, inString);
		return result;
	}

	public static void main(String[] args) throws IOException, WcbcException {
		utils.checkSisoArgs(args);
		String inString = "";
		if (args[0].equals("-f")) {
			inString = utils.readFile(utils.prependWcbcPath(args[1]));
		} else {
			inString = args[0];
		}
		YesOnSelf yesOnSelf = new YesOnSelf();
		String result = yesOnSelf.siso(inString);
		System.out.println(result);
	}

}
