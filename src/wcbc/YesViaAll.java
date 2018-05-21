package wcbc;

import java.io.IOException;

/**
 * SISO program YesViaAll.java
 * 
 * Performs a reduction from YesOnString to YesOnAll.
 * 
 * progString: a Java program P
 * 
 * inString: An input string I
 * 
 * returns: if the oracle function yesOnAll worked correctly, this program would
 * return "yes" if P(I) is "yes", and "no" otherwise.
 * 
 */
public class YesViaAll implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		utils.writeFile(utils.prependWcbcPath("progString.txt"), progString);
		utils.writeFile(utils.prependWcbcPath("inString.txt"), inString);
		YesOnAll yesOnAll = new YesOnAll();
		String ignoreInput = utils.readFile(utils.prependWcbcPath("ignoreInput.java"));
		String val = yesOnAll.siso(ignoreInput);
		return val;
	}

}
