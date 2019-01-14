package wcbc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BinAd {

	/**
	 * The only axiom for the binAd system.
	 */
	public static final String theAxiom = "1=1";

	private static final String binaryNumRegexString = "[01]+";
	private static final Pattern binaryNumRegex = Pattern.compile(binaryNumRegexString);

	private static final String wellFormedRegexString = "^[01]+(\\+[01]+)*=[01]+(\\+[01]+)*$";
	private static final Pattern wellFormedRegex = Pattern.compile(wellFormedRegexString);

	private static final String ruleTwoRegexString = "([=+]|^)([01]+)(?=\\+\\2([=+]|$))";
	private static final Pattern ruleTwoRegex = Pattern.compile(ruleTwoRegexString);
	private static final int NRuleTwoGroupID = 2;

	private static final String ruleThreeRegexString = "([=+]|^)1\\+([01]+)0([=+]|$)";
	private static final Pattern ruleThreeRegex = Pattern.compile(ruleThreeRegexString);
	private static final int NRuleThreeGroupID = 2;

	public BinAd() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Return True if f is well formed and False otherwise.
	 * 
	 * @param f
	 *            string representing a possible formula in binAd
	 * @return True if f is well formed and False otherwise
	 */
	public static boolean isWellFormed(String f) {
		Matcher m = wellFormedRegex.matcher(f);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Apply Rule 1 to the formula f.
	 * 
	 * @param f
	 *            string representing a formula in binAd
	 * @return Set of formulas that can be produced by applying Rule 1 to f. This
	 *         will be empty if f is not well formed.
	 */
	public static HashSet<String> applyRuleOne(String f) {
		HashSet<String> result = new HashSet<>();
		if (!isWellFormed(f)) {
			return result;
		}
		String[] components = f.split("=");
		String lhs = components[0];
		String rhs = components[1];
		String newF = "1+" + lhs + "=" + "1+" + rhs;
		result.add(newF);
		return result;

	}

	/**
	 * Apply Rule 2 to the formula f.
	 * 
	 * @param f
	 *            string representing a formula in binAd
	 * @return Set of formulas that can be produced by applying Rule 2 to f. This
	 *         will be empty if f is not well formed.
	 * @throws WcbcException
	 */
	public static HashSet<String> applyRuleTwo(String f) throws WcbcException {
		HashSet<String> result = new HashSet<>();
		if (!isWellFormed(f)) {
			return result;
		}
		Matcher m = ruleTwoRegex.matcher(f);

		while (m.find()) {
			String N = m.group(NRuleTwoGroupID);
			Matcher b = binaryNumRegex.matcher(N);
			if (!b.matches()) {
				throw new WcbcException("unexpected match in rule 2: " + N);
			}
			int Nstart = m.start(NRuleTwoGroupID);
			int Nend = m.end(NRuleTwoGroupID);
			int Nlen = Nend - Nstart;
			String newN = N + "0";
			int replaceLen = 2 * Nlen + 1; // 'N+N' is the string being replaced, so its length is 2*Nlen+1
			String newF = f.substring(0, Nstart) + newN + f.substring(Nstart + replaceLen, f.length());
			result.add(newF);
		}
		return result;
	}

	/**
	 * Apply Rule 3 to the formula f.
	 * 
	 * @param f
	 *            string representing a formula in binAd
	 * @return Set of formulas that can be produced by applying Rule 3 to f. This
	 *         will be empty if f is not well formed.
	 * @throws WcbcException
	 */
	public static HashSet<String> applyRuleThree(String f) throws WcbcException {
		HashSet<String> result = new HashSet<>();
		if (!isWellFormed(f)) {
			return result;
		}
		Matcher m = ruleThreeRegex.matcher(f);

		while (m.find()) {
			String N = m.group(NRuleThreeGroupID);
			Matcher b = binaryNumRegex.matcher(N);
			if (!b.matches()) {
				throw new WcbcException("unexpected match in rule 3: " + N);
			}
			int Nstart = m.start(NRuleThreeGroupID);
			int Nend = m.end(NRuleThreeGroupID);
			int Nlen = Nend - Nstart;
			String newN = N + "1";
			int replaceLen = Nlen + 3; // '1+N0' is the string being replaced, so its length is Nlen+3
			int replaceStart = Nstart - 2; // start replacing 2 chars before N, i.e., at the '1' in '1+N0'
			String newF = f.substring(0, replaceStart) + newN + f.substring(replaceStart + replaceLen, f.length());
			result.add(newF);
		}
		return result;
	}

	/**
	 * Apply Rules 1, 2, and 3 to the formula f.
	 * 
	 * @param f
	 *            string representing a formula in binAd
	 * @return Set of formulas that can be produced by applying Rule 1 or 2 or 3 to
	 *         f. This will be empty if f is not well formed.
	 * @throws WcbcException
	 */
	public static HashSet<String> applyAllRules(String f) throws WcbcException {
		HashSet<String> result = new HashSet<>();
		result.addAll(applyRuleOne(f));
		result.addAll(applyRuleTwo(f));
		result.addAll(applyRuleThree(f));
		return result;
	}

	/**
	 * Helper method for creating a sorted copy of sets of strings
	 * 
	 * @param strings
	 *            The set of strings to be sorted
	 * @return A sorted list of the given strings
	 */
	private static List<String> sortStringSet(Set<String> strings) {
		List<String> sortedStrings = new ArrayList<>(strings);
		Collections.sort(sortedStrings);
		return sortedStrings;
	}

	/**
	 * Apply Rules 1, 2, and 3 to a set of frontier formulas.
	 * 
	 * @param provedFormulas
	 *            a set of formulas that have been proved already. Formulas in this
	 *            set may or may not have yet been used to generate new formulas via
	 *            inference rules. This set will be updated, producing a set that
	 *            includes all newly proved formulas.
	 * @param frontierFormulas
	 *            set of formulas to which the inference rules have not yet been
	 *            applied. By definition, frontierFormulas is a subset of
	 *            provedFormulas and the function will throw an exception if this is
	 *            not the case.
	 * @param predecessors
	 *            dictionary in which the key is a formula f and the value is a
	 *            formula f' which was used to generate f via an inference rule.
	 *            This parameter is optional, but if it is non-null, this dictionary
	 *            will be updated.
	 * @return a new frontier consisting of only the newly proved formulas
	 * @throws WcbcException
	 *             if frontierFormulas is not a subset of provedFormulas
	 */
	public static Set<String> applyAllRulesToFrontier(Set<String> provedFormulas, Set<String> frontierFormulas,
			Map<String, String> predecessors) throws WcbcException {
		if (!provedFormulas.containsAll(frontierFormulas)) {
			throw new WcbcException("frontier formulas must be a subset of proved formulas");
		}
		Set<String> newFrontier = new HashSet<>();

		// We now iterate over a sorted copy of frontierFormulas. The sorting is not
		// needed for correctness of this method's return value, but it is needed to
		// obtain the same predecessors every time (there is otherwise ambiguity in the
		// definition).
		for (String f : sortStringSet(frontierFormulas)) {
			Set<String> newFs = applyAllRules(f);
			newFrontier.addAll(newFs);
			if (predecessors != null) {
				// We now iterate over a sorted copy of the new formulas. The sorting is not
				// needed for correctness of this method's return value, but it is needed to
				// obtain the same predecessors every time (there is otherwise ambiguity in the
				// definition).
				for (String newF : sortStringSet(newFs)) {
					if (!predecessors.containsKey(newF)) {
						predecessors.put(newF, f);
					}
				}

			}

		}

		// alreadyProved will be intersection of newFrontier and provedFormulas
		Set<String> alreadyProved = new HashSet<>(newFrontier);
		alreadyProved.retainAll(provedFormulas);

		// remove alreadyProved from newFrontier
		newFrontier.removeAll(alreadyProved);

		// add newFrontier to provedFormulas
		provedFormulas.addAll(newFrontier);

		return newFrontier;

	}

	/**
	 * See 3-parameter applyAllRulesToFrontier(). This is the 2-parameter variant.
	 */
	public static Set<String> applyAllRulesToFrontier(Set<String> provedFormulas, Set<String> frontierFormulas)
			throws WcbcException {
		return applyAllRulesToFrontier(provedFormulas, frontierFormulas, null);
	}

	/**
	 * Generate a proof of the formula f.
	 * 
	 * @param f
	 *            string representing a formula in binAd
	 * @param predecessors
	 *            map in which the key is a formula f and the value is a formula f'
	 *            which was used to generate f via an inference rule. Typically this
	 *            would have been constructed using calls to
	 *            applyAllRulesToFrontier().
	 * @return a sequence of formulas that constitute a proof of f.
	 * @throws WcbcException 
	 */
	public static List<String> getProofFromPredecessors(String f, Map<String, String> predecessors) throws WcbcException {
		List<String> proof = new ArrayList<>();
		proof.add(f);

		int maxPredecessors = 1000;
		String predecessor = predecessors.get(f);
		int count = 0;

		while (!predecessor.equals("") && count <= maxPredecessors) {
	        proof.add(predecessor);
	        predecessor = predecessors.get(predecessor);
	        count = count + 1;
		}
	    if (count > maxPredecessors) {
			throw new WcbcException("Warning: unexpected loop or long chain of predecessors");	    	
	    }

	    Collections.reverse(proof);
		return proof;
	}

	public static void main(String[] args) throws WcbcException {
		HashSet<String> s = BinAd.applyRuleThree("1+10=11");

	}

}
