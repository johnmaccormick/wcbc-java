package wcbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A Path object models a path in a graph.
 *
 */
public class Path implements Iterable<Edge>, Comparable<Path> {

	/**
	 * List of the nodes in the path, where each node is a string.
	 */
	protected ArrayList<String> nodes = null;

	/**
	 * A set of strings that is constructed lazily (if and when needed). It will
	 * contain the nodes in the path.
	 */
	protected HashSet<String> nodeSet = null;

	/**
	 * Set of Edge objects in the path. Constructed lazily (if and when needed).
	 */
	protected HashSet<Edge> edgeSet = null;

	/**
	 * Construct a Path from a list of nodes
	 * 
	 * @param nodes
	 *            list of nodes in the path, where each node is a string
	 * @throws WcbcException
	 *             thrown if any node name is the empty string
	 */
	public Path(Collection<String> nodes) throws WcbcException {
		for (String node : nodes) {
			if (node.length() == 0) {
				throw new WcbcException("Encountered empty node name");
			}
		}
		this.nodes = new ArrayList<>(nodes);

	}

	/**
	 * Construct a Path from a list of nodes
	 * 
	 * @param nodes
	 *            list of nodes in the path
	 * @throws WcbcException
	 *             thrown if any node name is the empty string
	 */
	public Path(String[] nodes) throws WcbcException {
		this(Arrays.asList(nodes));
	}

	@Override
	public String toString() {
		return utils.join(nodes, ",");
	}

	/**
	 * @return the number of nodes in the path
	 */
	public int getLength() {
		return nodes.size();
	}

	/**
	 * @param index
	 *            the index of the node to be returned
	 * @return the index-th node in the path
	 */
	public String getNode(int index) {
		return nodes.get(index);
	}

	/**
	 * Return True if node is in this path, and False otherwise.
	 * 
	 * @param node
	 *            The node whose presence is to be detected
	 * @return True if node is in this path, and False otherwise
	 */
	public boolean containsNode(String node) {
		if (this.nodeSet == null) {
			nodeSet = new HashSet<>(nodes);
		}
		return nodeSet.contains(node);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		// Importantly, we allow subclasses to be equal -- this allows
		// an equivalent Path and Edge to be equal.
		if (!(obj instanceof Path))
			return false;
		Path other = (Path) obj;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else {
			if (this.nodes.size() != other.nodes.size()) {
				return false;
			}
			for (int i = 0; i < this.nodes.size(); i++) {
				if (!this.nodes.get(i).equals(other.nodes.get(i))) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	

	/**
	 * Construct a new Path object from a string and return it.
	 * 
	 * @param pathStr
	 * @return This string should list the nodes in the path separated by commas.
	 *         For example: "apple,banana,x,y,z" yields a path consisting of five
	 *         nodes and four edges.
	 * @throws WcbcException
	 */
	public static Path fromString(String pathStr) throws WcbcException {
		ArrayList<String> nodes = new ArrayList<>();
		if (pathStr.length() > 0) {
			nodes.addAll(Arrays.asList(pathStr.split(",")));
		}
		return new Path(nodes);
	}

	/**
	 * Return the initial node in the path.
	 * 
	 * @return the initial node in the path
	 * @throws WcbcException
	 *             if there are no nodes in the path
	 */
	public String start() throws WcbcException {
		if (getLength() == 0) {
			throw new WcbcException("can\"t find start of empty path");
		}
		return this.getNode(0);
	}

	/**
	 * Return the final node in the path.
	 * 
	 * @return the final node in the path
	 * @throws WcbcException
	 *             if there are no nodes in the path
	 */
	public String end() throws WcbcException {
		if (getLength() == 0) {
			throw new WcbcException("can\"t find start of empty path");
		}
		return this.getNode(getLength() - 1);
	}

	/**
	 * Extend the current path by appending the given node.
	 * 
	 * @param node
	 *            The node to be added
	 * @return A new Path object with the given node appended
	 * @throws WcbcException
	 */
	public Path extend(String node) throws WcbcException {
		ArrayList<String> newNodes = new ArrayList<>(this.nodes);
		newNodes.add(node);
		return new Path(newNodes);
	}

	/**
	 * Reverse of the current path.
	 * 
	 * @return A new Path object which is the reverse of the current path.
	 * @throws WcbcException
	 */
	public Path reversed() throws WcbcException {
		ArrayList<String> reversedNodes = new ArrayList<>(this.nodes);
		Collections.reverse(reversedNodes);
		return new Path(reversedNodes);
	}

	/**
	 * Cyclically permute the nodes in the path so that a given node is at the start
	 * of the path.
	 * 
	 * @param node
	 *            The node which should be at the start of the path.
	 * @return A new Path object, cyclically permuted so the given node is at the
	 *         start of the path.
	 * @throws WcbcException
	 *             is thrown if the given node is not in the path
	 */
	public Path rotateToFront(String node) throws WcbcException {
		if (!this.containsNode(node)) {
			String message = "node " + node + " is not in the path " + this.toString();
			throw new WcbcException(message);
		}
		int ind = this.nodes.indexOf(node);
		Collection<String> tail = this.nodes.subList(ind, getLength());
		Collection<String> head = this.nodes.subList(0, ind);
		ArrayList<String> newNodes = new ArrayList<>(tail);
		newNodes.addAll(head);
		return new Path(newNodes);
	}

	private static final class PathIterator implements Iterator<Edge> {
		private int cursor;
		private final int end;
		private Path p;

		public PathIterator(Path p) {
			this.cursor = 0;
			this.end = p.getLength() - 1;
			this.p = p;
		}

		public boolean hasNext() {
			return this.cursor < end;
		}

		public Edge next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			List<String> edgeNodes = p.nodes.subList(cursor, cursor + 2);
			cursor++;
			Edge edge = null;
			try {
				edge = new Edge(edgeNodes);
			} catch (WcbcException e) {
				throw new RuntimeException("couldn't create edge: " + e.getMessage());
			}
			return edge;
		}

	}

	@Override
	public Iterator<Edge> iterator() {
		return new PathIterator(this);
	}

	/**
	 * Return True if the given edge is in this path and False otherwise.
	 * 
	 * @param edge
	 *            The edge we are querying.
	 * @return True if the given edge is in this path and False otherwise.
	 */
	public boolean containsEdge(Edge edge) {
		if (this.edgeSet == null) {
			edgeSet = new HashSet<>();
			for (Edge e : this) {
				edgeSet.add(e);
			}
		}
		return edgeSet.contains(edge);
	}

	@Override
	public int compareTo(Path other) {
		int thisLength = this.getLength();
		int otherLength = other.getLength();
		int minLength = Math.min(thisLength, otherLength);
		for(int i=0; i<minLength; i++) {
			String thisNode =this.getNode(i); 
			String otherNode =other.getNode(i);
			int cmpVal = thisNode.compareTo(otherNode);
			if(cmpVal!=0) { // the two nodes are not equal
				return cmpVal;
			}
		}
		return Integer.compare(thisLength, otherLength);
	}

}
