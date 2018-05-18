package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimulateTMTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSiso() throws IOException, WcbcException {
		String[][] testVals = { {"containsGAGA.tm", "CCCCCCCCCAAAAAA", "no"},
	            {"containsGAGA.tm", "CCCGAGACCAAAAAA", "yes"},
	            {"binaryIncrementer.tm", "x100111x", "x101000x"} };


		for (String[] v : testVals) {
			String tmString = utils.readFile(utils.prependWcbcPath(v[0]));
			String tapeStr = v[1];
			String solution = v[2];
			TuringMachine tm = new TuringMachine(tmString, tapeStr);
			String val = tm.run();
			assertEquals(val, solution);
		}	}

}
