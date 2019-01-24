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

    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSiso2Args(args);
		String progString = args[0];
		String inString = args[1];
		HaltsViaNumSteps haltsViaNumSteps = new HaltsViaNumSteps();
		String result = haltsViaNumSteps.siso(progString, inString);
		System.out.println(result);
	}


}
