package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class HalfUhcTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		String[][] testVals = { {"", "yes"},
                {"a,b", "no"},
                {"a,a", "yes"},
                {"a,b b,c", "no"},
                {"a,b b,c c,a", "yes"},
                {"a,b b,c c,a d,e d,f", "yes"},
                {"a,b b,c c,a d,e f,g", "no"},
                {"a,b b,c c,a a,d", "yes"},
                {"a,b b,c c,a a,d b,d", "yes"},
                {"a,b b,c c,d d,e e,f f,g g,h h,i i,j h,a", "yes"},
                {"a,b b,c c,d d,e e,f f,g g,h h,i i,j d,a", "no"}, };

		HalfUhc halfUhc = new HalfUhc();

		for (String[] v : testVals) {
			String inString = v[0];
			String solution = v[1];
			String val = halfUhc.siso(inString);
			if (solution.equals("no")) {
				assertEquals(solution, val);
			} else {
				Graph graph = new Graph(inString, false, false);
				Path path = Path.fromString(val);
				assertTrue(graph.isCycle(path));
				assertTrue(graph.cycleLength(path)>= graph.getNumNodes()/2);
			}
		}
	}

}
