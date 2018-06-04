package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class NDTuringMachineTest {

	@Test
	void testWrite() throws IOException, WcbcException {
		String[] testVals = { "containsGAGAorGTGT.tm", "manyClones.tm", "loop.tm", "GthenOneT.tm" };

		for (String v : testVals) {

			String description = utils.rf(v);
			NDTuringMachine tm = new NDTuringMachine(description);
			String description2 = tm.write();
			NDTuringMachine tm2 = new NDTuringMachine(description2);
			String description3 = tm2.write();
			assertEquals(description2, description3);
		}
	}
	
	@Test
	void testRun() throws IOException, WcbcException {
		String[][] testVals = { {"containsGAGAorGTGT.tm", "GTGAGAGAGT", "yes"},
	            {"containsGAGAorGTGT.tm", "GTGAGTGTGT", "yes"},
	            {"containsGAGAorGTGT.tm", "GTGAGTGAGT", "no"},
	            {"manyClones.tm", "CGCGCGCGCGCGCGCCCCCCCCC", NDTuringMachine.exceededMaxClonesMsg},
	            {"loop.tm", "x", TuringMachine.exceededMaxStepsMsg},
	            {"GthenOneT.tm", "xCCCCCTCCGTTx", "yes"},
	            {"GthenOneT.tm", "xCCCCCCCGTTGCATGx", "yes"},
	            {"GthenOneT.tm", "xCCTCCTCCGTTx", "no"},
	            {"GthenOneT.tm", "xCCCCCCCGTTGCATGTTx", "no"},
	            {"GthenOneT.tm", "xGTx", "yes"}, 
	            
		};

		for (String[] v : testVals) {
			String filename = v[0];
			String inString = v[1];
			String solution = v[2];
			String description = utils.rf(utils.prependWcbcPath(filename));
			String tapeStr = inString;
			NDTuringMachine tm = new NDTuringMachine(description, tapeStr, "test", true);
			String val = null;
			try {
				val = tm.run();
			} catch (WcbcException e) {
				if(e.getMessage().startsWith(NDTuringMachine.exceededMaxClonesMsg)) {
					val = NDTuringMachine.exceededMaxClonesMsg;
				} else if (e.getMessage().startsWith(TuringMachine.exceededMaxStepsMsg)) {
					val = TuringMachine.exceededMaxStepsMsg;
				} else {
					throw e;
				}
			}
			assertEquals(val, solution);
		}

	}



//	@Test
//	void testPrintTransitions() throws WcbcException, IOException {
//		String[] testVals = { "containsGAGAorGTGT.tm", "manyClones.tm", "loop.tm", "GthenOneT.tm" };
//
//		for (String v : testVals) {
//
//			String description = utils.rf(v);
//			NDTuringMachine tm = new NDTuringMachine(description);
//			tm.printTransitions();
//		}
//	}
	

}
