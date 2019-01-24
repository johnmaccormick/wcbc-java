package wcbc;

/**
 * SISO program HaltsViaCompletePeano.java
 * 
 * Solves the problem HaltsOnEmpty, assuming that Peano arithmetic is complete.
 * (In fact, this proves that Peano arithmetic is incomplete, since otherwise
 * the program below would contradict the undecidability of the halting
 * problem.)
 * 
 * inString: a Java program P
 * 
 * returns: If Peano arithmetic were complete (which it is not) this program
 * would return "yes" if the computation of P("") halts and otherwise return
 * "no".
 * 
 * Note that the program employs two unimplemented methods, isPeanoProof.siso()
 * and convertHaltToPeano.siso(). These are indeed computable functions and
 * could be implemented in Java, but their implementations would be long and
 * tedious and are not provided.
 * 
 */
public class HaltsViaCompletePeano implements Siso {

	@Override
	public String siso(String inString) {
		ConvertHaltToPeano convertHaltToPeano = new ConvertHaltToPeano();
		IsPeanoProof isPeanoProof = new IsPeanoProof();

		String haltInPeano = convertHaltToPeano.siso(inString);
		String notHaltInPeano = "NOT " + haltInPeano;
		String proofString = "";

		while (true) {
			if (isPeanoProof.siso(proofString, haltInPeano).equals("yes")) {
				return "yes";
			} else if (isPeanoProof.siso(proofString, notHaltInPeano).equals("yes")) {
				return "no";
			}
	        proofString = utils.nextASCII(proofString);  
		}
	}

    public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		HaltsViaCompletePeano haltsViaCompletePeano = new HaltsViaCompletePeano();
		String result = haltsViaCompletePeano.siso(inString);
		System.out.println(result);
	}


}
