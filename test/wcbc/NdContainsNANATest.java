package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class NdContainsNANATest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String geneticString = utils.rf(utils.prependWcbcPath("geneticString.txt"));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 100000; i++) {
			sb.append("T");
		}
		String allTs = sb.toString();

		String[][] testVals = { { "GAGA", "yes" }, { "CCCGAGA", "yes" }, { "AGAGAGAAGAAGAGAAA", "yes" },
				{ "GAGACCCCC", "yes" }, { "", "no" }, { "CCCCCCCCGAGTTTT", "no" }, { "afaCACAgs", "yes" },
				{ "sklgjsgTATA", "yes" }, { "AAAAjklasgas", "yes" }, { geneticString, "yes" }, { allTs, "no" }, };

		NdContainsNANA containsNANA = new NdContainsNANA();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = containsNANA.siso(inString);
			assertEquals(val, solution);
		}
	}

}
