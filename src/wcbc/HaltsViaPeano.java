package wcbc;

import java.io.IOException;

/**
 * SISO program HaltsViaPeano.java
 * 
 * Reduces HaltsOnEmpty to TrueInPeano. (In fact, this proves that Peano
 * arithmetic is undecidable, since otherwise the program below would contradict
 * the undecidability of the halting problem.)
 * 
 * inString: a Java program P
 * 
 * returns: If Peano arithmetic were decidable (which it is not) this program
 * would return "yes" if the computation of P("") halts and otherwise return
 * "no".
 * 
 * Note that the program relies on the unimplemented method
 * convertHaltToPeano.siso(). This is indeed a computable function and could be
 * implemented in Java, but its implementation would be long and tedious and is
 * not provided.
 * 
 */
public class HaltsViaPeano implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		ConvertHaltToPeano convertHaltToPeano = new ConvertHaltToPeano();
		TrueInPeano trueInPeano = new TrueInPeano();
		String haltInPA = convertHaltToPeano.siso(inString);
		return trueInPeano.siso(haltInPA);
	}

}
