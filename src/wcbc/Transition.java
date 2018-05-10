package wcbc;

import java.util.ArrayList;
import java.util.List;

/**
 * Models a transition in a Turing machine.
 * 
 * Example: Consider a transition from state q2 to q5, which is followed if the
 * scanned symbol is "G", rewriting the symbol as a "T" then moving the head to
 * the right. In the terminology of this class, q2 is the sourceState, q5 is the
 * destState, G is the label, T is the writeSymbol, and right is the direction.
 *
 */
public class Transition {
	private final String sourceState;
	private final String destState;
	private final String label;
	private final String writeSymbol;
	private final TuringMachine.Direction direction;

	public Transition(String sourceState, String destState, String label, String writeSymbol,
			TuringMachine.Direction direction) {
		this.sourceState = sourceState;
		this.destState = destState;
		this.label = label;
		this.writeSymbol = writeSymbol;
		this.direction = direction;
	}

	public String getSourceState() {
		return sourceState;
	}

	public String getDestState() {
		return destState;
	}

	public String getLabel() {
		return label;
	}

	public String getWriteSymbol() {
		return writeSymbol;
	}

	public TuringMachine.Direction getDirection() {
		return direction;
	}

	/**
	 * Determine whether this transition is compatible with the other transition.
	 * 
	 * Transitions are compatible if they have the same source state, destination
	 * state, write symbol, and direction. That is, they may differ in their label
	 * but nothing else. This method returns True if this transition is compatible
	 * with the other transition, and False otherwise.
	 * 
	 * @param other
	 *            A Transition object to be compared with the calling Transition
	 *            object.
	 * @return True if this transition is compatible with the other transition, and
	 *         False otherwise
	 */
	public boolean isCompatible(Transition other) {
		return this.sourceState == other.sourceState && this.destState == other.destState
				&& this.writeSymbol == other.writeSymbol && this.direction == other.direction;
	}

	/**
	 * Unify the transitions in a list of compatible transitions.
	 * 
	 * Transitions are compatible if they have the same source state, destination
	 * state, write symbol, and direction. That is, they may differ in their label
	 * but nothing else. Sometimes it is convenient to unify compatible transitions
	 * by transforming them into a single transition with a new label that
	 * incorporates all of the input transitions. This method returns a single
	 * transition that unifies a given list of transitions, which must themselves
	 * all be compatible.
	 * 
	 * 
	 * @param tList
	 *            the list of compatible transitions that will be unified.
	 * @return a single unified transition representing all transitions in tList.
	 * @throws WcbcException 
	 */
	public static Transition unify(List<Transition> tList) throws WcbcException {
		if(tList.size()==0) {
			String message = "cannot unify empty list of transitions";
			throw new WcbcException(message);
		}
		Transition first = tList.get(0);
		Transition unifiedTrans = new Transition(first.sourceState, first.destState, null, first.writeSymbol, first.direction);
		for(Transition t:tList) {
			if(!unifiedTrans.isCompatible(t)) {
				String message = "cannot transitions that are not compatible";
				throw new WcbcException(message);
			}
		}
		StringBuilder b = new StringBuilder();
		for(Transition t:tList) {
			b.append(t.label);
		}
		// reconstruct unifiedTrans, this time with the unified label
		String label = b.toString();
		unifiedTrans = new Transition(first.sourceState, first.destState, label, first.writeSymbol, first.direction);
		
		return unifiedTrans;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destState == null) ? 0 : destState.hashCode());
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((sourceState == null) ? 0 : sourceState.hashCode());
		result = prime * result + ((writeSymbol == null) ? 0 : writeSymbol.hashCode());
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
		Transition other = (Transition) obj;
		if (destState == null) {
			if (other.destState != null)
				return false;
		} else if (!destState.equals(other.destState))
			return false;
		if (direction != other.direction)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (sourceState == null) {
			if (other.sourceState != null)
				return false;
		} else if (!sourceState.equals(other.sourceState))
			return false;
		if (writeSymbol == null) {
			if (other.writeSymbol != null)
				return false;
		} else if (!writeSymbol.equals(other.writeSymbol))
			return false;
		return true;
	}

}
