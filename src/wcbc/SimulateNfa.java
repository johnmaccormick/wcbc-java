package wcbc;

import java.io.IOException;

/**
 * SISO program SimulateNfa.java
 * 
 * Simulate a given nfa with a given input. As an extra convenience
 * when running from the command line, if the first argument is "-f" then the
 * following argument will be interpreted as a filename whose contents should be
 * used as the nfa description nfaString.
 * 
 * nfaString: ASCII description of the nfa M to be simulated
 * 
 * inString: the content of M's tape
 * 
 * returns: "yes" if M accepts I and "no" otherwise
 * 
 * Example: > java wcbc/SimulateNfa -f simple3.nfa AGA 
 * 
 * "yes"
 * 
 */
public class SimulateNfa implements Siso2 {

	@Override
	public String siso(String nfaString, String tapeStr) throws WcbcException, IOException {
		Nfa nfa = new Nfa(nfaString, tapeStr);
		String nfaResult = nfa.run();
		return nfaResult;
	}

	public static void main(String[] args) throws IOException, WcbcException {
		utils.checkSiso2Args(args);
		String nfaString = "";
		String tapeStr = "";
		if (args[0].equals("-f")) {
			nfaString = utils.readFile(utils.prependWcbcPath(args[1]));
			tapeStr = args[2];
		} else {
			nfaString = args[0];
			tapeStr = args[1];
		}
		SimulateNfa simulateNfa = new SimulateNfa();
		String result = simulateNfa.siso(nfaString, tapeStr);
		System.out.println(result);
	}

}
