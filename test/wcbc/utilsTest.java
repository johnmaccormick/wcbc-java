package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Paths;

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

}
