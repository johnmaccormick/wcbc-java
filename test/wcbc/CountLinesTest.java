package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CountLinesTest {

	@Test
	void testSiso() {
		String[][] testVals = { { "", "1" }, // It is weird for the empty string to
				// contain one line, but this is what the
				// simple implementation above returns, and
				// it"s best to keep the simplest possible
				// implementation for this tutorial example.
				{ "abc", "1" }, { "abc\n", "1" }, { "\n", "0" }, { "abc\ndef", "2" }, { "abc\ndef\nghi", "3" },
				{ "abc\ndef\nghi\n", "3" }, };
		CountLines countLines = new CountLines();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = countLines.siso(inString);
			assertEquals(val, solution);
		}
	}

}
