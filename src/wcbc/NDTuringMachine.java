package wcbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * An NDTuringMachine object models a nondeterministic Turing machine as
 * described in the textbook.
 */
public class NDTuringMachine {

	/**
	 * The maximum number of clones permitted in a single NDTuringMachine object.
	 * 
	 * We impose this limit to prevent too many resources being consumed. A true,
	 * abstract nondeterministic Turing machine should of course be permitted as
	 * many clones as desired.
	 */
	final static int maxClones = 1000;

	final static String exceededMaxClonesMsg = "Exceeded maximum permitted clones";

	/**
	 * The maximum number of computational steps permitted in a computation.
	 * 
	 * We impose this limit to prevent too many resources being consumed. A true,
	 * abstract nondeterministic Turing machine should of course be permitted as
	 * many steps as desired.
	 */
	final static int maxSteps = 100000;

	/**
	 * Edit this to true to see extensive debugging information at each step in the
	 * computation.
	 */
	final static boolean verbose = false;

	/**
	 * The root clone of this nondeterministic Turing machine, which is itself a
	 * (deterministic) Turing machine.
	 */
	protected TuringMachine rootClone;

	/**
	 * the set of clones that comprise this nondeterministic Turing machine
	 */
	protected Set<TuringMachine> clones;

	/**
	 * The number of computational steps taken so far in the computation.
	 */
	protected int steps;

	/**
	 * Each clone is assigned an ID number sequentially, beginning with zero for the
	 * root clone. This stores the ID that will be assigned to the next clone
	 * created.
	 */
	protected int nextCloneID;

	/**
	 * The name of the machine can be used for debugging and meaningful output.
	 */
	protected String name;

	/**
	 * If True, keep a complete record of the history of this machine's computation.
	 * This is useful for certain experiments, but costly in terms of storage.
	 */
	protected boolean keepHistory;

	/**
	 * mostly for debugging, remember which clone accepted (if any)
	 */
	protected TuringMachine acceptingClone;

	/**
	 * @param description
	 *            A string describing the states and transitions of the
	 *            nondeterministic Turing machine, according to the specification
	 *            described in the textbook.
	 * @param tapeStr
	 *            The initial content of the Turing machine's tape.
	 * @param name
	 *            A string that gives a meaningful name to the machine, e.g. "binary
	 *            incrementer". This can be used for debugging and meaningful
	 *            output.
	 * @param keepHistory
	 *            If true, keep a complete record of the history of this machine's
	 *            computation. This is useful for certain experiments, but costly in
	 *            terms of storage.
	 * @throws WcbcException
	 * @throws IOException
	 */
	public NDTuringMachine(String description, String tapeStr, String name, boolean keepHistory)
			throws WcbcException, IOException {

		rootClone = createRootClone(description, tapeStr, "root", keepHistory);

		if (rootClone.getBlocksLen() > 0) {
			String msg = "Sorry, blocks aren't supported for nondeterministic machines";
			throw new WcbcException(msg);
		}

		this.initClones();
		this.steps = 0;
		this.nextCloneID = 1;
		this.name = name;
		this.keepHistory = keepHistory;
		this.acceptingClone = null;
	}

	public NDTuringMachine(String description, String tapeStr, String name) throws WcbcException, IOException {
		this(description, tapeStr, name, false);
	}

	public NDTuringMachine(String description, String tapeStr) throws WcbcException, IOException {
		this(description, tapeStr, null);
	}

	public NDTuringMachine(String description) throws WcbcException, IOException {
		this(description, "");
	}

	/**
	 * Create the root clone of this nondeterministic Turing machine.
	 * 
	 * This is factored out as a method primarily so that derived classes can
	 * override it. The parameters are as described in constructor documentation.
	 * 
	 * @return The root clone of this nondeterministic Turing machine.
	 * @throws WcbcException
	 * @throws IOException
	 */
	protected TuringMachine createRootClone(String description, String tapeStr, String name, boolean keepHistory)
			throws WcbcException, IOException {
		return new TuringMachine(description, tapeStr, 0, name, true, true, keepHistory);

	}

	private void initClones() {
		this.clones = new HashSet<TuringMachine>();
		clones.add(rootClone);
	}

	/**
	 * Perform one computational step in all clones in this nondeterministic Turing
	 * machine.
	 * 
	 * @throws WcbcException
	 */
	protected void step() throws WcbcException {
		this.steps += 1;
		Set<TuringMachine> victims = new HashSet<>();
		Set<TuringMachine> children = new HashSet<>();
		for (TuringMachine tm : clones) {
			ArrayList<Transition> ts = tm.getValidTransitions();
			if (ts.size() == 0) {
				victims.add(tm);
			} else {
				TuringMachine child = null;
				for (int i = 1; i < ts.size(); i++) {
					try {
						child = (TuringMachine) tm.clone();
					} catch (CloneNotSupportedException e) {
						throw new WcbcException(e.getMessage());
					}
					child.setName("clone" + this.nextCloneID);
					this.nextCloneID += 1;
					child.applyTransition(ts.get(i));
					children.add(child);
				}
				tm.applyTransition(ts.get(0));
			}
		}
		this.clones.removeAll(victims);
		this.clones.addAll(children);
	}

	/**
	 * Run the nondeterministic Turing machine until it halts.
	 * 
	 * For practical reasons, the machine will also stop once it exceeds its maximum
	 * number of steps.
	 * 
	 * @throws WcbcException
	 */
	public String run() throws WcbcException {
		boolean allReject = true;
		String retVal = null;
		while (true) {
			if (verbose) {
				System.out.println(this.toString() + "\n");
			}
			this.step();
			allReject = true;
			retVal = null;
			for (TuringMachine tm : clones) {
				if (!tm.getState().equals(TuringMachine.rejectState)) {
					allReject = false;
				}
				if (tm.getState().equals(TuringMachine.acceptState)) {
					retVal = "yes";
				} else if (tm.getState().equals(TuringMachine.haltState)) {
					retVal = tm.getTape();
				}
				// Mostly for debugging, remember which clone accepted
				// (if any). Also, we break so that the first clone to
				// accept is the one whose return value is used.
				if (tm.getState().equals(TuringMachine.acceptState) || tm.getState().equals(TuringMachine.haltState)) {
					acceptingClone = tm;
					break;
				}
			}
			if (allReject) {
				retVal = "no";
			}
			if (retVal != null) {
				return retVal;
			}
			if (steps >= maxSteps) {
				rootClone.raiseExceededMaxSteps();
			}
			if (clones.size() > maxClones) {
				throw new WcbcException(exceededMaxClonesMsg);
			}
		}
	}

	public void reset(String tapeStr) {
		reset(tapeStr, TuringMachine.startState);
	}

	public void reset(String tapeStr, String state) {
		reset(tapeStr, state, 0);
	}

	public void reset(String tapeStr, String state, int headPos) {
		reset(tapeStr, state, headPos, 0);
	}

	public void reset(String tapeStr, String state, int headPos, int steps) {
		reset(tapeStr, state, headPos, steps, true);
	}

	/**
	 * Reset the Turing machine.
	 * 
	 * This is typically used to run a fresh computation on the machine, but
	 * optional parameters allow the machine to be set up in more specific
	 * configurations.
	 * 
	 * 
	 * @param tapeStr
	 *            The initial content of the Turing machine's tape.
	 * @param state
	 *            The state in which the Turing machine should begin computing. By
	 *            default, this will be the Turing machine's predefined initial
	 *            state, but it can be something else.
	 * @param headPos
	 *            The initial location of the tape head, which defaults to zero.
	 * @param steps
	 *            The number of computational steps this machine has already
	 *            performed. Usually this would be zero, but for certain experiments
	 *            we want to reset the machine as if it has already done a certain
	 *            amount of work.
	 * @param resetHistory
	 *            If True, delete any history of previous computations.
	 */
	public void reset(String tapeStr, String state, int headPos, int steps, boolean resetHistory) {
		rootClone.reset(tapeStr, state, headPos, steps, resetHistory);
		initClones();
		this.steps = steps;
	}

	/**
	 * Print the transitions of this machine
	 */
	public void printTransitions() {
		this.rootClone.printTransitions();
	}

	/**
	 * Return a list of all valid transitions from the current configuration.
	 * 
	 * This is a list of all transitions from the current state whose label matches
	 * the current scanned symbol.
	 * 
	 * @return A list containing all valid transitions from the current
	 *         configuration. This could be an empty list.
	 */
	public ArrayList<Transition> getTransitions(String state) {
		return this.rootClone.getTransitions(state);
	}

	@Override
	public String toString() {
		final int maxClonesToPrint = 10;
		int clonesToPrint = Math.min(maxClonesToPrint, clones.size());
		ArrayList<String> tm_strings = new ArrayList<>();
		int i = 0;
		for (TuringMachine tm : clones) {
			tm_strings.add(tm.toString());
			i++;
			if (i >= clonesToPrint) {
				break;
			}
		}
		if (clones.size() > maxClonesToPrint) {
			tm_strings.add("... [and other clones]");
		}
		String all_tm_strings = utils.join(tm_strings, "\n");

		return String.format("steps: %d num clones: %d\n%s", this.steps, clones.size(), all_tm_strings);

	}
	
	/**
	 * Convert the current Turing machine into description format.
	 * 
	 * @return description format of the current machine, suitable for storing in a
	 *         .tm file.
	 * @throws WcbcException
	 */
	public String write() throws WcbcException {
		return rootClone.write();
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// The following Python methods were not translated into Java. Hopefully we
	// won't need them:
	//
	// labelMatchesSymbol(self, symbol, label)
	///////////////////////////////////////////////////////////////////////////////

}
