package wcbc;

/**
 * SISO program no.java
 * 
 * Returns "no" for all inputs.
 *
 * Example: > java wcbc/No asdf
 *
 * no
 */
public class No implements Siso {

	@Override
	public String siso(String inString) {
		return "no";
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		No no = new No();
		String result = no.siso(inString);
		System.out.println(result);
	}
	
}
