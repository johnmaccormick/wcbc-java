package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class CliqueTest {
	Clique cliqueObj = new Clique();

	private void doOneExtendsCliqueTest(Graph G, String cliqueStr, String newNode, boolean solution)
			throws WcbcException {
		String[] cliqueNodes = utils.splitCheckEmpty(cliqueStr, ",");
		Set<String> clique = new HashSet<String>(Arrays.asList(cliqueNodes));
		boolean val = cliqueObj.extendsClique(G, clique, newNode);
		assertEquals(solution, val);
	}

	@Test
	void testExtendsClique() throws WcbcException {
		Graph G = new Graph("a,b a,c a,d b,c b,d c,d c,e b,e d,e", false, false);
		doOneExtendsCliqueTest(G, "", "a", true);
		doOneExtendsCliqueTest(G, "a", "b", true);
		doOneExtendsCliqueTest(G, "a", "c", true);
		doOneExtendsCliqueTest(G, "a,b", "c", true);
		doOneExtendsCliqueTest(G, "a,b", "d", true);
		doOneExtendsCliqueTest(G, "a,b,c", "d", true);
		doOneExtendsCliqueTest(G, "a", "e", false);
		doOneExtendsCliqueTest(G, "a,b", "e", false);
		doOneExtendsCliqueTest(G, "a,b,c", "e", false);
	}

	private void doOneTryExtendCliqueTest(Graph G, int K, String cliqueStr, String remainingNodesStr, String solution)
			throws WcbcException {
		String[] cliqueNodes = utils.splitCheckEmpty(cliqueStr, ",");
		Set<String> clique = new HashSet<String>(Arrays.asList(cliqueNodes));
		String[] remainingNodesArr = utils.splitCheckEmpty(remainingNodesStr, ",");
		Set<String> remainingNodes = new HashSet<String>(Arrays.asList(remainingNodesArr));

		Set<String> val = cliqueObj.tryExtendClique(G, K, clique, remainingNodes);
		if (solution == null) {
			assertEquals(solution, val);
		} else {
			// need to verify the solution
			assertTrue(G.isClique(val));
			assertEquals(K, val.size());
		}

	}

	@Test
	void testTryExtendClique() throws WcbcException {
		String graphString = "a,b a,c a,d b,c b,d c,d c,e b,e d,e";
		Graph G = new Graph(graphString, false, false);
		doOneTryExtendCliqueTest(G, 1, "", "a,b,c,d,e", "verify");
		doOneTryExtendCliqueTest(G, 2, "", "a,b,c,d,e", "verify");
		doOneTryExtendCliqueTest(G, 3, "", "a,b,c,d,e", "verify");
		doOneTryExtendCliqueTest(G, 4, "", "a,b,c,d,e", "verify");
		doOneTryExtendCliqueTest(G, 5, "", "a,b,c,d,e", null);
		doOneTryExtendCliqueTest(G, 4, "c,d,e", "a,b", "verify");
		doOneTryExtendCliqueTest(G, 5, "c,d,e", "a,b", null);
		doOneTryExtendCliqueTest(G, 4, "a,c", "b,d,e", "verify");
		doOneTryExtendCliqueTest(G, 5, "a,c", "b,d,e", null);
	}

	private void doOneCliqueTest(String graphString, Graph G, int K, String solution)
			throws WcbcException, IOException {
		String inString = graphString + " ; " + Integer.toString(K);
		String val = cliqueObj.siso(inString);
		if (solution == null) {
			assertEquals("no", val);
		} else {
			// need to verify the solution
			String[] cliqueNodes = utils.splitCheckEmpty(val, ",");
			Set<String> clique = new HashSet<String>(Arrays.asList(cliqueNodes));
			assertTrue(G.isClique(clique));
			assertEquals(K, clique.size());
		}

	}

	@Test
	void testClique() throws WcbcException, IOException {
		String graphString = "a,b a,c a,d b,c b,d c,d c,e b,e d,e";
		Graph G = new Graph(graphString, false, false);
		doOneCliqueTest(graphString, G, 1, "verify");
		doOneCliqueTest(graphString, G, 2, "verify");
		doOneCliqueTest(graphString, G, 3, "verify");
		doOneCliqueTest(graphString, G, 4, "verify");
		doOneCliqueTest(graphString, G, 5, null);
	}

}
