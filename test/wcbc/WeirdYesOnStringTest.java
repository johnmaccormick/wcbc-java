package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class WeirdYesOnStringTest {

	@Test
	void testSiso() throws IOException, WcbcException {
		String[][] testVals = { { "containsGAGA.java", "no" }, { "isEmpty.java", "yes" }, };

		WeirdYesOnString weirdYesOnString = new WeirdYesOnString();

		for (String[] v : testVals) {
			String inString = utils.readFile(utils.prependWcbcPath(v[0]));
			String solution = v[1];
			String val = weirdYesOnString.siso(inString);
			assertEquals(val, solution);
		}
	}

}
