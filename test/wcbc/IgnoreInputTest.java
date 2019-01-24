package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class IgnoreInputTest {

	@Test
	void testSiso() throws IOException, WcbcException {
		String[][] testVals = { { "containsGAGA.java", "GAGAGAGAG", "yes" },
				{ "containsGAGA.java", "TTTTCCACCC", "no" } };
		IgnoreInput ignoreInput = new IgnoreInput();
		for (String[] v : testVals) {
			
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			utils.writeFile(utils.prependWcbcPath("progString.txt"), progString);
			String inString = v[1];
			utils.writeFile(utils.prependWcbcPath("inString.txt"), inString);
			String solution = v[2];
			String val = ignoreInput.siso("irrelevant input");
			assertEquals(val, solution);
		}
	}

}
