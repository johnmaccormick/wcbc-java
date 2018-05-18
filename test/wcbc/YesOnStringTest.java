package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class YesOnStringTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = {
		        {"ContainsGAGA.java", "TTTTGAGATT", "yes"},
		        {"ContainsGAGA.java", "TTTTGAGTT", "no"},
		        {"IsEmpty.java", "", "yes"},
		        {"IsEmpty.java", "x", "no"},
		    };
		YesOnString yesOnString = new YesOnString();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String inString = v[1];
			String solution = v[2];
			String val = yesOnString.siso(progString, inString);
			assertEquals(val, solution);
		}
	}

}

