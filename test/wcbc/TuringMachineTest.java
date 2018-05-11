package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TuringMachineTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testTuringMachineStringStringIntString() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}

	@Test
	void testGetOutput() {
		fail("Not yet implemented");
	}

	@Test
	void testRun() throws IOException, WcbcException {
		String[][] testVals = { { "containsGAGA.tm", "CCCCCCCCCAAAAAA", "no" },
				{ "containsGAGA.tm", "CCCGAGACCAAAAAA", "yes" }, { "binaryIncrementer.tm", "x100111x", "x101000x" }, 
				{"countCs.tm", "xGCGCGCACGCGGGx", "x101x"}};

		for (String[] v : testVals) {
			String filename = v[0];
			String inString = v[1];
			String solution = v[2];
			String description = utils.rf(filename);
			String tapeStr = inString;
			TuringMachine tm = new TuringMachine(description, tapeStr, 0, "test");
			tm.startKeepingHistory();
			String val = tm.run();
			assertEquals(val, solution);
		}

	}

	@Test
	void testClone() {
		fail("Not yet implemented");
	}

	@Test
	void testPrintTransitions() {
		fail("Not yet implemented");
	}

	@Test
	void testHaveSameTransitions() {
		fail("Not yet implemented");
	}

}
