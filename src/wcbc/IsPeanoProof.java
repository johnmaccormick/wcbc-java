package wcbc;

/**
 * SISO program IsPeanoProof.java
 * 
 * Determine whether a given proof is actually a proof of a given statement in
 * Peano Arithmetic.
 * 
 * proofString: a mechanical proof in Peano Arithmetic
 * 
 * inString: a statement in Peano Arithmetic
 * 
 * returns: If implemented correctly, this function would return "yes" if
 * proofString is a proof, in Peano Arithmetic, of the Peano Arithmetic
 * statement inString. Otherwise it returns "no". This function could certainly
 * be implemented -- it"s simply a matter of checking whether each line in the
 * proof is an axiom or follows from the earlier lines by one of the inference
 * rules, and also checking that the last line of the proof is the same as the
 * given inString. In practice, however, the implementation is rather
 * complicated and is omitted here. We instead implement a single trivial case
 * so that certain other functions can be tested.
 */
public class IsPeanoProof implements Siso2 {

	@Override
	public String siso(String proofString, String inString) {
		// To permit testing of certain other functions, we return a
		// correct value for proving "0=0". This is not actually an axiom
		// in the usual formulations of Peano arithmetic, but for testing
		// purposes let"s assume that it is an axiom. So for our purposes,
		// the proof of "0=0" consists only of the string "0=0".
		if (proofString.equals("0=0") && inString.equals("0=0")) {
			return "yes";
		} else {
			// Obviously, this could be wrong. We only do this for testing purposes.
			return "no";
		}
	}

    public static void main(String[] args) {
		utils.checkSiso2Args(args);
		String proofString = args[0];
		String inString = args[1];
		IsPeanoProof isPeanoProof = new IsPeanoProof();
		String result = isPeanoProof.siso(proofString, inString);
		System.out.println(result);
	}


}
