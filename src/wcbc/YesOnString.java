package wcbc;

import java.io.IOException;

/**
 * SISO program YesOnString.java
 * 
 * This is an APPROXIMATE solution to the computational problem YesOnString,
 * which is in fact uncomputable. As an extra convenience when running from the
 * command line, if the first argument is "-f" then the following argument will
 * be interpreted as a filename whose contents should be used as the string P.
 * 
 * progString: a Python program P
 * 
 * inString: an input string I
 * 
 * returns: if this program did in fact solve YesOnString, it would return "yes"
 * if P(I) is "yes", and "no" otherwise. However, this is an approximate version
 * that relies on simulating P(I), so it only returns in the cases where P
 * halts on input I.
 * 
 */
public class YesOnString implements Siso2 {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String progString, String inString) throws WcbcException {
		Universal universal = new Universal();
		String val = universal.siso(progString, inString);
		if(val.equals("yes")) {
			return "yes";
		} else {
			return "no";
		}
	}

	public static void main(String[] args) throws IOException, WcbcException {
		utils.checkSiso2Args(args);
		String progString = "";
		String inString = "";
		if (args[0].equals("-f")) {
			progString = utils.readFile(args[1]);
			inString = args[2];
		} else {
			progString = args[0];
			inString = args[1];
		}
		YesOnString yesOnString = new YesOnString();
		String result = yesOnString.siso(progString, inString);
		System.out.println(result);
	}

}
