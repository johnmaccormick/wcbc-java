package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class VerifyTspDPolytimeTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		VerifyTspDPolytime vtsp = new VerifyTspDPolytime();

		assertEquals("correct", vtsp.siso("a,b,5 a,c,9 b,d,1 d,c,6;22", "yes", "a,b,d,c"));
		assertEquals("correct", vtsp.siso("a,b,5 a,c,9 b,d,1 d,c,6;21", "yes", "a,b,d,c"));
		assertEquals("unsure", vtsp.siso("a,b,5 a,c,9 b,d,1 d,c,6;20", "yes", "a,b,d,c"));
		assertEquals("unsure", vtsp.siso("a,b,5 a,c,9 b,d,1 d,c,6;23", "yes", "a,b,d"));
		assertEquals("unsure", vtsp.siso("a,b,5 a,c,9 b,d,1 d,c,6;23", "yes", "a,b,c,d"));
	}

}
