package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class DhcTest {

	private void checkInstance(String graphStr, boolean hasDhc) throws WcbcException, IOException {
		Graph graph = new Graph(graphStr, false, true);
		String result = new Dhc().siso(graphStr);

		if (!hasDhc) {
			assertEquals("no", result);
		} else {
			Path path = Path.fromString(result);
			assertTrue(graph.isHamiltonCycle(path));
		}

	}

	@Test
	void testSiso() throws WcbcException, IOException {
        checkInstance("", true);
        checkInstance("a,b", false);
        checkInstance("a,b b,a", true);
        checkInstance("a,b b,c", false);
        checkInstance("a,b b,c c,a", true);
        checkInstance("a,b b,c c,a a,d", false);
        checkInstance("a,b b,c c,a a,d b,d", false);
        checkInstance("a,b b,c c,a a,d d,b", true);
	}

}
