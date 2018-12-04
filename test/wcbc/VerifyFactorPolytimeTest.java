package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VerifyFactorPolytimeTest {

	@Test
	void testSiso() {
		VerifyFactorPolytime vfp = new VerifyFactorPolytime();

		assertEquals("correct", vfp.siso("20","4", ""));
		assertEquals("unsure", vfp.siso("20","12", ""));
		assertEquals("unsure", vfp.siso("20","no", ""));
		assertEquals("correct", vfp.siso("20","5", ""));
		assertEquals("correct", vfp.siso("20","2", ""));
		assertEquals("unsure", vfp.siso("20","11", ""));
		assertEquals("unsure", vfp.siso("31","-5", ""));
		assertEquals("unsure", vfp.siso("31","0", ""));
		assertEquals("unsure", vfp.siso("31","1", ""));
		assertEquals("unsure", vfp.siso("31","2", ""));
		assertEquals("unsure", vfp.siso("31","3", ""));
		assertEquals("unsure", vfp.siso("31","no", ""));
	}

}
