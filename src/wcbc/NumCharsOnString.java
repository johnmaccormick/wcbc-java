package wcbc;

import java.io.IOException;

public class NumCharsOnString implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		Universal universal = new Universal();
		String val = universal.siso(progString, inString);
		return Integer.toString(val.length());
    }
    
    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSiso2Args(args);
		String progString = args[0];
		String inString = args[1];
		NumCharsOnString numCharsOnString = new NumCharsOnString();
		String result = numCharsOnString.siso(progString, inString);
		System.out.println(result);
	}

	
}
