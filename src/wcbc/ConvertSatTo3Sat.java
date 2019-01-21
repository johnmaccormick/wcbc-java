package wcbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import wcbc.Sat;
import wcbc.Sat.Clause;
import wcbc.Siso;
import wcbc.WcbcException;
import wcbc.utils;

/**
 * SISO program ConvertSatTo3Sat.java
 * 
 * Convert an instance of SAT into an equivalent instance of 3-SAT.
 * 
 * inString: an instance of SAT, formatted as described in the textbook and
 * sat.java.
 * 
 * returns: an instance of 3SAT in the same string format.
 * 
 * Example:
 * 
 * > java wcbc/ConvertSatTo3Sat "(x1 OR x2 OR NOT x3 OR NOT x4)"
 * 
 * "(d1 OR x1 OR x2) AND (NOT d1 OR NOT x3 OR NOT x4)"
 * 
 */
public class ConvertSatTo3Sat implements Siso {

	public static class ClauseTwoTuple {
		public Clause clause1;
		public Clause clause2;

		public ClauseTwoTuple(Clause clause1, Clause clause2) {
			this.clause1 = clause1;
			this.clause2 = clause2;
		}
	}

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		Sat.CNFformula cnfFormula = Sat.readSat(inString);
		Set<String> allVariables = cnfFormula.getVariablesAsSet();

		// Repeatedly sweep through the clauses looking for "long" clauses
		// (i.e., clauses with more than three literals). We favor
		// simplicity and readability over efficiency here. Every time a
		// long clause is found, it is removed, split, and replaced in the
		// list by two new, shorter clauses. The new clauses are inserted
		// at the point where the previous long clause was removed. Then
		// we go back to the start of the entire list of clauses and start
		// looking for long clauses again. This ends up being quadratic or
		// worse, whereas near-linear is possible. But the approach is
		// simple to understand and the clauses remain in a logical order.
		boolean done = false;
		while (!done) {
			done = true;
			for (int clauseID = 0; clauseID < cnfFormula.size(); clauseID++) {
				Clause clause = cnfFormula.clauses.get(clauseID);
				if (clause.literals.size() > 3) {
					done = false;
					ClauseTwoTuple tuple = splitClause(clause, allVariables);
					List<Clause> newCnfClauses = new ArrayList<>(cnfFormula.clauses.subList(0, clauseID));
					newCnfClauses.add(tuple.clause1);
					newCnfClauses.add(tuple.clause2);
					newCnfClauses.addAll(cnfFormula.clauses.subList(clauseID + 1, cnfFormula.clauses.size()));
					cnfFormula.clauses = newCnfClauses;
					break;
				}
			}
		}
		return Sat.writeSat(cnfFormula);
	}

	/**
	 * Split a clause using the method described in the textbook.
	 * 
	 * @param clause
	 *            The clause that will be split
	 * @param allVariables
	 *            A set of all variables in use, so that we can choose dummy
	 *            variables that are not already in use.
	 * @return 2-tuple consisting of two clauses, which are the result of splitting
	 *         the input using the method described in the textbook.
	 * @throws WcbcException
	 */
	static ClauseTwoTuple splitClause(Clause clause, Set<String> allVariables) throws WcbcException {
		int numLiterals = clause.literals.size();
		if (numLiterals <= 3) {
			throw new WcbcException("clause is too short to split");
		}
		String dummyVariable = addDummyVariable(allVariables);

		// There is no need to sort the variables, but it will give a more
		// readable and predictable outcome, since otherwise the order of
		// variables in the dictionary will be arbitrary.
		List<String> sortedClauseVariables = new ArrayList<String>(clause.literals.keySet());
		Collections.sort(sortedClauseVariables);

		Clause newClause1 = new Clause();
		Clause newClause2 = new Clause();

		// Put the first numLiterals-2 literals into newClause1, in the
		// last two literals into newClause2.
		for (int i = 0; i < numLiterals; i++) {
			String variable = sortedClauseVariables.get(i);
			int posNeg = clause.get(variable);
			if (i < numLiterals - 2) {
				newClause1.put(variable, posNeg);
			} else {
				newClause2.put(variable, posNeg);
			}

		}
		// Add the dummy variable, positive in newClause1 and negative in newClause2
		newClause1.put(dummyVariable, +1);
		newClause2.put(dummyVariable, -1);

		return new ClauseTwoTuple(newClause1, newClause2);
	}

	/**
	 * Create, add, and return a new dummy variable name. Specifically, the set
	 * allVariables is a set of all current variable names. We find a new variable
	 * name of the form d1, d2, d3, ... which is not in the given set. The new name
	 * is added to the set, and the new name is also returned. Implemented with a
	 * simple linear time algorithm; of course we could do better than that if
	 * desired.
	 * 
	 * @param allVariables
	 *            A set of all variables in use, so that we can choose dummy
	 *            variables that are not already in use.
	 * @return the new dummy variable name
	 */
	static String addDummyVariable(Set<String> allVariables) {
		int i = 1;
		boolean done = false;
		while (!done) {
			String dummyName = "d" + Integer.toString(i);
			if (!allVariables.contains(dummyName)) {
				allVariables.add(dummyName);
				return dummyName;
			}
			i++;
		}
		// never get here.
		return null;
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		ConvertSatTo3Sat convertSatTo3Sat = new ConvertSatTo3Sat();
		String result = convertSatTo3Sat.siso(inString);
		System.out.println(result);
	}

}
