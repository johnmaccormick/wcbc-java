package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ComputesFTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "containsGAGA.java", "no" }, { "F.java", "yes" }, };
		ComputesF computesF = new ComputesF();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String solution = v[1];
			String val = computesF.siso(progString);
			assertEquals(val, solution);
		}
	}

}
