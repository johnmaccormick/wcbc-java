package wcbc;

import java.io.IOException;

/**
 * SISO program G.java
 * 
 * This function is a placeholder for a generic computable function G. This
 * particular choice of G returns the first character of the input string.
 * 
 */
public class G implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		if (inString.length() >= 1) {
			return inString.substring(0, 1);
		} else {
			return "";
		}
	}

}
