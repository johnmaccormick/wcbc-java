package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class NotYesOnSelfTest {

	@Test
	void testSiso() throws IOException, WcbcException {
		String[][] testVals = { { "containsGAGA.java", "no" }, { "isEmpty.java", "yes" }, };

		NotYesOnSelf notYesOnSelf = new NotYesOnSelf();

		for (String[] v : testVals) {
			String inString = utils.readFile(utils.prependWcbcPath(v[0]));
			String solution = v[1];
			String val = notYesOnSelf.siso(inString);
			assertEquals(val, solution);
		}
	}

}
