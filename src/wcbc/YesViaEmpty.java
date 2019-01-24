package wcbc;

import java.io.IOException;

/**
 * SISO program YesViaEmpty.java
 * 
 * Performs a reduction from YesOnString to YesOnEmpty.
 * 
 * progString: a Java program P
 * 
 * inString: An input string I
 * 
 * returns: if the oracle function yesOnEmpty worked correctly, this program
 * would return "yes" if P(I) is "yes", and "no" otherwise.
 * 
 */
public class YesViaEmpty implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		utils.writeFile("progString.txt", progString);
		utils.writeFile("inString.txt", inString);
		YesOnEmpty yesOnEmpty = new YesOnEmpty();
		String ignoreInput = utils.readFile("ignoreInput.java");
		String val = yesOnEmpty.siso(ignoreInput);
		return val;
	}

    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSiso2Args(args);
		String progString = args[0];
		String inString = args[1];
		YesViaEmpty yesViaEmpty = new YesViaEmpty();
		String result = yesViaEmpty.siso(progString, inString);
		System.out.println(result);
	}


}
