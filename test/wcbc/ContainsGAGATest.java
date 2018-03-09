package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContainsGAGATest {

	@Test
	void testSiso() {
		String[][] testVals = { { "GAGA", "yes" }, { "CCCGAGA", "yes" }, { "AGAGAGAAGAAGAGAAA", "yes" },
				{ "GAGACCCCC", "yes" }, { "", "no" }, { "CCCCCCCCGAGTTTT", "no" }, };

		ContainsGAGA containsGAGA = new ContainsGAGA();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = containsGAGA.siso(inString);
			assertEquals(val, solution);
		}
	}

}
