package wcbc;

/**
 * SISO program Multiply.java
 * 
 * Computes the product of two integers provided as input.
 * 
 * inString: a string consisting of two integers separated by whitespace.
 * 
 * returns: The product of the input integers.
 * 
 * Example: > java wcbc/Multiply "5 4"
 *
 * 20
 * 
 */
public class Multiply implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {
		String[] numbersAsStr = utils.splitOnWhitespace(inString);
		long[] numbers = new long[2];
		for (int i = 0; i < 2; i++) {
			numbers[i] = Long.parseLong(numbersAsStr[i]);
		}
		long product = numbers[0] * numbers[1];
		String productString = Long.toString(product);
		return productString;
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		Multiply multiply = new Multiply();
		String result = multiply.siso(inString);
		System.out.println(result);
	}

}
