package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FactorTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSiso() {
		int[] testVals = { 1, 2, 3, 4, 5, 10, 15, 36, 37, 49, 97, 121, 0, -5 };

		Factor factor = new Factor();

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
