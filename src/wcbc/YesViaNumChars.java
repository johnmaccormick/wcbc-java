package wcbc;

import java.io.IOException;

/**
 * SISO program YesViaNumChars.java
 * 
 * Performs a reduction from YesOnString to NumCharsOnString.
 * 
 * progString: a Java program P
 * 
 * inString: An input string I
 * 
 * returns: if the oracle function numCharsOnString worked correctly, this
 * program would return "yes" if P(I) is "yes", and "no" otherwise.
 * 
 */
public class YesViaNumChars implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		String singleString = utils.ESS(progString, inString);
		NumCharsOnString numCharsOnString = new NumCharsOnString();
		String alterProg = utils.readFile(utils.prependWcbcPath("AlterYesToNumChars.java"));
		String val = numCharsOnString.siso(alterProg, singleString);
		if (val.equals("3")) {
			return "yes";
		} else {
			return "no";
		}
	}

}
