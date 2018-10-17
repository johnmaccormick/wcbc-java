package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class MatchingCharIndicesTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = {{"bat ball", "0,0 1,1"},
                {"hello world", "2,3 3,3 4,1"},
                {"a b", ""},
                {"aaa aaaaa", "0,0 0,1 0,2 0,3 0,4 1,0 1,1 1,2 1,3 1,4 2,0 2,1 2,2 2,3 2,4"},
                {"abcdefgh fghijklmnop", "5,0 6,1 7,2"},
    };
		
		MatchingCharIndices matchingCharIndices = new MatchingCharIndices();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = matchingCharIndices.siso(inString);
			assertEquals(val, solution);
		}		
	}

}
