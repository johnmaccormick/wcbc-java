package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class YesViaNumCharsTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = {
		        {"containsGAGA.java", "TTTTGAGATT", "yes"},
		        {"containsGAGA.java", "TTTTGAGTT", "no"},
		        {"isEmpty.java", "", "yes"},
		        {"isEmpty.java", "x", "no"},
		    };
		YesViaNumChars yesViaNumChars = new YesViaNumChars();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String inString = v[1];
			String solution = v[2];
			String val = yesViaNumChars.siso(progString, inString);
			assertEquals(val, solution);
		}
	}


}
