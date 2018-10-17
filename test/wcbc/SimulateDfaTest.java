package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimulateDfaTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSiso() throws IOException, WcbcException {
		String[][] testVals = { { "containsGAGA.dfa", "CCCCCCCCCAAAAAA", "no" },
				{ "containsGAGA.dfa", "CCCGAGACCAAAAAA", "yes" }, { "multipleOf5.dfa", "12345", "yes" },
				{ "multipleOf5.dfa", "1234560", "yes" }, { "multipleOf5.dfa", "123456", "no" }, };

		for (String[] v : testVals) {
			String dfaString = utils.readFile(utils.prependWcbcPath(v[0]));
			String tapeStr = v[1];
			String solution = v[2];
			Dfa tm = new Dfa(dfaString, tapeStr);
			String val = tm.run();
			assertEquals(val, solution);
		}
	}

}
