package wcbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SISO program Sat.java
 * 
 * Solves the computational problem Sat. In the textbook, Sat is described as a
 * decision problem. This program, however, treats it as a general computational
 * problem. If there is a positive solution then a description of a solution
 * will be returned. The recursive search algorithm enumerates all truth
 * assignments and therefore requires exponential time in the worst case.
 * 
 * inString: a Boolean formula B in CNF, expressed in ASCII as described in the
 * textbook, e.g. "(x1 OR x2) AND (NOT x2 OR x3 OR x1)"
 * 
 * returns: If B is satisfiable, a satisfying truth assignment will be returned,
 * as a string consisting of the names of all true variables separated by
 * commas. If B is not satisfiable, "no" is returned. Note that the empty string
 * does represent a satisfying assignment -- it assigns all variables to false
 * (and this includes the possibility that there are no variables).
 * 
 * Example:
 * 
 * > java wcbc/Sat("x1 OR x3 AND NOT x1 OR NOT x2 OR NOT x3")
 * 
 * "x3,x1"
 * 
 * 
 */
public class Sat implements Siso {

	/**
	 * Represents a clause in a CNF formula.
	 */
	public static class Clause {
		/**
		 * Key is the name of a variable. Values are +1 for positive literals and -1 for
		 * negative literals and 0 if the literal appears both negated and unnegated.
		 */
		public Map<String, Integer> literals = new HashMap<>();

		public void put(String variable, int value) {
			literals.put(variable, value);
		}

		public Integer get(String variable) {
			return literals.get(variable);
		}

		public boolean contains(String variable) {
			return literals.containsKey(variable);
		}

		private String literalToString(String variable) {
			int posNeg = literals.get(variable);
		    if (posNeg == 1) {
		        return variable;}
		    else if (posNeg == -1) {
		        return "NOT " + variable;}
		    else if( posNeg == 0) {
		        return variable + " OR NOT " + variable;}
		    else {
				throw new RuntimeException("unexpected literal value " + posNeg);
		    }
		}

		@Override
		public String toString() {
		    // For consistency we sort the variables
		    List<String> sortedClauseVariables = new ArrayList<String>(this.literals.keySet());
		    Collections.sort(sortedClauseVariables);
		    
		    List<String> literalStrings = new ArrayList<>();
		    for(String variable:sortedClauseVariables) {
		    	literalStrings.add(literalToString(variable));
		    }
		    return "(" + utils.join(literalStrings, " OR ")  + ")";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((literals == null) ? 0 : literals.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Clause other = (Clause) obj;
			if (literals == null) {
				if (other.literals != null)
					return false;
			} else if (!literals.equals(other.literals))
				return false;
			return true;
		}
		
		
	}

	/**
	 * Represents a CNF formula, which is just a list of clauses.
	 */
	public static class CNFformula {
		public List<Clause> clauses = new ArrayList<>();

		public boolean add(Clause clause) {
			return clauses.add(clause);
		}

		public int size() {
			return clauses.size();
		}

		/**
		 * Return a set of variables in the given formula.
		 * 
		 * @return the names of all variables that appear in the CNF formula
		 */
		public Set<String> getVariablesAsSet() {
			Set<String> variables = new HashSet<>();
			for (Clause clause : this.clauses) {
				variables.addAll(clause.literals.keySet());
			}
			return variables;
		}

		/**
		 * Return a list of variables in the given formula.
		 * 
		 * @return list of the names of all variables that appear in the CNF formula
		 */
		public List<String> getVariablesAsList() {
			return new ArrayList<>(getVariablesAsSet());
		}

		@Override
		public String toString() {
		    List<String> clauseStrings = new ArrayList<>();
		    for(Clause clause:this.clauses) {
		    	clauseStrings.add(clause.toString());
		    }
		    return utils.join(clauseStrings, " AND ");
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((clauses == null) ? 0 : clauses.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CNFformula other = (CNFformula) obj;
			if (clauses == null) {
				if (other.clauses != null)
					return false;
			} else if (!clauses.equals(other.clauses))
				return false;
			return true;
		}
		
		
		
	}

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		// Convert the ASCII formula into our internal format for CNF
		// formulas. See the documentation of readSat() for details.
		CNFformula cnfFormula = readSat(inString);

		// special case of zero clauses: solution is positive, but it's the empty string
		if (cnfFormula.size() == 0) {
			return "";
		}

		// key is the name of variable and value is the truth value assigned to that
		// variable
		Map<String, Boolean> truthAssignment = new HashMap<>();

		// initialize to all variables in the formula
		List<String> remainingVariables = cnfFormula.getVariablesAsList();

		// Search for a satisfying assignment using recursion. See the
		// documentation of tryExtensions() for details.
		Map<String, Boolean> satisfyingAssignment = tryExtensions(cnfFormula, truthAssignment, remainingVariables);

		if (satisfyingAssignment != null) {
			ArrayList<String> trueVars = new ArrayList<>();
			for (String variable : satisfyingAssignment.keySet()) {
				if (satisfyingAssignment.get(variable)) {
					trueVars.add(variable);
				}
			}
			return utils.join(trueVars, ",");
		} else {
			return "no";
		}
	}

	/**
	 * Return True if the given truthAssignment satisfies the given cnfFormula.
	 * 
	 * @param cnfFormula
	 *            a CNF formula
	 * @param truthAssignment
	 *            keys are variable names whose corresponding values are True or
	 *            False. We do allow the possibility that not all variables are
	 *            assigned by the truth assignment.
	 * @return True if the given truthAssignment satisfies the given cnfFormula
	 * @throws WcbcException
	 */
	public static boolean satisfies(CNFformula cnfFormula, Map<String, Boolean> truthAssignment) throws WcbcException {
		for (Clause clause : cnfFormula.clauses) {
			boolean clauseIsSatisfied = false;
			for (String variable : clause.literals.keySet()) {
				int posOrNeg = clause.get(variable);
				if (posOrNeg == 1) {
					if (truthAssignment.containsKey(variable) && truthAssignment.get(variable) == true) {
						clauseIsSatisfied = true;
						break;
					}
				} else if (posOrNeg == -1) { // i.e., the literal is negated
					if (truthAssignment.containsKey(variable) && truthAssignment.get(variable) == false) {
						clauseIsSatisfied = true;
						break;
					}

				} else if (posOrNeg == 0) { // the literal appears both positive and negative
					if (truthAssignment.containsKey(variable)) {
						clauseIsSatisfied = true;
						break;
					}
				} else { // unexpected value
					throw new WcbcException("unexpected literal value " + posOrNeg);
				}
			}
			if (!clauseIsSatisfied) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Extend the given truth assignment to satisfy the given formula.
	 * 
	 * This is the recursive function at the heart of finding a truth assignment for
	 * a given formula. If we can satisfy the given formula by extending the given
	 * truth assignment using the given remaining variables, a satisfying truth
	 * assignment is returned. The possible assignments are explored in true-first
	 * order, starting with the first variable in remainingVariables.
	 * 
	 * @param cnfFormula
	 *            a CNF formula that we attempt to satisfy
	 * @param truthAssignment
	 *            keys are variable names and whose values are True or False. We do
	 *            allow the possibility that not all variables are assigned by the
	 *            truth assignment.
	 * @param remainingVariables
	 *            list of variables that have not yet been assigned a truth value.
	 * @return If it is possible to extend the given truthAssignment by assigning
	 *         truth values to some or all of remainingVariables and thus satisfy
	 *         cnfFormula, a satisfying truthAssignment will be returned. If there
	 *         is no such truth assignment, null is returned.
	 * @throws WcbcException
	 */
	static Map<String, Boolean> tryExtensions(CNFformula cnfFormula, Map<String, Boolean> truthAssignment,
			List<String> remainingVariables) throws WcbcException {
		if (remainingVariables.size() == 0) {
			if (satisfies(cnfFormula, truthAssignment)) {
				return truthAssignment;
			} else {
				return null;
			}
		}

		String nextVariable = remainingVariables.get(0);
		List<String> newRemainingVariables = new ArrayList<>(remainingVariables.subList(1, remainingVariables.size()));

		// There are two recursive cases to deal with. In Case 1,
		// nextVariable is True. In Case 2, nextVariable is False.

		// Case 1 (nextVariable is True)
		Map<String, Boolean> newtruthAssignmentA = new HashMap<>(truthAssignment);
		newtruthAssignmentA.put(nextVariable, true);
		Map<String, Boolean> satisfyingAssignment = tryExtensions(cnfFormula, newtruthAssignmentA,
				newRemainingVariables);
		if (satisfyingAssignment != null) {
			return satisfyingAssignment;
		} else {
			// Case 2 (nextVariable is False)
			Map<String, Boolean> newtruthAssignmentB = new HashMap<>(truthAssignment);
			newtruthAssignmentB.put(nextVariable, false);
			return tryExtensions(cnfFormula, newtruthAssignmentB, newRemainingVariables);
		}
	}

	/**
	 * Convert a string representing a CNF formula into a Java data structure
	 * representing the same formula.
	 * 
	 * @param inString
	 *            a CNF formula in the ASCII format described in the textbook
	 * @return the CNF formula as a Java object
	 */
	static CNFformula readSat(String inString) {
		String[] clauseStrings = inString.split("AND");
		// remove whitespace from clauses
		utils.trimAll(clauseStrings);

		// Here we deal with an annoying special case. Unfortunately, our
		// spec for SAT instances doesn't distinguish very nicely between
		// empty clauses (which are unsatisfiable by definition), and an
		// empty *set* of clauses (which is satisfiable by definition).
		// Let us agree that if after the above splitting and stripping,
		// clauseStrings contains a single element which is the empty
		// string, then we have an empty set of clauses. Any other result
		// is a nonempty set of clauses, one or more of which may in fact
		// be an empty clause.
		if (clauseStrings.length == 1 && clauseStrings[0].equals("")) {
			clauseStrings = new String[] {};
		}

		// Trim leading and trailing parentheses
		for (int i = 0; i < clauseStrings.length; i++) {
			clauseStrings[i] = trimParentheses(clauseStrings[i]);
		}

		CNFformula clauses = new CNFformula();
		for (String clauseString : clauseStrings) {
			Clause clause = new Clause();
			String[] literals = clauseString.split("OR");
			utils.trimAll(literals);
			for (String literal : literals) {
				if (literal.startsWith("NOT")) {
					String[] splitLiteral = utils.splitOnWhitespace(literal);
					String variableName = splitLiteral[1];
					if (!clause.contains(variableName)) {
						clause.put(variableName, -1);
					} else if (clause.get(variableName).equals(1)) {
						clause.put(variableName, 0);
					}
				} else if (literal.length() > 0) {
					String variableName = literal;
					if (!clause.contains(variableName)) {
						clause.put(variableName, 1);
					} else if (clause.get(variableName).equals(-1)) {
						clause.put(variableName, 0);
					}
				}
			}
			clauses.add(clause);
		}

		return clauses;
	}

	/**
	 * Trim leading and trailing parentheses from the given string, returning the
	 * result as a new string.
	 */
	private static String trimParentheses(String s) {
		int len = s.length();
		if (len == 0) {
			return "";
		}

		int i;
		for (i = 0; i < len; i++) {
			char c = s.charAt(i);
			if (c != '(' && c != ')') {
				break;
			}
		}
		int firstNonParen = i;

		for (i = len - 1; i >= 0; i--) {
			char c = s.charAt(i);
			if (c != '(' && c != ')') {
				break;
			}
		}
		int lastNonParen = i;

		if (lastNonParen < firstNonParen) {
			return "";
		}

		return s.substring(firstNonParen, lastNonParen + 1);
	}
	
	public static String writeSat(CNFformula cnfFormula) {
		return cnfFormula.toString();
	}

	public static void main(String[] args) throws WcbcException, IOException {
		System.out.println(trimParentheses(""));
		System.out.println(trimParentheses("("));
		System.out.println(trimParentheses(")"));
		System.out.println(trimParentheses("))()("));
		System.out.println(trimParentheses("a"));
		System.out.println(trimParentheses("(a"));
		System.out.println(trimParentheses("a)"));
		System.out.println(trimParentheses("(a)"));
		System.out.println(trimParentheses("asdf"));
		System.out.println(trimParentheses("(())asdf()("));
		System.out.println(trimParentheses("(asdf"));
		System.out.println(trimParentheses("asdf)"));
		System.out.println(trimParentheses("(asdf)"));
	}
}
