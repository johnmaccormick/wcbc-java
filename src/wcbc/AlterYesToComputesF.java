package wcbc;

import java.io.IOException;

/**
 * SISO program AlterYesToComputesF.java
 * 
 * Ignore input, instead loading program P and input I from files progString.txt
 * and inString.txt. We are also given some fixed functions F and G. If
 * P(I)="yes", return F(I) and otherwise return G(I).
 * 
 * inString: ignored
 * 
 * returns: F(I) if P(I)="yes", otherwise G(I).
 */
public class AlterYesToComputesF implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		String progString = utils.readFile(utils.prependWcbcPath("progString.txt"));
		String newInString = utils.readFile(utils.prependWcbcPath("inString.txt"));
		Universal universal = new Universal();
		String val = universal.siso(progString, newInString);
		F f = new F();
		G g = new G();
		if(val.equals("yes")) {
			return f.siso(inString);
		} else {
			return g.siso(inString);
		}
	}
}
