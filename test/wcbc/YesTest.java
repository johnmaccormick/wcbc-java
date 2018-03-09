package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class YesTest {

	@Test
	void testSiso() {
		Yes yes = new Yes();

		String[][] testVals = { { "", "yes" }, { "x", "yes" }, { "asdf", "yes" }, { "GAGAGAGAGAGA", "yes" }, };

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = yes.siso(inString);
			assertEquals(val, solution);
		}
	}

}
