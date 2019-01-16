package wcbc;

import java.io.IOException;

/**
 * SISO program ConvertSatToClique.java
 * 
 * Convert an instance of SAT into an equivalent instance of Clique.
 * 
 * inString: an instance of SAT, formatted as described in the textbook and
 * Sat.java.
 * 
 * returns: an instance of Clique, formatted as described in the textbook.
 * 
 * Example:
 * 
 * > java wcbc/ConvertSatToClique("x1 OR x2 AND NOT x1 OR NOT x2 AND x2")
 * 
 * "x1nC2,x2pC1 x1nC2,x2pC3 x1pC1,x2nC2 x1pC1,x2pC3 x2pC1,x2pC3;3"
 */
public class ConvertSatToClique implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
