package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class GAGAOnStringTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "containsGAGA.java", "GAGAGAGAG", "no" }, { "repeatCAorGA.java", "CA", "no" },
				{ "repeatCAorGA.java", "GA", "yes" } };
		GAGAOnString gAGAOnString = new GAGAOnString();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String inString = v[1];
			String solution = v[2];
			String val = gAGAOnString.siso(progString, inString);
			assertEquals(val, solution);
		}
	}

}
