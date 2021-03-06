package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class NumCharsOnStringTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { 
				{"containsGAGA.java", "GAGAGAGAG", "3"}, 
                {"containsGAGA.java", "TTTTGGCCGGT", "2"} };
		NumCharsOnString numCharsOnString = new NumCharsOnString();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String inString = v[1];
			String solution = v[2];
			String val = numCharsOnString.siso(progString, inString);
			assertEquals(val, solution);
		}
	}

}
