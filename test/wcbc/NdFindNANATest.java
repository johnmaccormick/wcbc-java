package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class NdFindNANATest {

	@Test
	void testSiso() throws WcbcException, IOException {
//		String geneticString = utils.rf(utils.prependWcbcPath("geneticString.txt"));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 100000; i++) {
			sb.append("T");
		}
		String allTs = sb.toString();

		String[][] testVals = { { "GAGA", "GAGA" }, { "CCCGAGA", "GAGA" }, { "AGAGAGAAGAAGAGAAA", "GAGA" },
				{ "GAGACCCCC", "GAGA" }, { "", "no" }, { "CCCCCCCCGAGTTTT", "no" }, { "afaCACAgs", "CACA" },
				{ "sklgjsgTATA", "TATA" }, { "AAAAjklasgas", "AAAA" }, { allTs, "no" }, };

		NdFindNANA findNANA = new NdFindNANA();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = findNANA.siso(inString);
			assertEquals(val, solution);
		}
	}

}
