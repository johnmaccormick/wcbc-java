package wcbc;

import java.util.Collection;

/**
 * An Edge object represents an edge in a path or graph. It is implemented as a
 * Path with exactly 2 nodes, so it inherits all of the methods and properties
 * of Path.
 *
 */
public class Edge extends Path {
	private static String message = "An Edge object must have exactly 2 nodes";
	
	private void checkValidEdge() throws WcbcException {
		if(this.nodes.size()!=2) {
			throw new WcbcException(message);
		}
	}
	
	public Edge(Collection<String> nodes) throws WcbcException {
		super(nodes);
		checkValidEdge();
	}

	public Edge(String[] nodes) throws WcbcException {
		super(nodes);
		checkValidEdge();
	}

	@Override
	public Edge reversed() throws WcbcException {
		return new Edge(super.reversed().nodes);
	}

	@Override
	public Edge rotateToFront(String node) throws WcbcException {
		return new Edge(super.rotateToFront(node).nodes);
	}
	
	
}
