package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class P1test {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "0", "yes" }, { "2", "yes" }, { "4", "yes" }, { "100", "yes" }, { "1", "no" },
				{ "3", "no" }, { "5", "no" }, { "101", "no" }, };
		P1 p1 = new P1();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = p1.siso(inString);
			assertEquals(val, solution);
		}
	}

}
