package wcbc;

/**
 * SISO program VerifyFactor.java
 * 
 * Verifies a solution to the computational problem Factor.
 * 
 * The parameters I, S, H are the instance, proposed solution, and hint for a
 * verifier as described in Chapter 12.
 * 
 * The return value is as described in Chapter 12 for verifiers: "correct" if S
 * was successfully verified, and "unsure" otherwise.
 *
 * Example:
 * 
 * > java wcbc/VerifyFactor 15 5 asdf
 * 
 * correct
 */
public class VerifyFactor implements Siso3 {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString, String solution, String hint) {
		if (solution.equals("no")) {
			return "unsure";
		}
		int M = Integer.parseInt(inString);
		int m = Integer.parseInt(solution);
		if (m >= 2 && m < M && M % m == 0) {
			// m is a nontrivial factor of M
			return "correct";
		} else {
			// m is not a nontrivial factor of M
			return "unsure";
		}
	}

	public static void main(String[] args) {
		utils.checkSiso3Args(args);
		String inString = args[0];
		String solution = args[1];
		String hint = args[2];

		VerifyFactor verifyFactor = new VerifyFactor();
		String result = verifyFactor.siso(inString, solution, hint);
		System.out.println(result);
	}

}
