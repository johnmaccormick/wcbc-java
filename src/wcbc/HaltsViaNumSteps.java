package wcbc;

import java.io.IOException;

public class HaltsViaNumSteps implements Siso2 {

	@Override
	public String siso(String progString, String inString) throws WcbcException, IOException {
		NumStepsOnString numStepsOnString = new NumStepsOnString();
		String val = numStepsOnString.siso(progString, inString);
		if (val.equals("no")) {
			return "no";
		} else {
			return "yes";
		}
	}

}
