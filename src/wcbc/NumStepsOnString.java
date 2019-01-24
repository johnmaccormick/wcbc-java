package wcbc;

import java.io.IOException;

/**
 * SISO program NumStepsOnString.java
 * 
 * This is an APPROXIMATE solution to the computational problem
 * NumStepsOnString, which is uncomputable. In fact, this program uses elapsed
 * time as a very approximate measure of the number of "steps" in the execution
 * of the program, so it's a very poor approximation, but still useful for
 * certain types of testing.
 * 
 * progString: a Java program P
 * 
 * inString: an input string I
 * 
 * returns: if this program did in fact solve NumCharsOnString, it would return
 * the number of steps in the computation of P(I) if P(I) terminates, and "no"
 * otherwise. However, this is an approximate version that relies on simulating
 * P(I). Moreover, as mentioned above, even when P(I) terminates this program
 * returns the elapsed time and not the number of steps.
 * 
 */
public class NumStepsOnString implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		Universal universal = new Universal();
		long startTime = System.nanoTime();
		universal.siso(progString, inString);
		long elapsedTime = System.nanoTime() - startTime;
		return Long.toString(elapsedTime);
		// we should return "no" if it doesn't halt, but of course we can't actually do
		// that...
	}

    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSiso2Args(args);
		String progString = args[0];
		String inString = args[1];
		NumStepsOnString numStepsOnString = new NumStepsOnString();
		String result = numStepsOnString.siso(progString, inString);
		System.out.println(result);
	}


}
