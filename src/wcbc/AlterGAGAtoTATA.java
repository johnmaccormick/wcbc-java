package wcbc;

/**
 * SISO program AlterGAGAtoTATA.java
 * 
 * Given string encoding program P and input I, return P(I) except "GAGA"
 * becomes "TATA".
 * 
 * inString: Encodes Python program P and an input I for P, encoded via
 * utils.ESS()
 * 
 * returns: P(I), unless P(I)="GAGA" in which case "TATA" is returned.
 * 
 * Example:
 * 
 * >>> alterGAGAtoTATA(utils.ESS(rf("repeatCAorGA.java"), "GA"))
 *
 * 
 */
public class AlterGAGAtoTATA implements Siso {

	@Override
	public String siso(String inString) throws WcbcException {
//	    (progString, newInString) = utils.DESS(inString) 
//	    	    val = universal(progString, newInString)  
//	    	    if val == "GAGA": 
//	    	        return "TATA"
//	    	    else:
//	    	        return val 
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
