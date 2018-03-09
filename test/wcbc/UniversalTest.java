package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class UniversalTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "containsGAGA.java", "asdf", "no" },
				{ "containsGAGA.java", "GGGGGACCCCGAGATTT", "yes" },
				{ "multiply.java", "100 10000", (new Long(100 * 10000)).toString() },
				{ "multiply.java", "1024 256", (new Long(1024 * 256)).toString() }, };

		Universal universal = new Universal();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String inString = v[1];
			String solution = v[2];
			String val = universal.siso(progString, inString);
			assertEquals(val, solution);
		}
	}

}
