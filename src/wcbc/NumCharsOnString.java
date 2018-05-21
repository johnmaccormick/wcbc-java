package wcbc;

import java.io.IOException;

public class NumCharsOnString implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		Universal universal = new Universal();
		String val = universal.siso(progString, inString);
		return Integer.toString(val.length());
	}
}
