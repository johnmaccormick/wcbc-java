package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LongestWordTest {

	@Test
	void testSiso() {
		String[][] testVals = {{"", ""},
                {"a", "a"},
                {"abc", "abc"},
                {"a bc", "bc"},
                {"bc a", "bc"},
                {"x xx xxx xxxx xxxxx xxxx xxx xx x", "xxxxx"},
                {"x xx xxx xxxx\nxxxxx xxxx\nxxx xx xxxxxxxx", "xxxxxxxx"},
                };
		
		LongestWord longestWord = new LongestWord();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = longestWord.siso(inString);
			assertEquals(val, solution);
		}		
	}

}
