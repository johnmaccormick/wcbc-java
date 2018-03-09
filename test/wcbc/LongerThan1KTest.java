package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LongerThan1KTest {

	private String copyStringNtimes(String s, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	@Test
	void testSiso() {
		String[][] testVals = { { copyStringNtimes("x", 1001), "yes" }, { copyStringNtimes("xyz", 400), "yes" },
				{ "", "no" }, { copyStringNtimes("x", 1000), "no" }, };

		LongerThan1K longerThan1K = new LongerThan1K();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = longerThan1K.siso(inString);
			assertEquals(val, solution);
		}
	}

}
