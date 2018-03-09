package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class YesOnSelfTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "containsGAGA.java", "yes" }, { "isEmpty.java", "no" }, };

		YesOnSelf yesOnSelf = new YesOnSelf();

		for (String[] v : testVals) {
			String inString = utils.readFile(utils.prependWcbcPath(v[0]));
			String solution = v[1];
			String val = yesOnSelf.siso(inString);
			assertEquals(val, solution);
		}
	}

}
