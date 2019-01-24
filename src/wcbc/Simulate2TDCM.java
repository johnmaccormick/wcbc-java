package wcbc;

import java.io.IOException;

/**
 * SISO program Simulate2TDCM.java
 * 
 * Simulate a given 2TDCM with a given input. As an extra convenience when
 * running from the command line, if the first argument is "-f" then the
 * following argument will be interpreted as a filename whose contents should be
 * used as the Turing machine description tmString. The special value "-b" can
 * be given as the tape string to represent a blank tape.
 * 
 * tmString: ASCII description of the 2TDCM to be simulated
 * 
 * inString: the initial content of the machines tape
 * 
 * returns: As discussed in the book, a "good" 2TDCM runs forever. In practice,
 * this implementation returns a message stating the outcome and final tape
 * contents when either a maximum number of steps are completed or the machine
 * becomes "stuck" (i.e. enters a halting state).
 * 
 * Example:
 * 
 * > java wcbc/Simulate2TDCM -f unarySequence.tm -b
 * 
 * "exceeded maxSteps, outTape is:0010110111011110111110111111011111110..."
 * 
 */
public class Simulate2TDCM implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		TwoTDCM twoTDCM = new TwoTDCM(progString, inString);
		String output = "";
		try {
			twoTDCM.run();
		} catch (WcbcException e) {
			if (e.getMessage().startsWith(TuringMachine.exceededMaxStepsMsg)) {
				output = TuringMachine.exceededMaxStepsMsg + "; ";
			} else {
				throw e;
			}
		}
		output += twoTDCM.getOutput();
		return output;
	}

	public static void main(String[] args) throws IOException, WcbcException {
		utils.checkSiso2Args(args);
		String tmString = "";
		String tapeStr = "";
		if (args[0].equals("-f")) {
			tmString = utils.readFile(args[1]);
			tapeStr = args[2];
		} else {
			tmString = args[0];
			tapeStr = args[1];
		}
		if (tapeStr.equals("-b")) {
			// this is the special value representing a blank tape on the command line
			tapeStr = "";
		}
		Simulate2TDCM simulate2TDCM = new Simulate2TDCM();
		String result = simulate2TDCM.siso(tmString, tapeStr);
		System.out.println(result);
	}


}
