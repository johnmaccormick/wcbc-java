package wcbc;

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
