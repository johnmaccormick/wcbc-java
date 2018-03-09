package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IsEmptyTest {

	@Test
	void testSiso() {
		String[][] testVals = { { "", "yes" }, { "asdf", "no" }, };

		IsEmpty isEmpty = new IsEmpty();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = isEmpty.siso(inString);
			assertEquals(val, solution);
		}
	}

}
