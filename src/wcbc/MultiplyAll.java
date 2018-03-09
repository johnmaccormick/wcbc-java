package wcbc;

/**
 * SISO program MultiplyAll.java
 * 
 * Computes the product of some integers provided as input.
 * 
 * inString: a string consisting of integers separated by whitespace.
 * 
 * returns: The product of the input integers.
 *
 * Example: > java wcbc/MultiplyAll "5 6 7"
 *
 * 210
 */
public class MultiplyAll implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso//siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {

		// split on whitespace
		String[] numbersAsStr = inString.split("\\s+");

		// convert strings to integers
		long[] numbers = new long[numbersAsStr.length];
		for (int i = 0; i < numbers.length; i++) {
			if (numbersAsStr[i] != "") {
				numbers[i] = Long.parseLong(numbersAsStr[i]);
			} else {
				numbers[i] = 1; // won't change the final product
			}
		}

		// compute the product of the numbers array
		long product = 1;
		for (long num : numbers) {
			product = product * num;
		}

		// convert the product to a string, and return it
		String productString = Long.toString(product);
		return productString;
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		MultiplyAll multiplyAll = new MultiplyAll();
		String result = multiplyAll.siso(inString);
		System.out.println(result);
	}
}
