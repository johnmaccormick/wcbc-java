package wcbc;

import java.io.IOException;

/**
 * SISO program YesViaEmpty.java
 * 
 * Performs a reduction from YesOnString to YesOnEmpty.
 * 
 * progString: a Java program P
 * 
 * inString: An input string I
 * 
 * returns: if the oracle function yesOnEmpty worked correctly, this program
 * would return "yes" if P(I) is "yes", and "no" otherwise.
 * 
 */
public class YesViaEmpty implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		utils.writeFile(utils.prependWcbcPath("progString.txt"), progString);
		utils.writeFile(utils.prependWcbcPath("inString.txt"), inString);
		YesOnEmpty yesOnEmpty = new YesOnEmpty();
		String ignoreInput = utils.readFile(utils.prependWcbcPath("ignoreInput.java"));
		String val = yesOnEmpty.siso(ignoreInput);
		return val;
	}

}
