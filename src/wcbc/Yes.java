package wcbc;

/**
 * SISO program Yes.java
 * 
 * Returns "yes" for all inputs.
 *
 * Example: 
 *
 * > java wcbc/Yes asdf
 *
 * yes
 */
public class Yes implements Siso {

	@Override
	public String siso(String inString) {
		return "yes";
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		Yes yes = new Yes();
		String result = yes.siso(inString);
		System.out.println(result);
	}
	
}
