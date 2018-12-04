package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VerifyFactorTest {

	@Test
	void testSiso() {
		VerifyFactor vf = new VerifyFactor();

		assertEquals("correct", vf.siso("20","4", ""));
		assertEquals("unsure", vf.siso("20","12", ""));
		assertEquals("unsure", vf.siso("20","no", ""));
		assertEquals("correct", vf.siso("20","5", ""));
		assertEquals("correct", vf.siso("20","2", ""));
		assertEquals("unsure", vf.siso("20","11", ""));
		assertEquals("unsure", vf.siso("31","-5", ""));
		assertEquals("unsure", vf.siso("31","0", ""));
		assertEquals("unsure", vf.siso("31","1", ""));
		assertEquals("unsure", vf.siso("31","2", ""));
		assertEquals("unsure", vf.siso("31","3", ""));
		assertEquals("unsure", vf.siso("31","no", ""));
	}

}
