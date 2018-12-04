package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FactorUnaryTest {

	@Test
	void testSiso() {
		int[] testVals = { 1, 2, 3, 4, 5, 10, 15, 36, 37, 49, 97, 121};

		FactorUnary factorUnary = new FactorUnary();

		// make a long string of 1's
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<1000; i++) {
			sb.append("1");
		}
		String ones = sb.toString();
		
		for (int v : testVals) {
			String inString = ones.substring(0, v);
			String val = factorUnary.siso(inString);
			if (v < 2 || utils.isPrime(v)) {
				assertEquals(val, "no");
			} else {
				assertEquals(v % Integer.parseInt(val), 0);
			}
		}
	}


}
