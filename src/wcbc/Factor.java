package wcbc;

/**
 * SISO program Factor.java
 * 
 * Solves the computational problem Factor. Given an input integer M, it returns
 * the smallest nontrivial factor of M, or "no" if no such factor exists. A
 * naive exponential time algorithm is employed.
 * 
 * inString: an integer M.
 * 
 * returns: the smallest nontrivial factor of M, or "no" if no such factor
 * exists
 * 
 * Example: 
 *
 * > java wcbc/Factor 15
 *
 * 3
 * 
 */
public class Factor implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {
		int M = Integer.parseInt(inString);
		for (int i = 2; i < M; i++) {
			if(M % i == 0) {
				return Integer.toString(i);
			}
		}
		return "no";
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		Factor factor = new Factor();
		String result = factor.siso(inString);
		System.out.println(result);
	}

}
