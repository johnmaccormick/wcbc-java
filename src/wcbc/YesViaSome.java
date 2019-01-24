package wcbc;

import java.io.IOException;

/**
 * SISO program YesViaSome.java
 * 
 * Performs a reduction from YesOnString to YesOnSome.
 * 
 * progString: a Java program P
 * 
 * inString: An input string I
 * 
 * returns: if the oracle function yesOnSome worked correctly, this program
 * would return "yes" if P(I) is "yes", and "no" otherwise.
 * 
 */
public class YesViaSome implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		utils.writeFile("progString.txt", progString);
		utils.writeFile("inString.txt", inString);
		YesOnSome yesOnSome = new YesOnSome();
		String ignoreInput = utils.readFile("ignoreInput.java");
		String val = yesOnSome.siso(ignoreInput);
		return val;
	}

    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSiso2Args(args);
		String progString = args[0];
		String inString = args[1];
		YesViaSome yesViaSome = new YesViaSome();
		String result = yesViaSome.siso(progString, inString);
		System.out.println(result);
	}


}
