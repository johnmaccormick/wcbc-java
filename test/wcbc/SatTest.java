package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class SatTest {

	private void doOneReadSatTest(String inString, int numClauses, int numVars) {
		Sat.CNFformula cnfFormula = Sat.readSat(inString);
		assertEquals(numClauses, cnfFormula.size());
		assertEquals(numVars, cnfFormula.getVariablesAsSet().size());
	}

	@Test
	void testReadSat() {
		doOneReadSatTest("", 0, 0);
		doOneReadSatTest("  ", 0, 0);
		doOneReadSatTest("()", 1, 0);
		doOneReadSatTest("x1", 1, 1);
		doOneReadSatTest("x1 AND (x2)", 2, 2);
		doOneReadSatTest("x1 AND ()", 2, 1);
		doOneReadSatTest("x1 AND AND x2", 3, 2);
		doOneReadSatTest("  (  x1 )  AND x2 OR NOT   x3  ", 2, 3);
		doOneReadSatTest("(x1) AND (NOT x2) AND (NOT x1)", 3, 2);
		doOneReadSatTest("(x1 OR x2) AND (NOT x2 OR x3 OR x1) AND (NOT x1)  AND (NOT x3 OR NOT x2)", 4, 3);

	}

	private void doOneSatisfiesTest(String cnfString, Map<String, Boolean> truthAssignment, boolean solution)
			throws WcbcException {
		Sat.CNFformula cnfFormula = Sat.readSat(cnfString);
		assertEquals(solution, Sat.satisfies(cnfFormula, truthAssignment));
	}

	@Test
	void testSatisfies() throws WcbcException {
		Map<String, Boolean> truthAssignment = new HashMap<>();
		truthAssignment.put("x1", true);
		truthAssignment.put("x2", true);
		truthAssignment.put("x3", false);

		doOneSatisfiesTest("", truthAssignment, true);
		doOneSatisfiesTest("x1", truthAssignment, true);
		doOneSatisfiesTest("x1 AND (x2)", truthAssignment, true);
		doOneSatisfiesTest("(x1) AND (NOT x2) AND (NOT x1)", truthAssignment, false);
		doOneSatisfiesTest("(x1 OR x2) AND (NOT x2 OR x3 OR x1) AND (NOT x1)  AND (NOT x3 OR NOT x2)", truthAssignment,
				false);
		doOneSatisfiesTest("x1 OR x3 AND NOT x1 OR NOT x2 OR NOT x3", truthAssignment, true);
	}

	private void doOneTryExtensionsTest(Sat.CNFformula cnfFormula, Map<String, Boolean> truthAssignment,
			List<String> remainingVariables, boolean isSatisfiable) throws WcbcException {
		Map<String, Boolean> satisfyingAssignment = Sat.tryExtensions(cnfFormula, truthAssignment, remainingVariables);
		if (satisfyingAssignment != null) {
			assertTrue(isSatisfiable);
		} else {
			assertFalse(isSatisfiable);
		}
	}

	@Test
	void testTryExtensions() throws WcbcException {
		Sat.CNFformula cnfFormula = Sat.readSat("x1 OR x3 AND NOT x1 OR NOT x2 OR NOT x3");

		Map<String, Boolean> empty = new HashMap<>();

		Map<String, Boolean> f = new HashMap<>();
		f.put("x1", false);

		Map<String, Boolean> t = new HashMap<>();
		t.put("x1", true);

		Map<String, Boolean> tt = new HashMap<>();
		tt.put("x1", true);
		tt.put("x2", true);

		Map<String, Boolean> tf = new HashMap<>();
		tf.put("x1", true);
		tf.put("x2", false);

		Map<String, Boolean> ff = new HashMap<>();
		ff.put("x1", false);
		ff.put("x2", false);

		Map<String, Boolean> tft = new HashMap<>();
		tft.put("x1", true);
		tft.put("x2", false);
		tft.put("x3", true);

		Map<String, Boolean> ftf = new HashMap<>();
		ftf.put("x1", false);
		ftf.put("x2", true);
		ftf.put("x3", false);

		Map<String, Boolean> tff = new HashMap<>();
		tff.put("x1", true);
		tff.put("x2", false);
		tff.put("x3", false);

		ArrayList<String> emptyList = new ArrayList<>();
		ArrayList<String> x1x2x3 = new ArrayList<>(Arrays.asList("x1", "x2", "x3"));
		ArrayList<String> x2x3 = new ArrayList<>(Arrays.asList("x2", "x3"));
		ArrayList<String> x3 = new ArrayList<>(Arrays.asList("x3"));
		ArrayList<String> x2 = new ArrayList<>(Arrays.asList("x2"));

		doOneTryExtensionsTest(cnfFormula, empty, x1x2x3, true);
		doOneTryExtensionsTest(cnfFormula, f, x2x3, true);
		doOneTryExtensionsTest(cnfFormula, t, x2x3, true);
		doOneTryExtensionsTest(cnfFormula, tt, x3, true);
		doOneTryExtensionsTest(cnfFormula, tf, x2, true);
		doOneTryExtensionsTest(cnfFormula, ff, x2, false);
		doOneTryExtensionsTest(cnfFormula, tft, emptyList, true);
		doOneTryExtensionsTest(cnfFormula, ftf, emptyList, false);
		doOneTryExtensionsTest(cnfFormula, tff, emptyList, true);
	}

	private void doOneWriteSatTest(String cnfString) {
		String cnfString1 = cnfString;
		Sat.CNFformula cnfFormula1 = Sat.readSat(cnfString1);
		String cnfString2 = Sat.writeSat(cnfFormula1);
		Sat.CNFformula cnfFormula2 = Sat.readSat(cnfString2);
		assertEquals(cnfFormula1, cnfFormula2);
	}

	@Test
	void testWriteSat() {
		doOneWriteSatTest("");
		doOneWriteSatTest("   ");
		doOneWriteSatTest("AND");
		doOneWriteSatTest("x1 AND");
		doOneWriteSatTest("()");
		doOneWriteSatTest("x1");
		doOneWriteSatTest("x1 AND (x2)");
		doOneWriteSatTest("(x1) AND (NOT x2) AND (NOT x1)");
		doOneWriteSatTest("(x1 OR x2) AND (NOT x2 OR x3 OR x1) AND (NOT x1)  AND (NOT x3 OR NOT x2)");
		doOneWriteSatTest("x1 OR x3 AND NOT x1 OR NOT x2 OR NOT x3");
		doOneWriteSatTest("x1 OR NOT x1 OR x2 AND x3 OR NOT x3 OR NOT x3");
	}
}
