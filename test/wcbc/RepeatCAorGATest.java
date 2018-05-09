package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RepeatCAorGATest {

	@Test
	void testSiso() throws WcbcException {
		String[][] testVals = { { "CA", "CACA" }, 
					{ "GA", "GAGA" }, 
					{ "CCCCTTTAA", "unknown" } };

		RepeatCAorGA repeatCAorGA = new RepeatCAorGA();
		
		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = repeatCAorGA.siso(inString);
			assertEquals(val, solution);
		}
	}

}
