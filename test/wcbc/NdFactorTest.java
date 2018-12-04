package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class NdFactorTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		int[] testVals = { 1, 2, 3, 4, 5, 10, 15, 36, 37, 49, 97, 121, 0, -5 };

		NdFactor factor = new NdFactor();

		for (int v : testVals) {
			String inString = Integer.toString(v);
			String val = factor.siso(inString);
			if (v < 2 || utils.isPrime(v)) {
				assertEquals(val, "no");
			} else {
				assertEquals(v % Integer.parseInt(val), 0);
			}
		}
	}

}
