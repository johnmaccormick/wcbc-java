package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

class utilsTest {

	@Test
	void readFile() throws IOException {
		String[][] testVals = { { "geneticString.txt", "CAGTGGGGCAATT", "GCGCTCGCTCA" },
				{ "wasteland.txt", "Here is no", "without water" }, };
		for (String[] v : testVals) {
			String filename = utils.prependWcbcPath(v[0]);
			String start = v[1];
			String end = v[2];
			String val = utils.readFile(filename).trim();
			assertTrue(val.startsWith(start));
			assertTrue(val.endsWith(end));
		}
	}

	@Test
	void extractClassName() throws IOException, WcbcException {
		String[][] testVals = { { "ContainsGAGA.java", "ContainsGAGA" }, { "CountLines.java", "CountLines" }, };
		for (String[] v : testVals) {
			String filename = utils.prependWcbcPath(v[0]);
			String progString = utils.readFile(filename);
			String className = v[1];
			String val = utils.extractClassName(progString);
			assertEquals(className, val);
		}
	}

	@Test
	void ESSandDESS() throws IOException, WcbcException {
		for (int i = 0; i < 100; i++) {
			String inString1 = utils.randomLenAlphanumericString();
			String inString2 = utils.randomLenAlphanumericString();
			String combined = utils.ESS(inString1, inString2);
			String[] components = utils.DESS(combined);
			assertEquals(inString1, components[0]);
			assertEquals(inString2, components[1]);
		}
	}

	@Test
	void testNextShortLex() {
		List<Character> alphabet = utils.geneticAlphabetAsList();
		String s = "";
		for(int i=0; i<30; i++) {
	        s = utils.nextShortLex(s, alphabet);
		}
		assertEquals("AGC", s);
		assertEquals("AAA", utils.nextShortLex("TT", alphabet));
		assertEquals("ACC", utils.nextShortLex("ACA", alphabet));
	}

	@Test
	void testNextASCII() {
		assertEquals("b", utils.nextASCII("a"));
		assertEquals("abcdefh", utils.nextASCII("abcdefg"));
		assertEquals("abcdefg34", utils.nextASCII("abcdefg33"));
	}

}
