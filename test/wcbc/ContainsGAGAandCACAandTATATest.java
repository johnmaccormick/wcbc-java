package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContainsGAGAandCACAandTATATest {

	@Test
	void testSiso() {
		String[][] testVals = { { "GAGACACATATA", "yes" }, { "CTATACCACACGAGA", "yes" },
				{ "AGAGAGAAGAAGAGAATATAACACA", "yes" }, { "", "no" }, { "CCCCCCCCGAGTTTT", "no" },
				{ "CCCACAGAGACCCCCGAGTTTT", "no" }, { "CCTATAGAGACCCCCGAGTTTT", "no" },
				{ "CCTATACACACCCCCGAGTTTT", "no" }, };

		ContainsGAGAandCACAandTATA containsGAGAandCACAandTATA = new ContainsGAGAandCACAandTATA();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = containsGAGAandCACAandTATA.siso(inString);
			assertEquals(val, solution);
		}

	}
}
