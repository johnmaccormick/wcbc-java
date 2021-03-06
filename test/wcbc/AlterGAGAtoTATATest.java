package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class AlterGAGAtoTATATest {

	@Test
	void testSiso() throws IOException, WcbcException {
		String[][] testVals = { { "repeatCAorGA.java", "CA", "CACA" }, { "repeatCAorGA.java", "GA", "TATA" } };
		AlterGAGAtoTATA alterGAGAtoTATA = new AlterGAGAtoTATA();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			String inString = v[1];
			String solution = v[2];
			String val = alterGAGAtoTATA.siso(utils.ESS(progString, inString));
			assertEquals(val, solution);
		}
	}

}
