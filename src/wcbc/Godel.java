package wcbc;

import java.io.IOException;

/**
 * SISO program Godel.java
 * 
 * This program is constructed in such a way that it allows us to prove the
 * existence of true unprovable statements; the program itself does not compute
 * anything natural or useful. The program ignores its input and instead reads
 * its own source code, converting it into a Peano arithmetic statement
 * equivalent to the statement H = "this program doesn't halt." If H is
 * provable, the program halts. Otherwise it runs forever.
 */
public class Godel implements Siso {

	@Override
	public String siso(String inString) throws IOException, WcbcException {
		ConvertHaltToPeano convertHaltToPeano = new ConvertHaltToPeano();
		ProvableInPeano provableInPeano = new ProvableInPeano();

		String godelProg = utils.rf(utils.prependWcbcPath("Godel.java"));
		String haltInPeano = convertHaltToPeano.siso(godelProg);
		String notHaltInPeano = "NOT " + haltInPeano;

		if (provableInPeano.siso(notHaltInPeano).equals("yes")) {
			return "halted"; // any value would do
		} else {
			// This line will never be executed! But anyway...
			utils.loop(); // deliberate infinite loop
		}
		// Cannot reach this line either
		return null;
	}

	// This main method provides a rudimentary test of the program. Because of the
	// infinite loop and the unimplemented methods, it would not be easy to do
	// meaningful unit testing of this program.
	public static void main(String[] args) throws IOException, WcbcException {
		Godel godel = new Godel();
		System.out.println("starting Godel.siso()...");
		// This will go into an infinite loop, but arguably that's some kind of useful
		// test of correctness.
		godel.siso("irrelevant");
	}

}
