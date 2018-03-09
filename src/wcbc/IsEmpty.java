package wcbc;

/**
 * SISO program IsEmpty.java
 * 
 * Returns "yes" if its input is the empty string and "no" otherwise.
 * 
 */
public class IsEmpty implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {
		if (inString.equals("")) {
			return "yes";
		} else {
			return "no";
		}
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		IsEmpty isEmpty = new IsEmpty();
		String result = isEmpty.siso(inString);
		System.out.println(result);
	}

}
