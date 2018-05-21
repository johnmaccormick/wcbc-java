package wcbc;

import java.io.IOException;

/**
 * SISO program AlterYesToNumChars.java
 * 
 * Given string encoding program P and input I, return "xxx" if P(I)="yes" and
 * otherwise return "xx". This allows us to distinguish P(I) based on the number
 * of characters returned.
 * 
 * inString: Encodes Java program P and an input I for P, encoded via
 * utils.ESS()
 * 
 * returns: "xxx" if P(I)="yes" and otherwise "xx".
 * 
 */
public class AlterYesToNumChars implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		String[] components = utils.DESS(inString);
		String progString = components[0];
		String newInString = components[1];
		Universal universal = new Universal();
		String val = universal.siso(progString, newInString);
		if (val.equals("yes")) {
			// return a string with three characters
			return "xxx";
		} else {
			// return a string with two characters
			return "xx";
		}
	}
}
