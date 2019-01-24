package wcbc;

/**
 * SISO program ConvertHaltToPeano.java
 * 
 * No implementation is provided. A real implementation of this program would be
 * long, tedious, and messy. However, the discussion in the textbook explains
 * how this program could be implemented if desired. If the siso method were
 * implemented, then this program would take as input a Python program P, and it
 * would return a Peano arithmetic string with the meaning "P halts on empty
 * input."
 */
public class ConvertHaltToPeano implements Siso {

	@Override
	public String siso(String inString) {
		return "not implemented";
	}

    public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		ConvertHaltToPeano convertHaltToPeano = new ConvertHaltToPeano();
		String result = convertHaltToPeano.siso(inString);
		System.out.println(result);
	}


}
