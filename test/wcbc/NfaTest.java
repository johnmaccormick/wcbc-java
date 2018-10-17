package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NfaTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testRun() throws IOException, WcbcException {
		String[][] testVals = { { "simple3.nfa", "AA", "yes" }, { "simple3.nfa", "AGA", "yes" },
				{ "simple3.nfa", "AC", "yes" }, { "simple3.nfa", "AG", "yes" }, { "simple3.nfa", "ACCGCG", "yes" },
				{ "simple3.nfa", "", "no" }, { "simple3.nfa", "A", "no" }, { "simple3.nfa", "G", "no" },
				{ "simple3.nfa", "AAA", "no" },

		};

		for (String[] v : testVals) {
			String filename = v[0];
			String inString = v[1];
			String solution = v[2];
			String description = utils.rf(utils.prependWcbcPath(filename));
			String tapeStr = inString;
			Nfa nfa = new Nfa(description, tapeStr, "test", true);
			String val = null;
			try {
				val = nfa.run();
			} catch (WcbcException e) {
				if (e.getMessage().startsWith(NDTuringMachine.exceededMaxClonesMsg)) {
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

}
