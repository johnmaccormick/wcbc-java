package wcbc;

/**
 * SISO program RepeatCAorGA.java
 *
 * This simple function is used in the book to illustrate program-altering
 * programs. If the input is "CA", then "CACA" is returned. If the input is
 * "GA", then "GAGA" is returned. For all other inputs, "unknown" is returned.
 *
 */
public class RepeatCAorGA implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) throws WcbcException {
		if (inString.equals("CA")) {
			return "CACA";
		} else if (inString.equals("GA")) {
			return "GAGA";
		} else {
			return "unknown";
		}
	}

	/**
	 * @param args
	 * @throws WcbcException 
	 */
	public static void main(String[] args) throws WcbcException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		RepeatCAorGA repeatCAorGA = new RepeatCAorGA();
		String result = repeatCAorGA.siso(inString);
		System.out.println(result);
	}

}
