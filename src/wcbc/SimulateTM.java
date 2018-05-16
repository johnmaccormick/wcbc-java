package wcbc;

import java.io.IOException;

/**
 * SISO program SimulateTM.java
 * 
 * Simulate a given Turing machine with a given input. As an extra convenience
 * when running from the command line, if the first argument is "-f" then the
 * following argument will be interpreted as a filename whose contents should be
 * used as the Turing machine description tmString.
 * 
 * tmString: ASCII description of the machine M to be simulated
 * 
 * inString: the initial content I of M"s tape
 * 
 * returns: the tape content when M enters a halting state
 * 
 * Example:
 * 
 * >>> java SimulateTM -f binaryIncrementer.tm x100111x
 * 
 * "x101000x"
 *
 */
public class SimulateTM implements Siso2 {

	@Override
	public String siso(String tmString, String tapeStr) throws WcbcException, IOException {
		TuringMachine tm = new TuringMachine(tmString, tapeStr);
		String tmResult = tm.run();
		return tmResult;
	}

	public static void main(String[] args) throws IOException, WcbcException {
		utils.checkSiso2Args(args);
		String tmString = "";
		String tapeStr = "";
		if (args[0].equals("-f")) {
			tmString = utils.readFile(utils.prependWcbcPath(args[1]));
			tapeStr = args[2];
		} else {
			tmString = args[0];
			tapeStr = args[1];
		}
		SimulateTM simulateTM = new SimulateTM();
		String result = simulateTM.siso(tmString, tapeStr);
		System.out.println(result);
	}

}
