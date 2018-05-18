package wcbc;

import java.io.IOException;

/**
 * SISO program AlterGAGAtoTATA.java
 * 
 * Given string encoding program P and input I, return P(I) except "GAGA"
 * becomes "TATA". As an extra convenience when running from the command line,
 * if the first argument is "-f" then the following argument will be interpreted
 * as a filename whose contents should be used as the string P, and the argument
 * after that is treated as I. Then, P and I are encoded as a single string
 * before being passed to an AlterGAGAtoTATA instance.
 * 
 * inString: Encodes Python program P and an input I for P, encoded via
 * utils.ESS()
 * 
 * returns: P(I), unless P(I)="GAGA" in which case "TATA" is returned.
 * 
 * Example:
 * 
 * >>> java AlterGAGAtoTATA -f repeatCAorGA.java GA
 *
 * 
 */
public class AlterGAGAtoTATA implements Siso {

	@Override
	public String siso(String inString) throws WcbcException {
		String[] components = utils.DESS(inString);
		String progString = components[0];
		String newInString = components[1];
		Universal universal = new Universal();
		String val = universal.siso(progString, newInString);
		if (val.equals("GAGA")) {
			return "TATA";
		} else {
			return val;
		}
	}

	public static void main(String[] args) throws IOException, WcbcException {
		utils.checkSiso2Args(args);
		String progString = "";
		String inString = "";
		if (args[0].equals("-f")) {
			progString = utils.readFile(utils.prependWcbcPath(args[1]));
			inString = args[2];
		} else {
			progString = args[0];
			inString = args[1];
		}
		AlterGAGAtoTATA alterGAGAtoTATA = new AlterGAGAtoTATA();
		String result = alterGAGAtoTATA.siso(utils.ESS(progString, inString));
		System.out.println(result);
	}

}
