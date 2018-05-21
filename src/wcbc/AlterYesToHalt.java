package wcbc;

import java.io.IOException;

/**
 * SISO program AlterYesToHalt.java
 * 
 * Given string encoding program P and input I, return "GAGA" if P(I)="yes" and
 * otherwise return "no".
 * 
 * inString: Encodes Java program P and an input I for P, encoded via
 * utils.ESS()
 * 
 * returns: "halted" if P(I)="yes" and otherwise enters an infinite loop.
 * 
 */
public class AlterYesToHalt implements Siso {
	@Override
	public String siso(String inString) throws WcbcException, IOException {
		String[] components = utils.DESS(inString);
		String progString = components[0];
		String newInString = components[1];
		Universal universal = new Universal();
		String val = universal.siso(progString, newInString);
		if (val.equals("yes")) {
			// return value is irrelevant, since returning any string halts
	        return "halted"; 
		} else {
			// deliberately enter infinite loop
	        utils.loop();
	        return null;
		}
	}

}
