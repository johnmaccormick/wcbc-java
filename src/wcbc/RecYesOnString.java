package wcbc;

import java.io.IOException;

/**
 * SISO program RecYesOnString.java
 * 
 * This program recognizes, but does not decide, the decision problem
 * YesOnString. As an extra convenience when running from the command line, if
 * the first argument is "-f" then the following argument will be interpreted as
 * a filename whose contents should be used as the string P, and the argument
 * after that is treated as I.
 * 
 * inString: Encodes Java program P and an input I for P, encoded via
 * utils.ESS()
 * 
 * returns: If P(I) is "yes", returns "yes". If P(I) terminates but does not
 * return "yes", then "no" is returned. If P(I) enters an infinite loop, this
 * program also enters an infinite loop.
 * 
 * Example:
 * 
 * > java wcbc/RecYesOnString -f containsGAGA.java GA
 * 
 */
public class RecYesOnString implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		String[] components = utils.DESS(inString);
		String progString = components[0];
		String newInString = components[1];
		Universal universal = new Universal();
		String val = universal.siso(progString, newInString);
		if (val.equals("yes")) {
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
		RecYesOnString recYesOnString = new RecYesOnString();
		String result = recYesOnString.siso(utils.ESS(progString, inString));
		System.out.println(result);
	}

}
