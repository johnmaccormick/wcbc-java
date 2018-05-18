package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class YesOnEmptyTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "ContainsGAGA.java", "no" },
				{ "IsEmpty.java", "yes" },
				{ "Yes.java", "yes" }, };
		YesOnEmpty yesOnEmpty = new YesOnEmpty();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String solution = v[1];
			String val = yesOnEmpty.siso(progString);
			assertEquals(val, solution);
		}
	}

}
