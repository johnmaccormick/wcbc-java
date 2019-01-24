package wcbc;

import java.io.IOException;

/**
 * SISO program HaltExTuring.java
 * 
 * Solves the computational problem haltEx, for the case of input programs
 * represented as Turing machines.
 * 
 * progString: An ASCII description of a Turing machine M
 * 
 * inString: The initial content I of the Turing machine"s tape
 * 
 * returns: If the computation of M(I) halts in fewer than 2^len(I) steps,
 * return "yes" and otherwise return "no".
 * 
 * As an extra convenience when running from the command line, if the first
 * argument is "-f" then the following argument will be interpreted as a
 * filename whose contents should be used as the machine M, and the argument
 * after that is treated as I.
 * 
 * Example:
 * 
 * > java wcbc/HaltExTuring -f containsGAGA.tm TTTTTTTT
 * 
 * "yes"
 */
public class HaltExTuring implements Siso2 {
	
	static final int maxInStrLen = (int) (Math.log(Integer.MAX_VALUE) / Math.log(2.0));  


	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
	    // construct the Turing machine simulator
		TuringMachine sim = new TuringMachine(progString, inString); 

		// simulate for at most $2^n-1$ steps
		if(inString.length()>maxInStrLen) {
			throw new WcbcException("Input string is too long for simulation");
		}
		int maxSteps = (int) Math.exp(Math.log(2.0)*inString.length()) - 1;  
		for(int i=0; i<maxSteps; i++) {
			sim.step();
			if(sim.isHalted()) {
				return "yes";
			}
		}
		return "no";
	}

	
	public static void main(String[] args) throws IOException, WcbcException {
		utils.checkSiso2Args(args);
		String progString = "";
		String inString = "";
		if (args[0].equals("-f")) {
			progString = utils.readFile(args[1]);
			inString = args[2];
		} else {
			progString = args[0];
			inString = args[1];
		}
		HaltExTuring haltExTuring = new HaltExTuring();
		String result = haltExTuring.siso(progString, inString);
		System.out.println(result);
	}
	
	
	
	
	
}
