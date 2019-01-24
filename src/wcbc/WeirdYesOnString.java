package wcbc;

import java.io.IOException;

/**
 * SISO program WeirdYesOnString.java
 * 
 * This program is deliberately crafted to enable a certain proof by
 * contradiction. Given input string progString representing a Python program P,
 * weirdYesOnString returns "no" if P(P) is "yes"; otherwise, "no" is returned.
 * 
 * As an extra convenience when running from the
 * command line, if the first argument is "-f" then the following argument will
 * be interpreted as a filename whose contents should be used as the string P.
 * 
 */
public class WeirdYesOnString implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) throws WcbcException {
		// if yesOnString(progString, progString)=="yes":
		// return "no"
		// else:
		// return "yes"

		YesOnString yesOnString = new YesOnString();
		String result = yesOnString.siso(inString, inString);
		if (result.equals("yes")) {
			return "no";
		} else {
			return "yes";
		}
	}

	public static void main(String[] args) throws IOException, WcbcException {
		utils.checkSisoArgs(args);
		String inString = "";
		if (args[0].equals("-f")) {
			inString = utils.readFile(args[1]);
		} else {
			inString = args[0];
		}
		WeirdYesOnString weirdYesOnString = new WeirdYesOnString();
		String result = weirdYesOnString.siso(inString);
		System.out.println(result);
	}

}
