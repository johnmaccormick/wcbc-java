package wcbc;

/**
 * SISO program MaybeLoop.java
 * 
 * This program is used as an example of a decision program in chapter 3. If the
 * input does not contain the string "secret sauce", the program enters an
 * infinite loop. Otherwise, the output depends on the length L of the input. If
 * L is even, "yes" is returned, otherwise "no" is returned.
 * 
 * Example: > java wcbc/MaybeLoop "abcde secret sauce"
 *
 * yes
 * 
 */
public class MaybeLoop implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {
		if (!inString.contains("secret sauce")) {
			// enter an infinite loop
			int i = 0;
			while (true) {
				i = i + 1;
			}
		} else {
			// output "yes" if input length is even and "no" otherwise
			if (inString.length() % 2 == 0) {

				return "yes";
			} else {
				return "no";
			}
		}
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		MaybeLoop maybeLoop = new MaybeLoop();
		String result = maybeLoop.siso(inString);
		System.out.println(result);
	}

}
