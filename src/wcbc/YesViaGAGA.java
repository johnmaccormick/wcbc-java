package wcbc;

import java.io.IOException;

/**
 * SISO program yesViaGAGA.py
 * 
 * Performs a reduction from YesOnString to GAGAOnString.
 * 
 * progString: a python program P
 * 
 * inString: An input string I
 * 
 * returns: if the oracle function GAGAOnString worked correctly, this program
 * would return "yes" if P(I) is "yes", and "no" otherwise.
 * 
 */
public class YesViaGAGA implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		String singleString = utils.ESS(progString, inString);
		GAGAOnString gAGAOnString = new GAGAOnString();
		String alterProg = utils.readFile(utils.prependWcbcPath("AlterYesToGAGA.java"));
		String result = gAGAOnString.siso(alterProg, singleString);
		return result;
	}

}
