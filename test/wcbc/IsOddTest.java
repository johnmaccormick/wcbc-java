package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IsOddTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "-2", "no" }, { "0", "no" }, { "2", "no" }, { "3742788", "no" }, { "-1", "yes" },
				{ "1", "yes" }, { "3", "yes" }, { "17", "yes" }, { "3953969", "yes" }, };

		IsOdd isOdd = new IsOdd();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = isOdd.siso(inString);
			assertEquals(val, solution);
		}
	}

}
