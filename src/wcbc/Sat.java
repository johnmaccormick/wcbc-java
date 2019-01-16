package wcbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public class Clause {
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
	}

	/**
	 * Represents a CNF formula, which is just a list of clauses.
	 */
	public class CNFformula {
		public List<Clause> clauses = new ArrayList<>();
	}

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		// Convert the ASCII formula into our internal format for CNF
		// formulas. See the documentation of readSat() for details.
		CNFformula cnfFormula = readSat(inString);

		return null;
	}

	/**
	 * Convert a string representing a CNF formula into a Java data structure
	 * representing the same formula.
	 * 
	 * @param inString
	 *            a CNF formula in the ASCII format described in the textbook
	 * @return the CNF formula as a Java object
	 */
	private CNFformula readSat(String inString) {
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

		return null;
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
