package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class Gtest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "", "" }, { "x", "x" }, { "abcdef", "a" }, };
		G g = new G();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = g.siso(inString);
			assertEquals(val, solution);
		}
	}

}
