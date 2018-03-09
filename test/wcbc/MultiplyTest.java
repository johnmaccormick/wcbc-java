package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MultiplyTest {

	@Test
	void testSiso() {
		String[][] testVals = {{"4 5", "20"},
                {"100 10000", "1000000"},
                {"1024 256", "262144"}};

		Multiply multiply = new Multiply();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = multiply.siso(inString);
			assertEquals(val, solution);
		}
	}

}

