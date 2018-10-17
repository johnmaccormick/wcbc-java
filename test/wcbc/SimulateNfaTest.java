package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimulateNfaTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSiso() throws IOException, WcbcException {
		String[][] testVals = { { "simple3.nfa", "AA", "yes" }, { "simple3.nfa", "AGA", "yes" },
				{ "simple3.nfa", "AC", "yes" }, { "simple3.nfa", "AG", "yes" }, { "simple3.nfa", "ACCGCG", "yes" },
				{ "simple3.nfa", "", "no" }, { "simple3.nfa", "A", "no" }, { "simple3.nfa", "G", "no" },
				{ "simple3.nfa", "AAA", "no" }, };

		for (String[] v : testVals) {
			String nfaString = utils.readFile(utils.prependWcbcPath(v[0]));
			String tapeStr = v[1];
			String solution = v[2];
			Nfa tm = new Nfa(nfaString, tapeStr);
			String val = tm.run();
			assertEquals(val, solution);
		}
	}

}
