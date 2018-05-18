package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class LastDigitIsEvenTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "-2", "yes" }, { "0", "yes" }, { "2", "yes" }, { "3742788", "yes" }, { "-1", "no" },
				{ "1", "no" }, { "3", "no" }, { "17", "no" }, { "3953969", "no" } };

		LastDigitIsEven lastDigitIsEven = new LastDigitIsEven();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = lastDigitIsEven.siso(inString);
			assertEquals(val, solution);
		}
	}
}
