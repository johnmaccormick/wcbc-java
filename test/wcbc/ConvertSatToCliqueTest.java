package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ConvertSatToCliqueTest {

	private void doOneSisoTest(String satInstance) throws WcbcException, IOException {
		ConvertSatToClique convertSatToClique = new ConvertSatToClique();
		Sat sat = new Sat();
		Clique cliqueObj = new Clique();
		String cliqueInstance = convertSatToClique.siso(satInstance);
		String satSolution = sat.siso(satInstance);
		String cliqueSolution = cliqueObj.siso(cliqueInstance);
		if (satSolution.equals("no")) {
			assertEquals("no", cliqueSolution);
		} else {
			Map<String, Boolean> truthAssignment = convertSatToClique.revertSolution(cliqueSolution);
			Sat.CNFformula cnfFormula = Sat.readSat(satInstance);
			assertTrue(Sat.satisfies(cnfFormula, truthAssignment));
		}

	}

	@Test
	void testSiso() throws WcbcException, IOException {
		doOneSisoTest("");
		doOneSisoTest("x1");
		doOneSisoTest("(x1) AND (x2)");
		doOneSisoTest("(x1) AND (NOT x2) AND (NOT x1)");
		doOneSisoTest("(x1 OR x2) AND (NOT x2 OR x3 OR x1) AND (NOT x1)  AND (NOT x3 OR NOT x2)");
		doOneSisoTest("x1 OR x3 AND NOT x1 OR NOT x2 OR NOT x3");
		doOneSisoTest("x1 OR x2 AND NOT x1 OR NOT x2 AND x2");
		doOneSisoTest("x1 OR NOT x1");
	}

}
