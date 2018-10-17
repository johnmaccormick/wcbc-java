package wcbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * An Nfa object models an nfa as described in the textbook.
 */
public class Nfa extends NDTuringMachine {

	/**
	 * Represents epsilon-transitions in ASCII machine descriptions
	 */
	final static String epsilonStr = "Eps";

	public Nfa(String description, String tapeStr, String name, boolean keepHistory) throws WcbcException, IOException {
		super(description, tapeStr, name, keepHistory);
		this.transformEpsilonTransitions();
	}

	public Nfa(String description, String tapeStr, String name) throws WcbcException, IOException {
		super(description, tapeStr, name);
		this.transformEpsilonTransitions();
	}

	public Nfa(String description, String tapeStr) throws WcbcException, IOException {
		super(description, tapeStr);
		this.transformEpsilonTransitions();
	}

	public Nfa(String description) throws WcbcException, IOException {
		super(description);
		this.transformEpsilonTransitions();
	}

	// Convert into an equivalent nfa with no epsilon-transitions.
	private void transformEpsilonTransitions() throws WcbcException {
		Map<String, ArrayList<Transition>> newTransitions = new HashMap<String, ArrayList<Transition>>();
		for (String sourceState : this.rootClone.getTransitionKeySet()) {
			ArrayList<Transition> transitionList = rootClone.getTransitions(sourceState);
			ArrayList<Transition> newTransitionList = new ArrayList<>();
			for (Transition t : transitionList) {
				if (t.getLabel().contains(epsilonStr)) {
					for (Transition newT : this.transformAnEpsilonTransition(t)) {
						newTransitionList.add(newT);
					}
				} else {
					newTransitionList.add(t);
				}
			}
			newTransitions.put(sourceState, newTransitionList);
		}
		this.rootClone.setTransitions(newTransitions);
	}

	/**
	 * Transform an epsilon transition into equivalent transitions without epsilon.
	 * 
	 * @param t
	 *            the epsilon-transition to be transformed
	 * 
	 * @return The returned list will contain exactly one or two transitions that
	 *         are not epsilon-transitions. Replacing the input transition t with
	 *         these transitions yields an equivalent nfa.
	 * @throws WcbcException
	 */
	private ArrayList<Transition> transformAnEpsilonTransition(Transition t) throws WcbcException {
		// ts will be the list of one or two transitions to be returned.
		ArrayList<Transition> ts = new ArrayList<>();

		// Append a transition that corresponds only to the epsilon, ignoring any other
		// symbols that may be in the label of t. Note that this transition is valid for
		// any scanned symbol (hence the TuringMachine.anySym), and does not consume a
		// tape symbol (hence the TuringMachine.stayDir).
		ts.add(new Transition(t.getSourceState(), t.getDestState(), TuringMachine.anySym, null,
				TuringMachine.strToDir(TuringMachine.stayDir)));

		// Now we delete the epsilon from the label of t. If there is anything left,
		// append a standard transition using the remaining label. This standard
		// transition does consume a tape symbol (hence the TuringMachine.rightDir).
        String newLabel = t.getLabel().replace(Nfa.epsilonStr, "");
        if (!newLabel.equals("")) {
            ts.add(new Transition(t.getSourceState(), t.getDestState(), newLabel, null,
            		TuringMachine.strToDir(TuringMachine.rightDir)));
        }
		return ts;
	}

	@Override
	protected TuringMachine createRootClone(String description, String tapeStr, String name, boolean keepHistory)
			throws WcbcException, IOException {
		return new Dfa(description, tapeStr, 0, name, true, true, keepHistory);
	}

	
}
