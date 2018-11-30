package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class TspDirTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "aC,aB,1 aA,aB,1 aC,aA,1 aB,aA,1 aB,aC,1 aA,aC,1", "3" }, { "", "0" },
				{ "a,a,3", "3" }, { "a,b,4", "no" }, { "a,b,4 b,a,9", "13" }, { "a,b,4 b,a,9 b,c,6 c,a,10", "20" },
				{ "a,b,4 b,a,9 b,c,6 c,a,10 a,c,2 c,b,3", "14" }, { "a,b,4 b,a,9 b,c,6 c,a,10 a,c,2 c,b,300", "20" },
				{ "a,b,4 b,a,9 b,c,6 c,a,10 a,c,2 c,b,3 c,d,1", "no" }, };

		TspDir tspDir = new TspDir();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = tspDir.siso(inString);
			if (solution.equals("no")) {
				assertEquals(solution, val);
			} else {
				Graph graph = new Graph(inString, true, true);
				Path cycle = Path.fromString(val);
				int dist = graph.cycleLength(cycle);
				assertTrue(graph.isHamiltonCycle(cycle));
				assertEquals(Integer.parseInt(solution), dist);
			}
		}
	}
}
