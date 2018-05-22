package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class AlterYesToComputesFTest {

	static final String Finput = "xxxx";
	static final String F_of_Finput = "4";
	static final String G_of_Finput = "x";

	@Test
	void testSiso() throws IOException, WcbcException {
		String[][] testVals = { { "containsGAGA.java", "GAGAGAGAG", F_of_Finput },
				{ "containsGAGA.java", "TTTTGGCCGGT", G_of_Finput } };
		AlterYesToComputesF alterYesToComputesF = new AlterYesToComputesF();

		for (String[] v : testVals) {
			String progString = utils.readFile(utils.prependWcbcPath(v[0]));
			utils.writeFile(utils.prependWcbcPath("progString.txt"), progString);
			String inString = v[1];
			utils.writeFile(utils.prependWcbcPath("inString.txt"), inString);
			String solution = v[2];
			String val = alterYesToComputesF.siso(Finput);
			assertEquals(val, solution);
		}
	}

}
