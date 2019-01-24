package wcbc;

import java.io.IOException;

/**
 * SISO program YesViaComputesF.java
 * 
 * Performs a reduction from YesOnString to ComputesF.
 * 
 * progString: a Java program P
 * 
 * inString: An input string I
 * 
 * returns: if the oracle function computesF worked correctly, this program
 * would return "yes" if P(I) is "yes", and "no" otherwise.
 * 
 */
public class YesViaComputesF implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		ComputesF computesF = new ComputesF();
		utils.writeFile("progString.txt", progString);
		utils.writeFile("inString.txt", inString);
		String alterProgString = utils.readFile("AlterYesToComputesF.java");
		String val = computesF.siso(alterProgString);
		if (val.equals("yes")) {
			return "yes";
		} else {
			return "no";
		}
	}

    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSiso2Args(args);
		String progString = args[0];
		String inString = args[1];
		YesViaComputesF yesViaComputesF = new YesViaComputesF();
		String result = yesViaComputesF.siso(progString, inString);
		System.out.println(result);
	}


}
