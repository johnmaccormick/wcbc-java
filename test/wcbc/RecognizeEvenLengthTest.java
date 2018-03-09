package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RecognizeEvenLengthTest {

	@Test
	void testSiso() {
		String[][] testVals = { { "", "yes" }, { "xx", "yes" }, { "xxxx", "yes" }, };

		RecognizeEvenLength recognizeEvenLength = new RecognizeEvenLength();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = recognizeEvenLength.siso(inString);
			assertEquals(val, solution);
		}
	}

}
