package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class Ftest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "", "0" }, { "x", "1" }, { "abcdef", "6" }, };
		F f = new F();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = f.siso(inString);
			assertEquals(val, solution);
		}
	}

}
