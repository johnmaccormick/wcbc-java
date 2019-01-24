package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import wcbc.ConvertSatTo3Sat.ClauseTwoTuple;
import wcbc.Sat.CNFformula;
import wcbc.Sat.Clause;

class ConvertSatTo3SatTest {

	@Test
	void testAddDummyVariable() {
		String formulaStr = "(x1 OR x2 OR NOT x3 OR NOT x4 OR x5) AND (NOT x1 OR NOT x2 OR x3 OR x4) AND (x4 OR NOT x5)";
		CNFformula cnfFormula = Sat.readSat(formulaStr);
		Set<String> allVariables = cnfFormula.getVariablesAsSet();
		int numVars = allVariables.size();
		for (int i = 0; i < 5; i++) {
			String dummyName = ConvertSatTo3Sat.addDummyVariable(allVariables);
			String varName = "d" + Integer.toString(i + 1);
			assertTrue(allVariables.contains(varName));
			assertEquals(numVars + i + 1, allVariables.size());
			assertEquals(varName, dummyName);
		}
	}

	@Test
	void testSplitClause() throws WcbcException {
		String formulaStr = "(x1 OR x2 OR NOT x3 OR NOT x4 OR x5) AND (NOT x1 OR NOT x2 OR x3 OR x4) AND (x4 OR NOT x5)";
		CNFformula cnfFormula = Sat.readSat(formulaStr);
		Set<String> allVariables = cnfFormula.getVariablesAsSet();
		ClauseTwoTuple result = ConvertSatTo3Sat.splitClause(cnfFormula.clauses.get(0), allVariables);
		Clause clause1 = new Clause();
		clause1.put("x1", 1);
		clause1.put("d1", 1);
		clause1.put("x3", -1);
		clause1.put("x2", 1);
		Clause clause2 = new Clause();
		clause2.put("d1", -1);
		clause2.put("x5", 1);
		clause2.put("x4", -1);
		assertEquals(clause1, result.clause1);
		assertEquals(clause2, result.clause2);
	}

	@Test
	void testConvertSatTo3Sat() throws WcbcException, IOException {
		String s0 = "(x1 OR x2 OR NOT x3 OR NOT x4 OR x5) AND (NOT x1 OR NOT x2 OR x3 OR x4) AND (x4 OR NOT x5)";
		String s0soln = "(d1 OR d2 OR x1) AND (NOT d2 OR x2 OR NOT x3) AND (NOT d1 OR NOT x4 OR x5) AND (d3 OR NOT x1 OR NOT x2) AND (NOT d3 OR x3 OR x4) AND (x4 OR NOT x5)";
		String s1 = "";
		String s1soln = "";
		String s2 = "x1";
		String s2soln = "(x1)";
		String s3 = "x1 AND NOT x2";
		String s3soln = "(x1) AND (NOT x2)";
		String s4 = "x1 OR NOT x2";
		String s4soln = "(x1 OR NOT x2)";

		ConvertSatTo3Sat convertSatTo3Sat = new ConvertSatTo3Sat();
		
		assertEquals(s0soln, convertSatTo3Sat.siso(s0));
		assertEquals(s1soln, convertSatTo3Sat.siso(s1));
		assertEquals(s2soln, convertSatTo3Sat.siso(s2));
		assertEquals(s3soln, convertSatTo3Sat.siso(s3));
		assertEquals(s4soln, convertSatTo3Sat.siso(s4));
		
	}

}
