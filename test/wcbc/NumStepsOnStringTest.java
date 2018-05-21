package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class NumStepsOnStringTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { 
				{"containsGAGA.java", "GAGAGAGAG", "some number"},
				};
		NumStepsOnString numStepsOnString = new NumStepsOnString();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String inString = v[1];
			String solution = v[2];
			String val = numStepsOnString.siso(progString, inString);
			assertTrue(!val.isEmpty()); // weak test ... too bad
		}
	}

}
