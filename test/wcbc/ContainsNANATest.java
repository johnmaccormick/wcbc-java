package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ContainsNANATest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "GAGA", "yes" }, { "CCCGAGA", "yes" }, { "AGAGAGAAGAAGAGAAA", "yes" },
				{ "GAGACCCCC", "yes" }, { "", "no" }, { "CCCCCCCCGAGTTTT", "no" }, 
				{"afaCACAgs", "yes"},
				{"sklgjsgTATA", "yes"},
				{"AAAAjklasgas", "yes"},
				};

		ContainsNANA containsNANA = new ContainsNANA();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = containsNANA.siso(inString);
			assertEquals(val, solution);
		}
	}

}
