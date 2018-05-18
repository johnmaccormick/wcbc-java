package wcbc;

import java.io.IOException;
import java.math.BigInteger;

/**
 * SISO program IsOddViaReduction.java
 * 
 * Returns "yes" if the input is an odd integer and "no" otherwise, computing
 * the solution via a reduction to the problem LastDigitIsEven. An exception
 * will be thrown if the input string does not represent an integer.
 * 
 */
public class IsOddViaReduction implements Siso {


	@Override
	public String siso(String inString) throws WcbcException, IOException {
		BigInteger i = new BigInteger(inString);
		BigInteger iPlus1 = i.add(new BigInteger("1"));
		LastDigitIsEven lastDigitIsEven = new LastDigitIsEven();
		String result = lastDigitIsEven.siso(iPlus1.toString());
		return result;
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		IsOddViaReduction isOddViaReduction = new IsOddViaReduction();
		String result = isOddViaReduction.siso(inString);
		System.out.println(result);
	}	
	
}
