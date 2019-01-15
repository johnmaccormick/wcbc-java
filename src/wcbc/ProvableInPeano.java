package wcbc;

import java.io.IOException;

/**
 * SISO program ProvableInPeano.java
 * 
 * Determine whether a given statement in Peano Arithmetic is provable. If the
 * method isPeanoProof() were fully implemented, then the code for
 * provableInPeano() given below would recognize but not decide provable
 * statements. However, the materials do not provide a full implementation of
 * isPeanoProof(), so provableInPeano() only works for a single trivial case
 * that can be used for testing purposes.
 */
public class ProvableInPeano implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		String proofString = "";
		IsPeanoProof isPeanoProof = new IsPeanoProof();
		while (true) {
			if (isPeanoProof.siso(proofString, inString).equals("yes")) {
				return "yes";
			}
			proofString = utils.nextASCII(proofString);
		}
	}
}
