package wcbc;

/**
 * SISO program FactorUnary.java
 * 
 * Solves the computational problem FactorUnary. Given an input consisting of M
 * 1's, it returns the smallest nontrivial factor of M, or "no" if no such
 * factor exists. A naive exponential time algorithm is employed.
 * 
 * inString: a string consisting of M 1's.
 * 
 * returns: the smallest nontrivial factor of M, or "no" if no such factor
 * exists
 * 
 * Example: > java wcbc/FactorUnary 111111111111111 
 * 
 * "3"
 * 
 */
public class FactorUnary implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {
		int M = inString.length();
		String Mstr = Integer.toString(M);
		return new Factor().siso(Mstr);
	}
	
	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		FactorUnary factorUnary = new FactorUnary();
		String result = factorUnary.siso(inString);
		System.out.println(result);
	}

}
