package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NoTest {

	@Test
	void testSiso() {
		No no = new No();

		String[][] testVals = { { "", "no" }, { "x", "no" }, { "asdf", "no" }, { "GAGAGAGAGAGA", "no" }, };

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = no.siso(inString);
			assertEquals(val, solution);
		}
	}

}
