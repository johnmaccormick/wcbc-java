package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class UhcTest {

	private void checkInstance(String graphStr, boolean hasUhc) throws WcbcException, IOException {
		Graph graph = new Graph(graphStr, false, false);
		String result = new Uhc().siso(graphStr);

		if (!hasUhc) {
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
        checkInstance("a,b b,c", false);
        checkInstance("a,b b,c c,a", true);
        checkInstance("a,b b,c c,a a,d", false);
        checkInstance("a,b b,c c,a a,d b,d", true);
        checkInstance("aB,aA aB,aC aA,aC", true);
	}
}
