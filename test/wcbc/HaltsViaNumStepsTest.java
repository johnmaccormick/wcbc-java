package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class HaltsViaNumStepsTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { 
				{"containsGAGA.java", "GAGAGAGAG", "yes"}, 
                {"containsGAGA.java", "TTTTGGCCGGT", "yes"} };
		HaltsViaNumSteps haltsViaNumSteps = new HaltsViaNumSteps();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String inString = v[1];
			String solution = v[2];
			String val = haltsViaNumSteps.siso(progString, inString);
			assertEquals(val, solution);
		}
	}

}
