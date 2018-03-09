package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MultiplyAllTest {

	@Test
	void testSiso() {
		
		String[][] testVals = {{"", "1"},
	                {"1", "1"},
	                {"2", "2"},
	                {"1 5", "5"},
	                {"2 5", "10"},
	                {"10 20 30", "6000"},
	                {"10 10 10 10 10 10 10 10 10 10", "10000000000"},
		};
	
		MultiplyAll multiplyAll = new MultiplyAll();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = multiplyAll.siso(inString);
			assertEquals(val, solution);
		}
	}
}
