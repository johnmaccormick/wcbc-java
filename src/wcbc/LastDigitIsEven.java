package wcbc;

import java.io.IOException;

/**
 * SISO program IsEven.java
 * 
 * Returns "yes" if the last character of the input is an even digit and "no"
 * otherwise.
 * 
 */
public class LastDigitIsEven implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		int len = inString.length();
		String lastDigit = inString.substring(len-1, len);
		final String evens = "02468";
		if (evens.contains(lastDigit)) {
			return "yes";
		} else {
			return "no";
		}
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		LastDigitIsEven lastDigitIsEven = new LastDigitIsEven();
		String result = lastDigitIsEven.siso(inString);
		System.out.println(result);
	}

}
