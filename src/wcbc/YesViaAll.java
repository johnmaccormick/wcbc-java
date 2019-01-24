package wcbc;

import java.io.IOException;

/**
 * SISO program YesViaAll.java
 * 
 * Performs a reduction from YesOnString to YesOnAll.
 * 
 * progString: a Java program P
 * 
 * inString: An input string I
 * 
 * returns: if the oracle function yesOnAll worked correctly, this program would
 * return "yes" if P(I) is "yes", and "no" otherwise.
 * 
 */
public class YesViaAll implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		utils.writeFile("progString.txt", progString);
		utils.writeFile("inString.txt", inString);
		YesOnAll yesOnAll = new YesOnAll();
		String ignoreInput = utils.readFile("ignoreInput.java");
		String val = yesOnAll.siso(ignoreInput);
		return val;
	}

    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSiso2Args(args);
		String progString = args[0];
		String inString = args[1];
		YesViaAll yesViaAll = new YesViaAll();
		String result = yesViaAll.siso(progString, inString);
		System.out.println(result);
	}


}
