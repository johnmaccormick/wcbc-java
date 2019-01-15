package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class IsPeanoProofTest {

	@Test
	void testIsPeanoProof() throws WcbcException, IOException {
		IsPeanoProof ipp = new IsPeanoProof();
		assertEquals("yes", ipp.siso("0=0", "0=0"));
		assertEquals("no", ipp.siso("asdf", "zxcv"));
	}

}
