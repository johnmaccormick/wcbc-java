package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class RecYesOnStringTest {


	@Test
	void testSiso() throws IOException, WcbcException {
		String[][] testVals = { { "containsGAGA.java", "ACACAGGGAGACC", "yes" }, { "containsGAGA.java", "GA", "no" } };
		RecYesOnString recYesOnString = new RecYesOnString();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String inString = v[1];
			String solution = v[2];
			String val = recYesOnString.siso(utils.ESS(progString, inString));
			assertEquals(val, solution);
		}
	}

}
