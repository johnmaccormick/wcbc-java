package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class All3SetsTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSiso() {
		String[][] testVals = { { "", "" }, { "4 5", "" }, { "4 5 6", "{4,5,6}" },
				{ "4 5 6 7", "{4,5,6} {4,5,7} {4,6,7} {5,6,7}" },
				{ "4 5 6 7 8", "{4,5,6} {4,5,7} {4,5,8} {4,6,7} {4,6,8} {4,7,8} {5,6,7} {5,6,8} {5,7,8} {6,7,8}" }, };

		All3Sets all3Sets = new All3Sets();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = all3Sets.siso(inString);
			assertEquals(val, solution);
		}
	}

}
