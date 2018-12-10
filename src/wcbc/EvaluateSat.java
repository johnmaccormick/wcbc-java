package wcbc;

import java.io.IOException;

/**
 * SISO program EvaluateSat.java
 * 
 * Determine whether a particular Boolean formula is satisfied by a given truth
 * assignment. The program does not work as written below. The string "dummy"
 * needs to be replaced with a real Boolean formula before it can function.
 * 
 * inString: a string consisting only of 1's and 0's, in which the ith character
 * is the truth value of variable x_i. Example: "101" means x_1 and x_3 are true
 * but x_2 is false.
 * 
 * returns: "yes" if the particular Boolean formula pasted in to replace "dummy"
 * is true with the given truth assignment, and "no" otherwise.
 * 
 * Note: See evaluateSatNoDummy.java for an example of how this program can be
 * altered to work correctly for a particular Boolean formula.
 * 
 * Example:
 * 
 * [assume the local variable booleanFormula below contains a formula that is
 * true when its two inputs are true]
 * 
 * > java wcbc/EvaluateSat "11"
 * 
 * "yes"
 * 
 * 
 * 
 */
public class EvaluateSat implements Siso {

	/**
	 * Return a new string with leading open-parens and trailing close-parens
	 * removed.
	 */
	private String removeParentheses(String s) {
		int start = 0;
		while (start < s.length() && s.charAt(start) == '(') {
			start++;
		}
		int end = s.length();
		while (end > 0 && s.charAt(end - 1) == ')') {
			end--;
		}
		return s.substring(start, end);
	}

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		// replace "dummy" with the desired formula, such as the one below.
		// String booleanFormula = "dummy";
		String booleanFormula = "(x1 OR NOT x2) AND (NOT x1 OR x2) AND (x1 OR NOT x1)";

		String[] clauses = booleanFormula.split("AND");

		// remove whitespace from clauses
		utils.trimAll(clauses);

		// remove parentheses from clauses
		for (int i = 0; i < clauses.length; i++) {
			clauses[i] = removeParentheses(clauses[i]);
		}

		for (String clause : clauses) {
			String[] literals = clause.split("OR");
			// remove whitespace
			utils.trimAll(literals);

			boolean clauseIsSatisfied = false;
			for (String literal : literals) {
				boolean negated;
				String variableName;
				if (literal.startsWith("NOT")) {
					negated = true;
					String[] splitLiteral = utils.splitOnWhitespace(literal);
					variableName = splitLiteral[1];

				} else {
					negated = false;
					variableName = literal;
				}
				// ignore the initial "x" in the variable name
				int variableID = Integer.parseInt(variableName.substring(1));
				char binaryInput = inString.charAt(variableID - 1); // variable IDs start at 1 not 0
				if ((binaryInput == '1' && !negated) || (binaryInput == '0' && negated)) {
					clauseIsSatisfied = true;
					// this clause is satisfied, no need to check remaining literals
					break;
				}
			}
			if (!clauseIsSatisfied) {
				// this clause is unsatisfied, so no need to check remaining clauses
				return "no";
			}
		}
		// all clauses were satisfied
		return "yes";
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		EvaluateSat e = new EvaluateSat();
		String result = e.siso(inString);
		System.out.println(result);
	}

}
