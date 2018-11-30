package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TspTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "a,b,5", "no", "None" }, { "a,b,5 b,c,6 c,d,8 d,a,9 a,c,1 d,b,2", "a,b,d,c", "16" },
				{ "a,b,5 b,c,6 c,d,8 d,a,9 a,c,1 d,b,200", "a,b,c,d", "28" },
				{ "a,b,5 b,c,6 d,a,9 a,c,1", "no", "None" }, };

		Tsp tsp = new Tsp();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String distStr = v[2];
			String val = tsp.siso(inString);
			if (solution.equals("no")) {
				assertEquals(solution, val);
			} else {
				Graph graph = new Graph(inString, true, false);
				Path cycle = Path.fromString(val);
				int dist = graph.cycleLength(cycle);
				assertTrue(graph.isHamiltonCycle(cycle));
				assertEquals(Integer.parseInt(distStr), dist);
			}
		}
	}

}
