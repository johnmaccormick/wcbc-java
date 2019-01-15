package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ProvableInPeanoTest {

	@Test
	void testProvableInPeano() throws WcbcException, IOException {
		// isPeanoProof() works only for one particular string ("0=0") in our
		// artificial, skeletal implementation for testing purposes. So we test only
		// that string.
		ProvableInPeano provableInPeano = new ProvableInPeano();
		assertEquals("yes", provableInPeano.siso("0=0"));
	}

}
