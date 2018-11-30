package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TspPathTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { { "a,b,5;a;b", "5" }, { "a,b,5 b,c,6 c,d,8 d,a,9 a,c,1 d,b,2;a;b", "11" },
				{ "a,b,5 b,c,6 c,d,8 d,a,9 a,c,1 d,b,2 c,e,10 d,e,20;a;b", "33" },
				{ "a,b,5 b,c,6 d,a,9 a,c,1 d,b,2;a;b", "no" }, { "a,b,5 b,c,6 d,a,9 a,c,1;a;b", "no" }, };

		TspPath tspPath = new TspPath();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = tspPath.siso(inString);
			if (solution.equals("no")) {
				assertEquals(solution, val);
			} else {
				String components[] = inString.split(";");
				utils.trimAll(components);
				String graphStr = components[0];
				Graph graph = new Graph(graphStr, true, false);
				Path path = Path.fromString(val);
				int dist = graph.pathLength(path);
				assertTrue(graph.isHamiltonPath(path));
				assertEquals(Integer.parseInt(solution), dist);
			}
		}
	}

}
