package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class YesOnSomeTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "ContainsGAGA.java", "yes" }, { "IsEmpty.java", "yes" }, { "Yes.java", "yes" },
				{ "No.java", "no" }, };
		YesOnSome yesOnSome = new YesOnSome();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String solution = v[1];
			String val = yesOnSome.siso(progString);
			assertEquals(val, solution);
		}
	}

}
