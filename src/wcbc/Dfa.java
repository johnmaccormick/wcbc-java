package wcbc;

import java.io.IOException;

import wcbc.TuringMachine.Direction;

/**
 * A Dfa object models a dfa as described in the textbook.
 */
public class Dfa extends TuringMachine {

	public Dfa(String description, String tapeStr, int depth, String name, boolean allowImplicitReject,
			boolean allowLeftFromCell0, boolean keepHistory) throws WcbcException, IOException {
		super(description, tapeStr, depth, name, allowImplicitReject, allowLeftFromCell0, keepHistory);
	}

	public Dfa(String description, String tapeStr, int depth, String name) throws WcbcException, IOException {
		super(description, tapeStr, depth, name);
	}

	public Dfa(String description, String tapeStr) throws WcbcException, IOException {
		super(description, tapeStr);
	}

	public Dfa(String description) throws WcbcException, IOException {
		super(description);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.TuringMachine#extractTransition(java.lang.String)
	 * 
	 * We need to override because the format is slightly different for dfas,
	 * compared to Turing machines.
	 */
	@Override
	protected Transition extractTransition(String line) throws WcbcException {
		String[] components = splitTransition(line);
		String label = components[0];
		String sourceState = components[1];
		String destState = components[2];

		return new Transition(sourceState, destState, label);		
	}

	/* (non-Javadoc)
	 * @see wcbc.TuringMachine#splitTransition(java.lang.String)
	 * 
	 * Given a line in a Turing machine description, split into transition
	 * components.
	 * 
	 * @param line
	 *            a line in a Turing machine description
	 * @return 3-tuple of Strings (label, sourceState, destState): where
	 *         label, sourceState, destState are attributes as described in the
	 *         documentation for the Transition class.
	 * 
	 */
	@Override
	protected String[] splitTransition(String line) {
		String[] components = line.split(TuringMachine.labelSeparator);
		utils.trimAll(components); // yields (states, label)
		String states = components[0];
		String label = components[1];

		// Split into source and destination state
		String[] stateComponents = states.split(TuringMachine.stateSeparator);
		utils.trimAll(stateComponents);
		String sourceState = stateComponents[0];
		String destState = stateComponents[1];

		return new String[] { label, sourceState, destState };
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		
		
		Dfa newDfa = null;
		try {
			newDfa = new Dfa(null, utils.joinChars(this.tape), this.depth, this.name);
		} catch (WcbcException e) {
			throw new CloneNotSupportedException(e.getMessage());
		} catch (IOException e) {
			throw new CloneNotSupportedException(e.getMessage());
		}
		
		this.copyTMState(newDfa);
		return newDfa;
	}

	@Override
	protected String writeTransition(Transition t) throws WcbcException {
		// TODO Auto-generated method stub
		String s = super.writeTransition(t);
		int separatorLocation = s.lastIndexOf(TuringMachine.writeSymSeparator);
		// epsilon transitions must be treated separately
		if (t.getLabel().equals(TuringMachine.anySym) && dirToStr(t.getDirection()).equals(TuringMachine.stayDir)) {
			// it's an epsilon transition -- return whole string
			return s;
		} else {
			// dfa writes only the label
			return s.substring(0, separatorLocation);
		}
	}
	
	
	public static void main(String[] args) throws IOException, WcbcException {
		String description = utils.rf(utils.prependWcbcPath("containsGAGA.dfa"));
		String tapeStr = "AAAAAAGAGATTT";
		Dfa dfa = new Dfa(description, tapeStr);
		String result = dfa.run();
		System.out.println(result);
	}
	

}
