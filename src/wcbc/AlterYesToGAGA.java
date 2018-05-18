package wcbc;

import java.io.IOException;

/**
 * SISO program AlterYesToGAGA.java
 * 
 * Given string encoding program P and input I, return "GAGA" if P(I)="yes" and
 * otherwise return "no".
 * 
 * inString: Encodes Java program P and an input I for P, encoded via
 * utils.ESS()
 * 
 * returns: "GAGA" if P(I)="yes" and otherwise "no".
 * 
 */
public class AlterYesToGAGA implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		String[] components = utils.DESS(inString);
		String progString = components[0];
		String newInString = components[1];
		Universal universal = new Universal();
		String val = universal.siso(progString, newInString);
		if (val.equals("yes")) {
			return "GAGA";
		} else {
			return "no";
		}
	}

}
