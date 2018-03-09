package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MaybeLoopTest {

	@Test
	void testSiso() {
		String[][] testVals = { 
//				{ "", null }, { "sdfjkhask", null }, 
				{ "secret sauce", "yes" },
				{ "xsecret sauce", "no" }, { "xsecret saucex", "yes" }, };

		MaybeLoop maybeLoop = new MaybeLoop();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = maybeLoop.siso(inString);
			assertEquals(val, solution);
		}
	}

}
