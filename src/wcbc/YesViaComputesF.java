package wcbc;

import java.io.IOException;

/**
 * SISO program YesViaComputesF.java
 * 
 * Performs a reduction from YesOnString to ComputesF.
 * 
 * progString: a Java program P
 * 
 * inString: An input string I
 * 
 * returns: if the oracle function computesF worked correctly, this program
 * would return "yes" if P(I) is "yes", and "no" otherwise.
 * 
 */
public class YesViaComputesF implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		ComputesF computesF = new ComputesF();
		utils.writeFile(utils.prependWcbcPath("progString.txt"), progString);
		utils.writeFile(utils.prependWcbcPath("inString.txt"), inString);
		String alterProgString = utils.readFile(utils.prependWcbcPath("AlterYesToComputesF.java"));
		String val = computesF.siso(alterProgString);
		if (val.equals("yes")) {
			return "yes";
		} else {
			return "no";
		}
	}

}
