package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MCopiesOfCTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSiso() {
		String[][] testVals = { { "0", "" }, { "1", "C" }, { "2", "CC" }, { "20", "CCCCCCCCCCCCCCCCCCCC" }, };

		MCopiesOfC mCopiesOfC = new MCopiesOfC();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = mCopiesOfC.siso(inString);
			assertEquals(val, solution);
		}
	}

}
