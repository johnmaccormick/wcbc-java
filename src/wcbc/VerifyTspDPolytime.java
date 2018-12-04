package wcbc;

import java.io.IOException;

/**
 * SISO program VerifyTspDPolytime.java
 * 
 * Verifies a solution to the computational problem TspD, in polynomial time.
 * 
 * The parameters I, S, H are the instance, proposed solution, and hint for a
 * verifier as described in Chapter 12.
 * 
 * The return value is as described in Chapter 12 for verifiers: "correct" if S
 * was successfully verified, and "unsure" otherwise.
 * 
 * Example:
 * 
 * > java wcbc/VerifyTspDPolytime "a,b,5 a,c,9 b,d,1 d,c,6;22" "yes" "a,b,d,c"
 * 
 * correct
 */
public class VerifyTspDPolytime implements Siso3 {

	@Override
	public String siso(String inString, String solution, String hint) throws WcbcException, IOException {
		// reject excessively long solutions and hints
		if (solution.length() > inString.length() || hint.length() > inString.length()) {
			return "unsure";
		}

	    // The remainder of the program is identical to verifyTspD.java
	    // ...
		
		if (solution.equals("no")) {
			return "unsure";
		}
		// extract G,L from inString, and convert to correct data types etc.
		String[] components = inString.split(";");
		utils.trimAll(components);
		String graphStr = components[0];
		String LStr = components[1];
		Graph graph = new Graph(graphStr, true, false); // undirected weighted graph
		int L = Integer.parseInt(LStr);

		// split the hint string into a list of vertices, which will
		// form a Hamilton cycle of length at most L, if the hint is correct
		Path cycle = Path.fromString(hint);

		// verify the hint is a Hamilton cycle, and has length at most L
		if (graph.isHamiltonCycle(cycle) && graph.cycleLength(cycle) <= L) {
			return "correct";
		} else {
			return "unsure";
		}
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSiso3Args(args);
		String inString = args[0];
		String solution = args[1];
		String hint = args[2];

		VerifyTspDPolytime verifyTspDPolytime = new VerifyTspDPolytime();
		String result = verifyTspDPolytime.siso(inString, solution, hint);
		System.out.println(result);
	}
}
