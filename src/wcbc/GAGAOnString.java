package wcbc;

import java.io.IOException;

/**
 * SISO program GAGAOnString.java
 * 
 * This is an APPROXIMATE version of a program solving the computational problem
 * GAGAOnString, which is itself uncomputable.
 * 
 * progString: A Java program P
 * 
 * inString: A string I, to be thought of as an input to P
 * 
 * returns: the program attempts to return "yes" if P(I)=="GAGA" and "no"
 * otherwise, but it will fail if its simulation of P enters an infinite loop.
 */
public class GAGAOnString implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		Universal universal = new Universal();
		String val = universal.siso(progString, inString);
		if (val.equals("GAGA")) {
			return "yes";
		} else {
			return "no";
		}
	}

    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSiso2Args(args);
		String progString = args[0];
		String inString = args[1];
		GAGAOnString gAGAOnString = new GAGAOnString();
		String result = gAGAOnString.siso(progString, inString);
		System.out.println(result);
	}


}
