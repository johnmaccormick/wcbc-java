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
	void testWrite() throws IOException, WcbcException {
		String[] testVals = { "containsGAGA.tm", "binaryIncrementer.tm" };

		for (String v : testVals) {

			String description = utils.rf(v);
			TuringMachine tm = new TuringMachine(description);
			String description2 = tm.write();
			TuringMachine tm2 = new TuringMachine(description2);
			String description3 = tm.write();
			assertEquals(description2, description3);
		}
	}

	@Test
	void testRun() throws IOException, WcbcException {
		String[][] testVals = { { "containsGAGA.tm", "CCCCCCCCCAAAAAA", "no" },
				{ "containsGAGA.tm", "CCCGAGACCAAAAAA", "yes" }, { "binaryIncrementer.tm", "x100111x", "x101000x" },
				{ "countCs.tm", "xGCGCGCACGCGGGx", "x101x" } };

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
	void testClone() throws IOException, WcbcException, CloneNotSupportedException {
		String[] testVals = { "containsGAGA.tm", "binaryIncrementer.tm" };

		for (String v : testVals) {

			String description = utils.rf(v);
			TuringMachine tm = new TuringMachine(description);
			TuringMachine tmClone = (TuringMachine) tm.clone();
			String description2 = tm.write();
			TuringMachine tm2 = new TuringMachine(description2);
			String descriptionClone = tmClone.write();
			assertEquals(description2, descriptionClone);
		}
	}

	@Test
	void testPrintTransitions() throws WcbcException, IOException {
		String[] testVals = { "containsGAGA.tm", "binaryIncrementer.tm" };

		for (String v : testVals) {

			String description = utils.rf(v);
			TuringMachine tm = new TuringMachine(description);
			tm.printTransitions();
		}	}


}
