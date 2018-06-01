package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class DfaTest {

	@Test
	void testWrite() throws IOException, WcbcException {
		String[] testVals = { "containsGAGA.dfa", "multipleOf5.dfa" };

		for (String v : testVals) {

			String description = utils.rf(v);
			Dfa dfa = new Dfa(description);
			String description2 = dfa.write();
			Dfa dfa2 = new Dfa(description2);
			String description3 = dfa2.write();
			assertEquals(description2, description3);
		}
	}

	@Test
	void testRun() throws IOException, WcbcException {
		String[][] testVals = { { "containsGAGA.dfa", "CCCCCCCCCAAAAAA", "no" },
				{ "containsGAGA.dfa", "CCCGAGACCAAAAAA", "yes" }, { "multipleOf5.dfa", "12345", "yes" },
				{ "multipleOf5.dfa", "1234560", "yes" }, { "multipleOf5.dfa", "123456", "no" } };

		for (String[] v : testVals) {
			String filename = v[0];
			String inString = v[1];
			String solution = v[2];
			String description = utils.rf(filename);
			String tapeStr = inString;
			Dfa dfa = new Dfa(description, tapeStr, 0, "test");
			dfa.startKeepingHistory();
			String val = dfa.run();
			assertEquals(val, solution);
		}

	}

	@Test
	void testClone() throws IOException, WcbcException, CloneNotSupportedException {
		String[] testVals = { "containsGAGA.dfa", "multipleOf5.dfa" };

		for (String v : testVals) {

			String description = utils.rf(v);
			Dfa dfa = new Dfa(description);
			Dfa dfaClone = (Dfa) dfa.clone();
			String description2 = dfa.write();
			String descriptionClone = dfaClone.write();
			assertEquals(description2, descriptionClone);
		}
	}

//	@Test
//	void testPrintTransitions() throws WcbcException, IOException {
//		String[] testVals = { "containsGAGA.dfa", "multipleOf5.dfa" };
//
//		for (String v : testVals) {
//
//			String description = utils.rf(v);
//			Dfa dfa = new Dfa(description);
//			dfa.printTransitions();
//		}
//	}

}
