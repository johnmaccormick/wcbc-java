package wcbc;

import java.io.IOException;

/**
 * SISO program YesViaSome.java
 * 
 * Performs a reduction from YesOnString to YesOnSome.
 * 
 * progString: a Java program P
 * 
 * inString: An input string I
 * 
 * returns: if the oracle function yesOnSome worked correctly, this program
 * would return "yes" if P(I) is "yes", and "no" otherwise.
 * 
 */
public class YesViaSome implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		utils.writeFile(utils.prependWcbcPath("progString.txt"), progString);
		utils.writeFile(utils.prependWcbcPath("inString.txt"), inString);
		YesOnSome yesOnSome = new YesOnSome();
		String ignoreInput = utils.readFile(utils.prependWcbcPath("ignoreInput.java"));
		String val = yesOnSome.siso(ignoreInput);
		return val;
	}

}
