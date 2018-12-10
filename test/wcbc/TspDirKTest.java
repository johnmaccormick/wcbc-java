package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class TspDirKTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { ";1", "no" }, { "a,a,3;1", "3" }, { "a,a,3 ; 1 ", "3" }, { "a,a,3;2", "no" },
				{ "a,b,4;1", "no" }, { "a,b,4;2", "no" }, { "a,b,4;3", "no" }, { "a,b,4 b,a,9;1", "13" },
				{ "a,b,4 b,a,9;2", "13" }, { "a,b,4 b,a,9;3", "no" }, { "a,b,4 b,a,9 b,c,6 c,a,10;2", "13" },
				{ "a,b,4 b,a,9 b,c,6 c,a,10;3", "20" }, { "a,b,4 b,a,9 b,c,6 c,a,1;2", "11" },
				{ "a,b,4 b,a,9 b,c,6 c,a,10;4", "no" }, { "a,b,4 b,a,9 b,c,6 c,a,10 a,c,2 c,b,3;3", "14" },
				{ "a,b,4 b,a,9 b,c,6 c,a,10 a,c,2 c,b,3;4", "no" }, };

		TspDirK tspDirK = new TspDirK();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = tspDirK.siso(inString);
			if (solution.equals("no")) {
				assertEquals(solution, val);
			} else {
				String components[] = inString.split(";");
				utils.trimAll(components);
				String graphStr = components[0];
				int K = Integer.parseInt(components[1]);
				Graph graph = new Graph(graphStr, true, true);
				Path cycle = Path.fromString(val);
				int dist = graph.cycleLength(cycle);
				assertTrue(graph.isCycle(cycle));
				assertTrue(cycle.getLength() >= K);
				assertEquals(Integer.parseInt(solution), dist);
			}
		}
	}
}
