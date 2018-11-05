package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AllSubsetsTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSiso() {
		String[][] testVals = { { "", "{}" }, { "4", "{} {4}" },
				{ "4 5 6", "{} {4} {5} {4,5} {6} {4,6} {5,6} {4,5,6}" }, };

		AllSubsets allSubsets = new AllSubsets();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = allSubsets.siso(inString);
			assertEquals(solution, val);
		}
	}

}
