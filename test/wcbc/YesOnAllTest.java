package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class YesOnAllTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "ContainsGAGA.java", "no" },
				{ "IsEmpty.java", "no" },
				{ "Yes.java", "yes" }, };
		YesOnAll yesOnAll = new YesOnAll();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String solution = v[1];
			String val = yesOnAll.siso(progString);
			assertEquals(val, solution);
		}
	}

}
