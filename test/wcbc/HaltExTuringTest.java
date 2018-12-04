package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class HaltExTuringTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String progString = utils.readFile(utils.prependWcbcPath("containsGAGA.tm"));
		String Gs = "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG";
		HaltExTuring haltEx = new HaltExTuring();
		for (int i = 0; i < 10; i++) {
			String inString = Gs.substring(0, i);
			TuringMachine sim = new TuringMachine(progString, inString);
			sim.run();
			int stepCutoff = (int) Math.exp(Math.log(2.0) * inString.length());
			String val = null;
			if (sim.getSteps() < stepCutoff) {
				val = "yes";
			} else {
				val = "no";
			}
			String solution = haltEx.siso(progString, inString);
			assertEquals(val, solution);
		}
	}

}
