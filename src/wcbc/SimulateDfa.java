package wcbc;

import java.io.IOException;

/**
 * SISO program SimulateDfa.java
 * 
 * Simulate a given dfa with a given input. As an extra convenience
 * when running from the command line, if the first argument is "-f" then the
 * following argument will be interpreted as a filename whose contents should be
 * used as the dfa description dfaString.
 * 
 * dfaString: ASCII description of the dfa M to be simulated
 * 
 * inString: the content of M's tape
 * 
 * returns: "yes" if M accepts I and "no" otherwise
 * 
 * Example: > java wcbc/SimulateDfa -f multipleOf5.dfa 3425735 
 * 
 * "yes"
 * 
 */
public class SimulateDfa implements Siso2 {

	@Override
	public String siso(String dfaString, String tapeStr) throws WcbcException, IOException {
		Dfa dfa = new Dfa(dfaString, tapeStr);
		String dfaResult = dfa.run();
		return dfaResult;
	}

	public static void main(String[] args) throws IOException, WcbcException {
		utils.checkSiso2Args(args);
		String dfaString = "";
		String tapeStr = "";
		if (args[0].equals("-f")) {
			dfaString = utils.readFile(args[1]);
			tapeStr = args[2];
		} else {
			dfaString = args[0];
			tapeStr = args[1];
		}
		SimulateDfa simulateDfa = new SimulateDfa();
		String result = simulateDfa.siso(dfaString, tapeStr);
		System.out.println(result);
	}

}
