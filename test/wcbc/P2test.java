package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class P2test {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "0", "yes" }, { "2", "yes" }, { "4", "yes" }, { "100", "yes" }, { "1", "no" },
				{ "3", "no" }, { "5", "no" }, { "101", "no" }, };
		P2 p2 = new P2();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = p2.siso(inString);
			assertEquals(val, solution);
		}
	}

}
