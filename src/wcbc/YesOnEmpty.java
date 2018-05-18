package wcbc;

import java.io.IOException;

/**
 * SISO program YesOnEmpty.java
 * 
 * This is an APPROXIMATE version of a program solving the computational problem
 * yesOnEmpty, which is itself uncomputable.
 * 
 * progString: A Java program P
 * 
 * returns: the program attempts to return "yes" if P("")=="yes", and "no"
 * otherwise, but it will fail if its simulation of P doesn't terminate.
 * 
 */
public class YesOnEmpty implements Siso {

	public YesOnEmpty() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String siso(String progString) throws WcbcException, IOException {
		Universal universal = new Universal();
		String val = universal.siso(progString, "");
		if (val.equals("yes")) {
			return "yes";
		} else {
			return "no";
		}
	}

}
