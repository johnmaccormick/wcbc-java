package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TwoTDCMTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	private void testOneRun(String filename, String initTapeStr, String expectedTapeStr, String expectedOutput)
			throws IOException, WcbcException {
		String tmString = utils.readFile(utils.prependWcbcPath(filename));
		TwoTDCM twoTDCM = new TwoTDCM(tmString, initTapeStr);
		String tape = null;
		try {
			tape = twoTDCM.run();
		} catch (WcbcException e) {
			if (e.getMessage().startsWith(TuringMachine.exceededMaxStepsMsg)) {
				tape = TuringMachine.exceededMaxStepsMsg;
			} else {
				throw e;
			}
		}
		String output = twoTDCM.getOutput();
		assertEquals(expectedTapeStr, tape);
		if(expectedOutput.equals("")) {
			assertEquals(expectedOutput, output);
		}
		else {
			assertTrue(output.startsWith(expectedOutput));
		}
	}

	@Test
	void testRun() throws IOException, WcbcException {
		testOneRun("containsGAGA.tm", "CCCCCCCCCAAAAAA", "no", "");
		testOneRun("containsGAGA.tm", "CCCGAGACCAAAAAA", "yes", "");
		testOneRun("loop.tm", "x", TuringMachine.exceededMaxStepsMsg, "");
		testOneRun("alternating01.tm", "", TuringMachine.exceededMaxStepsMsg, "010101010101010101010101");
		testOneRun("unarySequence.tm", "", TuringMachine.exceededMaxStepsMsg, "0010110111011110111110111111011111110111111110");
	}

}
