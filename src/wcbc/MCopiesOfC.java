package wcbc;

/**
 * SISO program MCopiesOfC.java
 * 
 * inString: string representing an integer M. An exception will be raised if
 * inString does not represent an integer.
 * 
 * returns: a string consisting of M copies of the character "C".
 * 
 * Example: 
 *
 * > java wcbc/MCopiesOfC 12
 *
 * CCCCCCCCCCCC
 * 
 */
public class MCopiesOfC implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {
		int M = Integer.parseInt(inString);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < M; i++) {
			sb.append("C");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		MCopiesOfC mCopiesOfC = new MCopiesOfC();
		String result = mCopiesOfC.siso(inString);
		System.out.println(result);
	}

}
