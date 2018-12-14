package wcbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A TwoTDCM object models a 2TDCM as described in the textbook.
 * 
 * It does not support blocks or nondeterminism.
 */
public class TwoTDCM extends TuringMachine {

	// Symbols that can be written on the output tape -- what Turing would
	// call "of the first kind"
	public static final Set<Character> outSymbols = new HashSet<>(
			Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

	// Best to allow few steps as 2TDCM's often run forever. This value will be used
	// to override the superclass, via getMaxsteps().
	private static final int maxSteps = 200;

	/**
	 * The contents of the special output tape, where symbols "of the first kind"
	 * (i.e. in outSymbols) will be written. It is append-only.
	 */
	protected ArrayList<Character> outTape = new ArrayList<>();

	public TwoTDCM(String description, String tapeStr, int depth, String name, boolean allowImplicitReject,
			boolean allowLeftFromCell0, boolean keepHistory) throws WcbcException, IOException {
		super(description, tapeStr, depth, name, allowImplicitReject, allowLeftFromCell0, keepHistory);
	}

	public TwoTDCM(String description, String tapeStr, int depth, String name) throws WcbcException, IOException {
		super(description, tapeStr, depth, name);
	}

	public TwoTDCM(String description, String tapeStr) throws WcbcException, IOException {
		super(description, tapeStr);
	}

	public TwoTDCM(String description) throws WcbcException, IOException {
		super(description);
	}

	@Override
	public int getMaxsteps() {
		return TwoTDCM.maxSteps;
	}

	/*
	 * Write the given symbol. If it's an output symbol, append to the output tape.
	 * Otherwise write to the cuurent cell of the regular tape.
	 */
	@Override
	protected void writeSymbol(char symbol) {
		if (outSymbols.contains(symbol)) {
			outTape.add(symbol);
		} else {
			super.writeSymbol(symbol);
		}
	}

	/*
	 * Return contents of output tape as a string.
	 */
	@Override
	public String getOutput() {
		return utils.joinChars(outTape);
	}

	@Override
	public void reset(String tapeStr, String state, int headPos, int steps, boolean resetHistory) {
		outTape = new ArrayList<>();		
		super.reset(tapeStr, state, headPos, steps, resetHistory);
	}

	
	
}
