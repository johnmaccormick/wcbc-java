package wcbc;

import java.io.IOException;
import java.math.BigInteger;

/**
 * SISO program IsOdd.java
 * 
 * Returns "yes" if the input is an odd integer and "no" otherwise. An exception
 * will be thrown if the input string does not represent an integer.
 */
public class IsOdd implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		BigInteger i = new BigInteger(inString);
		if (i.mod(new BigInteger("2")).equals(new BigInteger("1"))) {
			return "yes";
		} else {
			return "no";
		}
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		IsOdd isOdd = new IsOdd();
		String result = isOdd.siso(inString);
		System.out.println(result);
	}
	
}
