package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class EvaluateSatTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		EvaluateSat e = new EvaluateSat();
		assertEquals("yes", e.siso("11"));
		assertEquals("no", e.siso("01"));
	}

}
