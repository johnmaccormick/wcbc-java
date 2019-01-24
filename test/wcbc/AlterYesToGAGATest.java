package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class AlterYesToGAGATest {


	@Test
	void testSiso() throws IOException, WcbcException {
		String[][] testVals = { { "containsGAGA.java", "GAGAGAGAG", "GAGA" }, { "containsGAGA.java", "ATATACCC", "no" } };
		AlterYesToGAGA alterYesToGAGA = new AlterYesToGAGA();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String inString = v[1];
			String solution = v[2];
			String val = alterYesToGAGA.siso(utils.ESS(progString, inString));
			assertEquals(val, solution);
		}
	}

}
