package wcbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TuringMachine {

	public enum Direction {
		LEFT, RIGHT, STAY
	};

	public static boolean verbose = false;
	public static final String blockMarker = "block:";
	public static final int maxSteps = 100000;
	public static final int maxDepth = 1000;
	public static final String exceededMaxStepsMsg = "exceeded maxSteps";
	public static final String rightDir = "R";
	public static final String leftDir = "L";
	public static final String stayDir = "S";
	public static final String noStr = "no";
	public static final String yesStr = "yes";

	/**
	 * An underscore character represents blanks in this model of a Turing machine.
	 * Technically, this means the Turing machines models here are not the same as
	 * the ones in the textbook, because the textbook Turing machines employ the
	 * ASCII alphabet, and the underscore character is an element of that alphabet.
	 * We prefer to accept that inconsistency rather than using a more complex
	 * method for modeling blanks.
	 */
	public static final String blank = "_";

	// As with the blank symbol above, the following characters have
	// special meaning in our Turing machine representation, and are
	// therefore not permitted as part of the alphabet. Whitespace
	// characters are also excluded from the alphabet.

	public static final String anySym = "~"; // Stands for any one symbol
	public static final String notSym = "!"; // Stands for any symbol not in the immediately following sequence
	public static final String commentStart = "#";
	public static final String actionSeparator = ",";
	public static final String blockSeparator = "=";
	public static final String stateSeparator = "->";
	public static final String labelSeparator = ":";
	public static final String writeSymSeparator = ";";

	public static final String acceptState = "qA";
	public static final String rejectState = "qR";
	public static final String haltState = "qH";
	public static final String startState = "q0";

	public static final Character[] validSymbolsArray = { '$', '"', '%', '&', '(', ')', '*', '+', '-', '.', '/', '0',
			'1', '2', '3', '4', '5', '6', '7', '8', '9', '<', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']',
			'^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', };

	public static final Set<Character> validSymbols = new HashSet<Character>(Arrays.asList(validSymbolsArray));

	/**
	 * "transitions" is a dictionary whose key is state, and value is list of
	 * transitions leaving that state. Each transition is stored as a Transition
	 * object.
	 * 
	 */
	private Map<String, ArrayList<Transition>> transitions = null;

	/**
	 * This Turing machine could be a "root" machine (i.e., it's not being used as a
	 * building block in some parent machine), in which case its depth is zero.
	 * Otherwise we store the the depth of this building block relative to the root
	 * building block.
	 * 
	 */
	public final int depth;

	/**
	 * The name of the machine can be used for debugging and meaningful output.
	 */
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * If we encounter a state for which there is no explicitly listed transition,
	 * we can implicitly assume this is a transition to the reject state. According
	 * to the textbook definition, allowImplicitReject should be True. But sometimes
	 * it is convenient to set it to False for debugging purposes, since it will
	 * prevent us unintentionally rejecting certain inputs.
	 */
	public final boolean allowImplicitReject;

	/**
	 * If we are already at cell 0 and we are commanded to move left, ignore the
	 * command and remain at cell 0. If allowLeftFromCell0 is False, throw an
	 * exception when commanded to move left from cell 0. According to the textbook
	 * definition, allowLeftFromCell0 should be True. But sometimes it is convenient
	 * to set it to False for debugging purposes.
	 * 
	 */
	public final boolean allowLeftFromCell0;

	/**
	 * Dictionary of building blocks used in this machine. Key is the state from
	 * which we jump to the building block, value is the building block itself.
	 * 
	 */
	private Map<String, TuringMachine> blocks = new HashMap<String, TuringMachine>();

	/**
	 * 
	 * @return
	 */
	public int getBlocksLen() {
		return blocks.size();
	}	
	
	/**
	 * If True, keep a complete record of the history of this machine's computation.
	 * This is useful for certain experiments, but costly in terms of storage.
	 */
	public boolean keepHistory;



	/**
	 * list of strings giving config at each step
	 * 
	 */
	private List<String> history = new ArrayList<>();

	/**
	 * The content of the Turing machine's tape
	 */
	protected ArrayList<Character> tape = new ArrayList<>();

	public String getTape() {
		return utils.joinChars(tape);
	}	
	
	/**
	 * The position of the Turing machine's read/write head.
	 */
	private int headPos;

	/**
	 * Current state of the Turing machine.
	 */
	private String state = null;

	public String getState() {
		return state;
	}

	// todo
	private int steps = 0;

	// todo
	private boolean halted = false;

	/**
	 * @param description
	 *            A string describing the states and transitions of the Turing
	 *            machine, according to the specification described in the textbook.
	 *            For examples, see the files containsGAGA.tm and
	 *            binaryIncrementer.tm.
	 * @param tapeStr
	 *            The initial content of the Turing machine's tape.
	 * @param depth
	 *            This Turing machine could be a "root" machine (i.e., it's not
	 *            being used as a building block in some parent machine), in which
	 *            case its depth is zero. Otherwise we store the the depth of this
	 *            building block relative to the root building block.
	 * @param name
	 *            A string that gives a meaningful name to the machine, e.g. "binary
	 *            incrementer". This can be used for debugging and meaningful
	 *            output.
	 * @param allowImplicitReject
	 *            If we encounter a state for which there is no explicitly listed
	 *            transition, we can implicitly assume this is a transition to the
	 *            reject state. According to the textbook definition,
	 *            allowImplicitReject should be True. But sometimes it is convenient
	 *            to set it to False for debugging purposes, since it will prevent
	 *            us unintentionally rejecting certain inputs.
	 * @param allowLeftFromCell0
	 *            If we are already at cell 0 and we are commanded to move left,
	 *            ignore the command and remain at cell 0. If allowLeftFromCell0 is
	 *            False, throw an exception when commanded to move left from cell 0.
	 *            According to the textbook definition, allowLeftFromCell0 should be
	 *            True. But sometimes it is convenient to set it to False for
	 *            debugging purposes.
	 * @param keepHistory
	 *            If True, keep a complete record of the history of this machine's
	 *            computation. This is useful for certain experiments, but costly in
	 *            terms of storage.
	 * @throws WcbcException
	 * @throws IOException
	 */
	public TuringMachine(String description, String tapeStr, int depth, String name, boolean allowImplicitReject,
			boolean allowLeftFromCell0, boolean keepHistory) throws WcbcException, IOException {
		this.depth = depth;
		this.name = name;
		this.allowImplicitReject = allowImplicitReject;
		this.allowLeftFromCell0 = allowLeftFromCell0;
		this.keepHistory = keepHistory;

		reset(tapeStr, null, 0, 0, true);
		if (description != null) {
			read(description);
		}
		checkAllSymbolsValid();
	}

	public TuringMachine(String description, String tapeStr, int depth, String name) throws WcbcException, IOException {
		this(description, tapeStr, depth, name, true, true, false);
	}

	public TuringMachine(String description, String tapeStr) throws WcbcException, IOException {
		this(description, tapeStr, 0, null);
	}

	public TuringMachine(String description) throws WcbcException, IOException {
		this(description, "");
	}

	public void startKeepingHistory() {
		if (this.keepHistory) {
			return;
		} else {
			this.keepHistory = true;
			this.initHistory();
		}

	}

	/**
	 * Convert a string representing a direction into the corresponding Direction
	 * enum
	 * 
	 * @param s
	 *            string representing one of left, right, stay
	 * @return Direction enum representing one of left, right, stay
	 * @throws WcbcException
	 */
	protected Direction strToDir(String s) throws WcbcException {
		if (s.equals(leftDir)) {
			return Direction.LEFT;
		} else if (s.equals(rightDir)) {
			return Direction.RIGHT;
		} else if (s.equals(stayDir)) {
			return Direction.STAY;
		} else {
			throw new WcbcException("Unexpected direction string " + s);
		}
	}

	/**
	 * Convert a Direction enum into the corresponding string representing a
	 * direction
	 * 
	 * @param d
	 *            Direction enum representing one of left, right, stay
	 * @return string representing one of left, right, stay
	 * @throws WcbcException
	 */
	protected String dirToStr(Direction d) throws WcbcException {
		if (d == Direction.LEFT) {
			return leftDir;
		} else if (d == Direction.RIGHT) {
			return rightDir;
		} else if (d == Direction.STAY) {
			return stayDir;
		} else {
			throw new WcbcException("Unexpected direction " + d);
		}
	}

	/**
	 * Given a line in a Turing machine description, split into transition
	 * components.
	 * 
	 * @param line
	 *            a line in a Turing machine description
	 * @return 4-tuple of Strings (label, actions, sourceState, destState): where
	 *         label, sourceState, destState are attributes as described in the
	 *         documentation for the Transition class, and actions is a string
	 *         containing the write symbol, if any, and the direction.
	 */
	protected String[] splitTransition(String line) {
		// Define a character set consisting of the label separator and
		// the write symbol separator.
		String splitRegex = "[" + TuringMachine.labelSeparator + TuringMachine.writeSymSeparator + "]";
		// Split on the above two separators
		String[] components = line.split(splitRegex);
		utils.trimAll(components); // yields (states, label, actions)
		String states = components[0];
		String label = components[1];
		String actions = components[2];

		// Split into source and destination state
		String[] stateComponents = states.split(TuringMachine.stateSeparator);
		utils.trimAll(stateComponents);
		String sourceState = stateComponents[0];
		String destState = stateComponents[1];

		return new String[] { label, actions, sourceState, destState };
	}

	/**
	 * Given a line in a Turing machine description, return a new Transition object
	 * described by that line.
	 * 
	 * @param line
	 *            a line in a Turing machine description
	 * @return a new Transition object
	 * @throws WcbcException
	 */
	protected Transition extractTransition(String line) throws WcbcException {
		String[] components = splitTransition(line);
		String label = components[0];
		String actions = components[1];
		String sourceState = components[2];
		String destState = components[3];

		String writeSymbol = null;
		String dirString = actions;
		if (actions.contains(TuringMachine.actionSeparator)) {
			String[] actionComponents = actions.split(TuringMachine.actionSeparator);
			utils.trimAll(actionComponents);
			writeSymbol = actionComponents[0];
			dirString = actionComponents[1];
		}
		Direction dir = strToDir(dirString);
		return new Transition(sourceState, destState, label, writeSymbol, dir);
	}

	/**
	 * Strip comments from a Turing machine description.
	 * 
	 * A comment is anything after a "#" chaacter on a given line.
	 * 
	 * @param lines
	 *            list of lines in the Turing machine description
	 * @return The same list of Turing machine description lines with comments
	 *         removed.
	 */
	private String[] stripComments(String[] lines) {
		String[] strippedLines = new String[lines.length];
		for (int i = 0; i < lines.length; i++) {
			String[] components = lines[i].split(TuringMachine.commentStart);
			strippedLines[i] = components[0].trim();
		}
		return strippedLines;
	}

	/**
	 * Build the states and transitions of a Turing machine from an ASCII
	 * description.
	 * 
	 * This method creates the this.transitions dictionary attribute, and populates
	 * it with the transitions in the given description. Nothing is returned. This
	 * method can also deal with building blocks, recursively reading descriptions
	 * of any building blocks encountered and adding them to the current machine.
	 * 
	 * @param tmString
	 *            Turing machine description
	 * @throws WcbcException
	 * @throws IOException
	 */
	private void read(String tmString) throws WcbcException, IOException {
		initTransitions();
		// split on newlines
		String[] tmLines = tmString.split("\n");
		// strip comments
		tmLines = stripComments(tmLines);
		utils.trimAll(tmLines);
		for (String line : tmLines) {
			if (line.length() > 0) {
				if (line.startsWith(TuringMachine.blockMarker)) {
					addBlock(line);
				} else {
					Transition t = extractTransition(line);
					addTransition(t);
				}
			}
		}

	}

	private void initTransitions() {
		this.transitions = new HashMap<String, ArrayList<Transition>>();
	}

	/**
	 * Convert a transition into Turing machine description format.
	 * 
	 * @param t
	 *            the Transition object to be converted to description format
	 * @return description format of the transition t
	 * @throws WcbcException
	 */
	protected String writeTransition(Transition t) throws WcbcException {
		String[] componentsBase = { t.getSourceState(), TuringMachine.stateSeparator, t.getDestState(),
				TuringMachine.labelSeparator, " ", t.getLabel(), TuringMachine.writeSymSeparator };

		ArrayList<String> components = new ArrayList<>(Arrays.asList(componentsBase));
		if (t.getWriteSymbol() != null) {
			components.add(t.getWriteSymbol());
			components.add(TuringMachine.actionSeparator);
		}
		components.add(dirToStr(t.getDirection()));
		return utils.join(components);
	}

	/**
	 * Convert the current Turing machine into description format.
	 * 
	 * @return description format of the current machine, suitable for storing in a
	 *         .tm file.
	 * @throws WcbcException
	 */
	public String write() throws WcbcException {
		if (this.blocks.size() > 0) {
			throw new WcbcException("Error: writing Turing machines is not implemented for blocks.");
		}
		if (this.transitions == null) {
			return "[No transitions]";
		}
		ArrayList<String> lines = new ArrayList<>();
		for (ArrayList<Transition> tList : this.transitions.values()) {
			for (Transition t : tList) {
				String line = this.writeTransition(t);
				lines.add(line);
			}
		}
		Collections.sort(lines);
		return utils.join(lines, "\n");
	}

	/**
	 * Add a building block to the current machine.
	 * 
	 * @param line
	 *            A line in a Turing machine description that requests the insertion
	 *            of a block. For an example, see the file countCs.tm.
	 * @throws WcbcException
	 * @throws IOException
	 */
	private void addBlock(String line) throws WcbcException, IOException {
		if (this.depth == TuringMachine.maxDepth) {
			throw new WcbcException("Exceeded max depth when adding block");
		}
		// Remove the initial block marker
		line = line.substring(TuringMachine.blockMarker.length());

		// Partition on the TuringMachine.blockSeparator
		String[] components = line.split(TuringMachine.blockSeparator);
		if (components.length != 2) {
			throw new WcbcException("Unexpected format in block description: " + line);
		}
		utils.trimAll(components);
		String state = components[0];
		String filename = components[1];

		TuringMachine newBlock = new TuringMachine(utils.rf(filename), "", this.depth + 1, filename,
				allowImplicitReject, allowLeftFromCell0, keepHistory);
		this.blocks.put(state, newBlock);
	}

	/**
	 * Return the symbol currently scanned by the machine.
	 * 
	 * @return The return value will be a single character, which is the value of
	 *         the tape cell at the current position.
	 */
	private char getScannedSymbol() {
		return this.tape.get(this.headPos);
	}

	/**
	 * Return True if the given symbol is valid for a transition with the given
	 * label.
	 * 
	 * Usually, this will return True if symbol is one of the characters in label,
	 * but this method also handles certain special cases, such as the special
	 * symbol that matches any character, and the use of "!" for "not".
	 * 
	 * @param symbol
	 *            a single character
	 * @param label
	 *            the label attribute of a Transition. See the Transition
	 *            documentation.
	 * @return True if the symbol is valid for a transition with the given label,
	 *         and False otherwise.
	 */
	private boolean labelMatchesSymbol(String symbol, String label) {
		final char notSymChar = TuringMachine.notSym.charAt(0);
		if (label.equals(anySym)) {
			return true;
		} else if (label.charAt(0) == notSymChar) {
			if (!label.substring(1).contains(symbol)) {
				return true;
			} else {
				return false;
			}
		} else if (label.contains(symbol)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Return True if t is a valid transition for the current configuration.
	 * 
	 * @param t
	 *            a Transition object
	 * @return True if the current scanned symbol matches the label of t, meaning
	 *         that t is a transition that can be followed from the current
	 *         configuration.
	 */
	private boolean isValidTransition(Transition t) {
		char scannedSymbol = this.getScannedSymbol();
		return this.labelMatchesSymbol(Character.toString(scannedSymbol), t.getLabel());
	}

	/**
	 * Return a list of the possible transitions from the given state.
	 * 
	 * This ignores the scanned symbol. The returned transitions are all the
	 * transitions that could ever be followed from the given state.
	 * 
	 * @param state
	 *            a state in the Turing machine
	 * @return A list containing all transitions whose source state is the given
	 *         state parameter, or null if there are no such transitions.
	 */
	public ArrayList<Transition> getTransitions(String state) {
		ArrayList<Transition> trList = this.transitions.get(state);
		return trList;
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
	public ArrayList<Transition> getValidTransitions() {
		ArrayList<Transition> transitionList = this.getTransitions(this.state);
		ArrayList<Transition> ts = new ArrayList<>();
		if (transitionList != null) {
			for (Transition t : transitionList) {
				if (this.isValidTransition(t)) {
					ts.add(t);
				}
			}
		}
		return ts;
	}

	/**
	 * Write the given symbol onto the tape at the current head position.
	 * 
	 * @param symbol
	 *            a single character to be written onto the tape
	 */
	private void writeSymbol(char symbol) {
		this.tape.set(headPos, symbol);
	}

	/**
	 * Add a transition to this machine.
	 * 
	 * @param t
	 *            the Transition object to be added
	 */
	private void addTransition(Transition t) {
		if (this.transitions == null) {
			initTransitions();
		}
		ArrayList<Transition> transitionList = null;
		String sourceState = t.getSourceState();
		if (this.transitions.containsKey(sourceState)) {
			transitionList = this.transitions.get(sourceState);
		} else {
			transitionList = new ArrayList<Transition>();
			this.transitions.put(sourceState, transitionList);
		}
		transitionList.add(t);
	}

	/**
	 * True if the given state is a halting state, and False otherwise.
	 */
	static private boolean isAHaltingState(String state) {
		return state.equals(TuringMachine.acceptState) || state.equals(TuringMachine.rejectState)
				|| state.equals(TuringMachine.haltState);
	}

	/**
	 * Apply the given transition to the current configuration.
	 * 
	 * This implements one computational step of the Turing machine, following the
	 * given transition to its destination state, writing a symbol onto the tape if
	 * necessary, and moving the head if necessary.
	 * 
	 * @param t
	 *            the Transition to be followed. If t is None and implicit rejection
	 *            is permitted, the machine will transition into the project state.
	 * @throws WcbcException
	 */
	public void applyTransition(Transition t) throws WcbcException {
		if (TuringMachine.verbose) {
			System.out.println("Applying transition" + t.toString());
		}

		this.steps = this.steps + 1;
		if (t == null) {
			if (this.allowImplicitReject) {
				this.state = TuringMachine.rejectState;
			} else {
				String message = "***Error***: No valid transition was found, and implicit rejects are disabled.\n"
						+ "Current configuration:\n" + this.toString();
				throw new WcbcException(message);
			}
		} else {
			this.state = t.getDestState();
		}
		if (isAHaltingState(this.state)) {
			halted = true;
		}
		if (t != null) {
			if (t.getWriteSymbol() != null) {
				writeSymbol(t.getWriteSymbol().charAt(0));
			}
			if (t.getDirection() == strToDir(TuringMachine.leftDir)) {
				if (headPos > 0) {
					this.headPos = this.headPos - 1;
				} else if (this.allowLeftFromCell0) {
					// do nothing
				} else {
					String message = "***Error***: Tried to move left from cell 0, which is disallowed by "
							+ "this Turing machine.\n" + "Current configuration:\n" + this.toString();
					throw new WcbcException(message);
				}
			} else if (t.getDirection() == strToDir(TuringMachine.rightDir)) {
				this.headPos = this.headPos + 1;
				if (this.headPos == this.tape.size()) {
					this.tape.add(TuringMachine.blank.charAt(0));
				}
			}
		}
		if (this.keepHistory) {
			this.history.add(this.toString());
		}
	}

	private void runBlock() throws WcbcException {
		TuringMachine block = this.blocks.get(this.state);
		block.reset(utils.joinChars(this.tape), TuringMachine.startState, this.headPos);
		block.run();
		this.steps = this.steps + block.steps;
		this.tape = block.tape;
		this.headPos = block.headPos;
		if (block.state == TuringMachine.rejectState) {
			this.state = TuringMachine.rejectState;
			this.halted = true;
		}
	}

	// Perform one computational step for this Turing machine.
	private void step() throws WcbcException {
		ArrayList<Transition> ts = this.getValidTransitions();
		if (ts.size() > 1) {
			String message = "***Error***: multiple valid transitions in deterministic Turing machine.\n"
					+ "Current state:" + this.state + "\n" + "Valid transitions:" + ts.toString();
			throw new WcbcException(message);
		}
		Transition t;
		if (ts.size() == 0) {
			t = null;
		} else {
			t = ts.get(0);
		}
		this.applyTransition(t);
		if (this.blocks.containsKey(this.state)) {
			this.runBlock();
		}
	}

	@Override
	public String toString() {
		String name = null;
		if (this.name != null) {
			name = this.name + ": ";
		} else {
			name = "";
		}
		return name + String.format("steps: %d tape: %s state: %s, headPos: %d", this.steps, utils.joinChars(this.tape),
				this.state, this.headPos);
	}

	/**
	 * Return the output of the Turing machine.
	 * 
	 * By definition, the output of a Turing machine is the tape contents up to but
	 * not including the first blank.
	 * 
	 * @return output of the Turing machine
	 */
	public String getOutput() {
		int blankIndex = -1;
		// Find the index of the first blank on the tape.
		if (!tape.contains(TuringMachine.blank.charAt(0))) {
			// there is an implicit blank at the end of the tape
			blankIndex = tape.size();
		} else {
			blankIndex = this.tape.indexOf(TuringMachine.blank.charAt(0));
		}
		List<Character> truncatedTape = tape.subList(0, blankIndex);
		return utils.joinChars(truncatedTape);
	}

	// Allow derived classes to override ...?? //todo
	public int getMaxsteps() {
		return maxSteps;
	}

	/**
	 * Run the Turing machine until it halts and return the output.
	 * 
	 * For practical reasons, the machine will also stop once it exceeds its maximum
	 * number of steps.
	 * 
	 * @return The output of the machine's computation
	 * @throws WcbcException
	 */
	public String run() throws WcbcException {
		while (!this.halted && this.steps < this.getMaxsteps()) {
			this.step();
		}
		if (this.state.equals(TuringMachine.acceptState)) {
			return TuringMachine.yesStr;
		} else if (this.state.equals(TuringMachine.rejectState)) {
			return TuringMachine.noStr;
		} else if (this.state.equals(TuringMachine.haltState)) {
			return this.getOutput();
		} else {
			if (this.steps < this.getMaxsteps()) {
				String message = "Turing machine halted in an unexpected way";
				throw new WcbcException(message);
			} else {
				this.raiseExceededMaxSteps();
			}
			return null;
		}
	}

	/**
	 * Raise an exception indicating that the maximum number of steps was exceeded.
	 * 
	 * @throws WcbcException
	 */
	public void raiseExceededMaxSteps() throws WcbcException {
		String message = TuringMachine.exceededMaxStepsMsg + ".  Current output: " + this.getOutput();
		throw new WcbcException(message);
	}

	// Initialize the tape with the given content
	private void initTape(String tapeContent) {
		this.tape = new ArrayList<>();
		for (int i = 0; i < tapeContent.length(); i++) {
			tape.add(tapeContent.charAt(i));
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
		initTape(tapeStr);
		if (state != null) {
			this.state = state;
		} else {
			this.state = TuringMachine.startState;
		}
		this.headPos = headPos;
		this.halted = false;
		this.steps = steps;
		// append blanks up to the head position if necessary
		if (this.headPos >= this.tape.size()) {
			int numBlanksNeeded = this.headPos - this.tape.size() + 1;
			for (int i = 0; i < numBlanksNeeded; i++) {
				this.tape.add(TuringMachine.blank.charAt(0));
			}
		}
		if (this.keepHistory && resetHistory) {
			initHistory();
		}
	}

	private void initHistory() {
		this.history = new ArrayList<>();
		this.history.add(this.toString());
	}

	/**
	 * Copy most of the state of this Turing machine to the destination machine.
	 * 
	 * This is a helper to the clone() method, factored out so that derived classes
	 * can implement their own clone() then call this helper. Copies the
	 * transitions, blocks and history attributes to the destination then resets it.
	 * 
	 * 
	 * @param dest
	 *            Destination machine to which state will be copied.
	 */
	protected void copyTMState(TuringMachine dest) {
		dest.transitions = this.transitions;
		dest.blocks = this.blocks;
		dest.keepHistory = this.keepHistory;
		if (this.keepHistory) {
			dest.history = new ArrayList<String>(this.history);
		}
		dest.reset(utils.joinChars(this.tape), this.state, this.headPos, this.steps, false);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		TuringMachine newTM = null;
		try {
			newTM = new TuringMachine(null, utils.joinChars(this.tape), this.depth, this.name);
		} catch (WcbcException e) {
			throw new CloneNotSupportedException(e.getMessage());
		} catch (IOException e) {
			throw new CloneNotSupportedException(e.getMessage());
		}
		this.copyTMState(newTM);
		return newTM;
	}

	public void printTransitions() {
		for (ArrayList<Transition> tList : this.transitions.values()) {
			for(Transition t:tList) {
			System.out.println(t.toString());}
		}
	}

	/**
	 * Check if a given symbol is permitted in Turing machines.
	 * 
	 * Nothing is returned, but a WcbcException is raised if the symbol is invalid.
	 * 
	 * @param t
	 *            the Transition in which c is used.
	 * @param c
	 *            a single character which is the symbol to be checked
	 * @throws WcbcException
	 */
	private void checkSymbolIsValid(Transition t, char c) throws WcbcException {
		if (!TuringMachine.validSymbols.contains(c)) {
			String message = String
					.format("***Error***: The symbol %c (ASCII value %d) is not permitted in Turing machine alphabets. "
							+ "The full transition containing this error is:\n%s", c, (int) c, t.toString());
			throw new WcbcException(message);
		}
	}

	/**
	 * Check that all symbols used in this machine are permitted.
	 * 
	 * Nothing is returned, but a WcbcException is raised if a symbol is invalid.
	 * 
	 * @throws WcbcException
	 */
	private void checkAllSymbolsValid() throws WcbcException {
		if (transitions != null) {
			for (ArrayList<Transition> tList : this.transitions.values()) {
				for (Transition t : tList) {
					String label = t.getLabel();
					if (label.equals(TuringMachine.anySym)) {
						continue;
					} else if (label.charAt(0) == TuringMachine.notSym.charAt(0)) {
						label = label.substring(1);
					}
					for (int i = 0; i < label.length(); i++) {
						checkSymbolIsValid(t, label.charAt(i));
					}
				}
			}
		}

	}

	/**
	 * Determine whether two Turing machines have the same transitions.
	 * 
	 * @param tm1
	 *            first Turing machine to be compared
	 * @param tm2
	 *            second Turing machine to be compared
	 * @return True if the two given Turing machines have transitions that are
	 *         equivalent, and False otherwise.
	 */
	public boolean haveSameTransitions(TuringMachine tm1, TuringMachine tm2) {
		return tm1.hasSameTransitions(tm2) && tm2.hasSameTransitions(tm1);
	}

	/**
	 * Determine whether every transition in this Turing machine exists in the other
	 * machine.
	 * 
	 * This method is not symmetric in the parameters. It checks if every transition
	 * in this has a corresponding transition in other, but other may have
	 * additional transitions.
	 * 
	 * @param other
	 *            machine whose transitions will be compared with the current
	 *            machine
	 * @return True if every transition in this has a corresponding transition in
	 *         other.
	 */
	private boolean hasSameTransitions(TuringMachine other) {
		for (String state : transitions.keySet()) {
			ArrayList<Transition> tList = transitions.get(state);
			ArrayList<Transition> otherTList = other.getTransitions(state);
			for (Transition t : tList) {
				ArrayList<Transition> otherCompatible = new ArrayList<Transition>();
				for (Transition tr : otherTList) {
					if (t.isCompatible(tr)) {
						otherCompatible.add(tr);
					}
				}
				for (Character c : TuringMachine.validSymbolsArray) {
					if (labelMatchesSymbol(c.toString(), t.getLabel())) {
						boolean foundMatch = false;
						for (Transition otherTrans : otherCompatible) {
							if (labelMatchesSymbol(c.toString(), otherTrans.getLabel())) {
								foundMatch = true;
								break;
							}
						}
						if (!foundMatch) {
							return false;
						}
					}
				}
			}
		}

		return true;
	}

	///////////////////////////////////////////////////////////////////////////////
	// The following Python methods were not translated into Java. Hopefully we
	// won't need them:
	//
	// sortLabelChars(this, s); standardizeDescription(this, d);
	// descriptionsAreSame(this, thisDesc, other, otherDesc); unifyTransitions(this)
	///////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws IOException, WcbcException {
		// String description = utils.rf("containsGAGA.tm");
		// String tapeStr = "CCCGAGACCAAAAAA";
		String description = utils.rf("countCs.tm");
		String tapeStr = "xGCGCGCACGCGGGx";
		TuringMachine tm = new TuringMachine(description, tapeStr);
		String result = tm.run();
		System.out.println(result);
	}

}
