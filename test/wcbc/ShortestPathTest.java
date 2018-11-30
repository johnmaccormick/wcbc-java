package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ShortestPathTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "a,b,5 ; a; b", "5" }, { "a,b,5 b,c,6 c,d,8 d,a,9 a,c,1 d,b,2 ; a; b", "5" },
				{ "a,b,5 b,c,6 c,d,8 d,a,9 a,c,1 d,b,2 ; a; c", "1" },
				{ "a,b,5 b,c,6 c,d,8 d,a,9 a,c,1 d,b,2 ; a; d", "7" }, { "a,b,5 b,c,6 d,a,9 a,c,1 d,b,2 ; a; b", "5" },
				{ "a,b,5 b,c,6 d,a,9 a,c,1 ; a; b", "5" }, { "a,b,1 b,c,1 c,d,1 d,e,1 ; a; d", "3" },
				{ "a,b,1 b,c,1 c,d,1 d,e,1 a,e,5; a; e", "4" }, { "a,b,1 b,c,1 c,d,1 d,e,1 a,e,5 a,c,1; a; e", "3" },
				{ "a,b,5 c,d,7 ; a; c", "no" }, };

		ShortestPath shortestPath = new ShortestPath();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = shortestPath.siso(inString);
			assertEquals(solution, val);
		}
	}
}
