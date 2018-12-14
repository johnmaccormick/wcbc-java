package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class Simulate2TDCMTest {

	@Test
	void testSiso() throws IOException, WcbcException {
		String inString = "";
		String tmString = utils.readFile(utils.prependWcbcPath("unarySequence.tm"));
		String outString = new Simulate2TDCM().siso(tmString, inString);
		assertTrue(outString.contains("0010110111011110111110111111011111110111111110"));
	}

}
