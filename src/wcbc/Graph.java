package wcbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import java.util.Set;

/**
 * A Graph object represents a graph i.e., a collection of nodes with edges
 * between them.
 * 
 * The graph may be weighted or unweighted, directed or undirected.
 *
 */
public class Graph implements Iterable<String> {

	/**
	 * True if this object represents a weighted graph.
	 */
	private boolean weighted;

	/**
	 * True if this object represents a directed graph.
	 */
	private boolean directed;

	/**
	 * A map storing the structure of the graph. It maps strings to maps. Each key
	 * is a node name n, and the corresponding value this.nodes[n] is a map of
	 * outgoing edges leaving n. Each of these maps of outgoing edges maps strings
	 * to integers: each key in this.nodes[n] is a neighbor m of n, and the value
	 * this.nodes[n][m] is the weight of the edge from n to m.
	 */
	private Map<String, Map<String, Integer>> nodes;

	/**
	 * Each element is an isolated node, i.e. a node with no incoming or outgoing
	 * edges. This will be computed lazily, on demand.
	 */
	private Set<String> isolatedNodes = null;

	/**
	 * Construct a new Graph object from a given ASCII description.
	 * 
	 * @param graphString
	 *            A description of the graph as an ASCII string, as described in the
	 *            textbook. Examples include "a,b b,c c,a" and "a,b,4 b,c,3".
	 * @param weighted
	 *            True if this is a weighted graph and False otherwise.
	 * @param directed
	 *            True if this is a directed graph and False otherwise.
	 * @throws WcbcException
	 */
	public Graph(String graphString, boolean weighted, boolean directed) throws WcbcException {
		this.weighted = weighted;
		this.directed = directed;
		this.nodes = new HashMap<>();
		this.readDescription(graphString);
	}

	/**
	 * Read the given ASCII description of a graph, storing information about the
	 * nodes and edges.
	 * 
	 * This is intended to be a private method called by the constructor. It will
	 * update this.nodes, which should be an empty dictionary when this method is
	 * called.
	 * 
	 * @param graphString
	 *            a description of the graph as an ASCII string, as described in the
	 *            textbook. Examples include "a,b b,c c,a" and "a,b,4 b,c,3".
	 * @throws WcbcException
	 */
	private void readDescription(String graphString) throws WcbcException {
		String[] split = utils.splitOnWhitespace(graphString);
		ArrayList<String> edgeDescriptions = new ArrayList<>();
		for (String s : split) {
			edgeDescriptions.add(s.trim());
		}
		for (String edgeDescription : edgeDescriptions) {
			if (edgeDescription.length() > 0) {
				String[] components = edgeDescription.split(",");
				String sourceStr = null;
				String destStr = null;
				String weightStr = null;
				int weight = -1; // deliberately invalid
				if (components.length == 1) {
					// isolated node with no edges (yet)
					sourceStr = components[0];
				} else if (this.weighted) {
					if (components.length != 3) {
						throw new WcbcException(
								"expected 3 components in edge description " + edgeDescription + " for weighted graph");
					}
					sourceStr = components[0];
					destStr = components[1];
					weightStr = components[2];
					weight = Integer.parseInt(weightStr);
				} else { // unweighted
					if (components.length != 2) {
						throw new WcbcException("expected 2 components in edge description " + edgeDescription
								+ " for unweighted graph");
					}
					sourceStr = components[0];
					destStr = components[1];
					weight = 1;
				}

				if (sourceStr.length() == 0 || (destStr != null && destStr.length() == 0)) {
					throw new WcbcException("encountered node name of length zero");
				}

				if (!nodes.containsKey(sourceStr)) {
					nodes.put(sourceStr, new HashMap<String, Integer>());
				}

				Map<String, Integer> source = nodes.get(sourceStr);
				if (destStr != null) {
					if (!nodes.containsKey(destStr)) {
						// we haven't seen this node before -- create it
						nodes.put(destStr, new HashMap<String, Integer>());
					}
					if (source.containsKey(destStr)) {
						throw new WcbcException("duplicate edge: " + sourceStr + " " + destStr);
					}
					source.put(destStr, weight);
					if (!directed) {
						Map<String, Integer> dest = nodes.get(destStr);
						if (dest.containsKey(sourceStr) && !sourceStr.equals(destStr)) {
							throw new WcbcException("duplicate edge: " + destStr + " " + sourceStr);
						}
						dest.put(sourceStr, weight);
					}
				}
			}
		}
	}

	/**
	 * Construct a new directed Graph object from a given ASCII description.
	 * 
	 * @param graphString
	 *            A description of the graph as an ASCII string, as described in the
	 *            textbook. Examples include "a,b b,c c,a" and "a,b,4 b,c,3".
	 * @param weighted
	 *            True if this is a weighted graph and False otherwise.
	 * @throws WcbcException
	 */
	public Graph(String graphString, boolean weighted) throws WcbcException {
		this(graphString, weighted, true);
	}

	/**
	 * Construct a new weighted, directed Graph object from a given ASCII
	 * description.
	 * 
	 * @param graphString
	 *            A description of the graph as an ASCII string, as described in the
	 *            textbook. Examples include "a,b b,c c,a" and "a,b,4 b,c,3".
	 * @param weighted
	 *            True if this is a weighted graph and False otherwise.
	 * @throws WcbcException
	 */
	public Graph(String graphString) throws WcbcException {
		this(graphString, true, true);
	}

	@Override
	public String toString() {
		ArrayList<String> edgeStrings = new ArrayList<>();
		Map<Edge, Integer> edgeMap;
		try {
			edgeMap = this.getEdgesAsDict();
		} catch (WcbcException e) {
			throw new RuntimeException(e.getMessage());
		}
		for (Entry<Edge, Integer> pair : edgeMap.entrySet()) {
			Edge edge = pair.getKey();
			Integer weight = pair.getValue();
			String edgeString = null;
			if (this.weighted) {
				edgeString = edge.toString() + "," + weight.toString();
			} else {
				edgeString = edge.toString();
			}
			edgeStrings.add(edgeString);
		}

		ArrayList<String> edgesAndIsolatedNodes = new ArrayList<>(edgeStrings);
		edgesAndIsolatedNodes.addAll(this.getIsolatedNodes());
		Collections.sort(edgesAndIsolatedNodes);
		String graphString = utils.join(edgesAndIsolatedNodes, " ");
		return graphString;
	}

	private Set<String> computeIsolatedNodes() {
		Set<String> isolated = new HashSet<>(this.nodes.keySet());
		for (Entry<String, Map<String, Integer>> pair : this.nodes.entrySet()) {
			String node = pair.getKey();
			Map<String, Integer> neighbors = pair.getValue();
			if (neighbors.size() > 0) {
				isolated.remove(node);
			}
			for (String neighbor : neighbors.keySet()) {
				isolated.remove(neighbor);
			}
		}
		return isolated;
	}

	private Set<String> getIsolatedNodes() {
		if (this.isolatedNodes == null) {
			this.isolatedNodes = computeIsolatedNodes();
		}
		return isolatedNodes;
	}

	public int getNumNodes() {
		return nodes.size();
	}

	public Map<Edge, Integer> getEdgesAsDict() throws WcbcException {
		Map<Edge, Integer> edges = new HashMap<>();
		for (Entry<String, Map<String, Integer>> pair : this.nodes.entrySet()) {
			String node = pair.getKey();
			Map<String, Integer> neighbors = pair.getValue();
			for (Entry<String, Integer> pair2 : neighbors.entrySet()) {
				String neighbor = pair2.getKey();
				Integer weight = pair2.getValue();
				String[] edgeNodes = { node, neighbor };
				String[] reversedEdgeNodes = { neighbor, node };
				Edge edge = new Edge(edgeNodes);
				Edge reversedEdge = new Edge(reversedEdgeNodes);
				// If undirected, store only one direction. By
				// convention, this will be the lexicographically
				// earlier one.
				if (!directed && reversedEdge.compareTo(edge) < 0) {
					edge = reversedEdge;
				}
				// Now store the edge
				edges.put(edge, weight);
			}
		}
		return edges;
	}

	public boolean containsNode(String node) {
		return nodes.containsKey(node);
	}

	/**
	 * Add the given node to the graph.
	 * 
	 * The node is added to the graph as an isolated node with no incoming or
	 * outgoing edges.
	 * 
	 * @param node
	 *            The node to be added.
	 * @throws WcbcException
	 *             thrown if the node is already present
	 */
	public void addNode(String node) throws WcbcException {
		if (this.containsNode(node)) {
			throw new WcbcException("Tried to add existing node " + node);
		}
		this.nodes.put(node, new HashMap<String, Integer>());
		isolatedNodes = null; // force recomputation later, when needed
	}

	@Override
	protected Graph clone() throws CloneNotSupportedException {
		Graph g = null;
		try {
			// inefficient, but extremely easy to implement!
			g = new Graph(this.toString(), this.weighted, this.directed);
		} catch (WcbcException e) {
			throw new CloneNotSupportedException(e.getMessage());
		}
		return g;
	}

	@Override
	public Iterator<String> iterator() {
		return this.nodes.keySet().iterator();
	}

	/**
	 * Return a set consisting of all nodes in the graph.
	 * 
	 * @return a set consisting of all nodes in the graph
	 */
	public Set<String> getNodesAsSet() {
		return this.nodes.keySet();
	}

	/**
	 * Return a set of edges in the graph.
	 * 
	 * @return a set of edges in the graph
	 * @throws WcbcException
	 */
	private Set<Edge> getEdgesAsSet() throws WcbcException {
		return this.getEdgesAsDict().keySet();
	}

	/**
	 * Return a set of edges in the graph.
	 * 
	 * @return a set of edges in the graph
	 * @throws WcbcException
	 */
	public Set<Edge> edges() throws WcbcException {
		// At present, this is implemented rather inefficiently via getEdgesAsSet(),
		// which makes a new copy of all the edges. A future implementation could do
		// something better than this.
		return this.getEdgesAsSet();
	}

	/**
	 * Return the neighbors of a given node.
	 * 
	 * @param node
	 *            The node whose neighbors will be returned
	 * @return the neighbors of the given node
	 * @throws WcbcException
	 */
	public Set<String> neighbors(String node) throws WcbcException {
		checkContainsNode(node);
		return this.nodes.get(node).keySet();
	}

	/**
	 * Throw an exception if the given node is not in this graph.
	 * 
	 * @param node
	 *            The node to search for.
	 * @throws WcbcException
	 *             If the node is not present in the graph.
	 */
	public void checkContainsNode(String node) throws WcbcException {
		if (!nodes.containsKey(node)) {
			throw new WcbcException("node " + node + " not in graph");
		}
	}

	private void checkContainsEdge(Edge edge) throws WcbcException {
		if (!containsEdge(edge)) {
			throw new WcbcException("edge " + edge + " not in graph");
		}
	}

	/**
	 * Return a dictionary of the neighbors of a given node with weights as keys.
	 * 
	 * The neighbors of node n are defined to be all nodes that are at the end of
	 * outgoing edges from n.
	 * 
	 * @param node
	 *            The node whose neighbors will be returned
	 * 
	 * @return the neighbors of the given node. Each key in the dictionary is a
	 *         neighbor m of n, i.e. there is an edge from n to m. The value
	 *         corresponding to key m is the weight of the edge n to m.
	 * @throws WcbcException
	 */
	public Map<String, Integer> weightedNeighbors(String node) throws WcbcException {
		checkContainsNode(node);
		return this.nodes.get(node);
	}

	/**
	 * Return the weight of the given edge.
	 * 
	 * @param edge
	 *            the edge to be searched for
	 * @return the weight of the given edge.
	 * @throws WcbcException
	 *             thrown if the edge is not present
	 */
	public int getWeight(Edge edge) throws WcbcException {
		checkContainsEdge(edge);
		String node1 = edge.start();
		String node2 = edge.end();
		return nodes.get(node1).get(node2);
	}

	/**
	 * Add an edge to the graph.
	 * 
	 * @param edge
	 *            the edge to be added
	 * @param weight
	 *            the weight of the edge to be added
	 * @throws WcbcException
	 *             An exception is thrown if the edge is already present. An
	 *             exception is also thrown if the edge contains a node that is not
	 *             in the graph.
	 */
	public void addEdge(Edge edge, int weight) throws WcbcException {
		String node1 = edge.start();
		String node2 = edge.end();
		if (this.containsEdge(edge)) {
			throw new WcbcException("edge " + edge.toString() + " already in graph");
		}
		checkContainsNode(node1);
		checkContainsNode(node2);

		nodes.get(node1).put(node2, weight);
		if (!directed) {
			nodes.get(node2).put(node1, weight);
		}
		isolatedNodes = null; // force recomputation later, when needed
	}

	/**
	 * Add an edge with default weight 1 to the graph.
	 * 
	 * @param edge
	 *            the edge to be added
	 * @throws WcbcException
	 *             An exception is thrown if the edge is already present. An
	 *             exception is also thrown if the edge contains a node that is not
	 *             in the graph.
	 */
	public void addEdge(Edge edge) throws WcbcException {
		addEdge(edge, 1);
	}

	/**
	 * Return True if the graph contains the given edge and False otherwise
	 * 
	 * @param edge
	 *            the edge to be searched for
	 * @return True if the graph contains the given edge and False otherwise
	 * @throws WcbcException
	 */
	public boolean containsEdge(Edge edge) throws WcbcException {
		String node1 = edge.start();
		String node2 = edge.end();
		if (!this.containsNode(node1) || !this.containsNode(node2)) {
			return false;
		}
		if (nodes.get(node1).containsKey(node2)) {
			return true;
		} else if (!directed && nodes.get(node2).containsKey(node1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Remove an edge from the graph.
	 * 
	 * @param edge
	 *            the edge to be removed
	 * @throws WcbcException
	 *             An exception is thrown if the edge is not already present. This
	 *             implicitly requires that both nodes in the edge are also present.
	 */
	public void removeEdge(Edge edge) throws WcbcException {
		checkContainsEdge(edge);
		String node1 = edge.start();
		String node2 = edge.end();
		if (nodes.get(node1).containsKey(node2)) {
			nodes.get(node1).remove(node2);
		}
		if (!directed) {
			if (nodes.get(node2).containsKey(node1)) {
				nodes.get(node2).remove(node1);
			}

		}
		isolatedNodes = null; // force recomputation later, when needed
	}

	/**
	 * Return true if the given path exists as a simple path in the current graph.
	 * 
	 * The path passed in as a parameter is really just a sequence of nodes. The
	 * question is, does each consecutive pair of nodes in that sequence exist as an
	 * edge in the current graph? Optionally, we can also check whether the start
	 * and end of the path correspond to particular nodes.
	 * 
	 * Note that this method does not permit paths to have repeated edges i.e. it
	 * returns True only if the given path is a simple** path. Perhaps the method
	 * would be better named isSimplePath(), but for the purposes of the textbook we
	 * are only interested in simple paths and it seems easier to stick with a short
	 * method name.
	 * 
	 * @param path
	 *            the sequence p of nodes to be investigated
	 * @param source
	 *            the name of the node that will be checked to see if it is the
	 *            start of the path, or null to skip this check
	 * @param dest
	 *            the name of the node that will be checked to see if it is the end
	 *            of the path, or null to skip this check
	 * @return True if p exists as a simple path in the current graph, and False
	 *         otherwise. In addition, if source is specified, we return False
	 *         unless the given source is the start of p. Similarly, if dest is
	 *         specified, we return False unless the given dest is the end of p.
	 * @throws WcbcException
	 */
	public boolean isPath(Path path, String source, String dest) throws WcbcException {
		if (path.getLength() == 0) {
			return true;
		}
		if (source == null) {
			source = path.start();
		}
		if (dest == null) {
			dest = path.end();
		}
		if (!path.start().equals(source) || !path.end().equals(dest)) {
			return false;
		}

		// use a set to check for repeated edges, which aren't allowed
		// in our definition of a path
		HashSet<Edge> edges = new HashSet<>();
		for (Edge e : path) {
			if (!this.containsEdge(e)) {
				return false;
			}
			// repeated edges are not allowed
			if (edges.contains(e)) {
				return false;
			}
			// remember this edge, and its reverse if this is not a
			// directed graph
			edges.add(e);
			if (!this.directed) {
				edges.add(e.reversed());
			}
		}
		return true;
	}

	/**
	 * See isPath(Path path, String source, String dest)
	 * 
	 * @throws WcbcException
	 */
	public boolean isPath(Path path) throws WcbcException {
		return isPath(path, null, null);
	}

	/**
	 * Return True if the given path exists as a simple cycle in the current graph.
	 * 
	 * The path passed in as a parameter is just a sequence of nodes. The question
	 * is, does each consecutive pair of nodes in that sequence exist as an edge in
	 * the current graph, and is there also an edge from the final node to the
	 * initial node?
	 * 
	 * Note that this method does not permit cycles to have repeated edges i.e. it
	 * returns True only if the given cycle is a simple** cycle. Perhaps the method
	 * would be better named isSimpleCycle(), but for the purposes of the textbook
	 * we are only interested in simple cycles and it seems easier to stick with a
	 * short method name.
	 * 
	 * Another very important note is our convention for representing cycles: the
	 * "final edge" (from the last node in the cycle back to the first node) is not
	 * explicitly represented in the sequence of nodes passed as a parameter. The
	 * final edge is assumed implicitly to be part of the cycle. If that final edge
	 * is explicitly present in the passed parameter, this method will return False,
	 * because it does not in fact represent a cycle once the implicit final edge
	 * has also been added.
	 * 
	 * @param path
	 *            the sequence p of nodes to be investigated
	 * @return True if p exists as a simple cycle in the current graph (once the
	 *         implicit final edge is added), and False otherwise.
	 * @throws WcbcException
	 */
	public boolean isCycle(Path path) throws WcbcException {
		if (path.getLength() == 0) {
			return true;
		}
		if (!isPath(path)) {
			return false;
		}

		Edge finalEdge = new Edge(path.end(), path.start());
		if (!this.containsEdge(finalEdge)) {
			return false;
		}

		// repeated edge is not permitted in a cycle
		if (path.containsEdge(finalEdge)) {
			return false;
		}
		if (!this.directed && path.containsEdge(finalEdge.reversed())) {
			return false;
		}

		return true;

	}

	/**
	 * Return True if the given path contains all nodes in the current graph exactly
	 * once.
	 * 
	 * @param path
	 *            the sequence p of nodes to be investigated
	 * @return True if p contains all nodes in the current graph exactly once.
	 * @throws WcbcException
	 */
	public boolean containsAllNodesOnce(Path path) throws WcbcException {
		// check that all graph nodes are contained in the path
		for (String node : nodes.keySet()) {
			if (!path.containsNode(node)) {
				return false;
			}
		}

		// check that all path nodes are contained in the graph
		for (String node : path.nodes) {
			if (!this.containsNode(node)) {
				return false;
			}
		}

		// It's possible that some nodes were repeated. An easy way to
		// check for this is to see if the number of nodes in the path
		// is the same as the number of nodes in the graph.
		if (path.getLength() != this.getNumNodes()) {
			return false;
		}

		return true;

	}

	/**
	 * Return True if the given path is a Hamilton path in the current graph.
	 * 
	 * @param path
	 *            the sequence p of nodes to be investigated
	 * @return True if p is a Hamilton path in the current graph.
	 * @throws WcbcException
	 */
	public boolean isHamiltonPath(Path path) throws WcbcException {
		if (!isPath(path)) {
			return false;
		}
		if (!containsAllNodesOnce(path)) {
			return false;
		}

		return true;

	}

	/**
	 * Return True if the given path is a Hamilton cycle in the current graph.
	 * 
	 * See the important note in the documentation for isCycle(): the given path
	 * parameter should not explicitly contain the final edge back to the start of
	 * the cycle; it will be added implicitly.
	 * 
	 * @param path
	 *            the sequence p of nodes to be investigated
	 * @return True if p is a Hamilton cycle in the current graph.
	 * @throws WcbcException
	 */
	public boolean isHamiltonCycle(Path path) throws WcbcException {
		if (!isCycle(path)) {
			return false;
		}
		if (!containsAllNodesOnce(path)) {
			return false;
		}
		return true;
	}

	/**
	 * Return True if the given collection of nodes forms a clique in the current
	 * graph.
	 * 
	 * @param nodes
	 *            the collection of nodes to be investigated
	 * @return True if the given collection of nodes forms a clique in the current
	 *         graph.
	 * @throws WcbcException
	 */
	public boolean isClique(Collection<String> nodes) throws WcbcException {
		for (String node1 : nodes) {
			for (String node2 : nodes) {
				if (!node1.equals(node2)) {
					if (!this.containsEdge(new Edge(node1, node2))) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Convert the current graph to a weighted graph.
	 * 
	 * If the graph is already weighted, it will be unchanged. If it is unweighted,
	 * it will now be a weighted graph with the default weight of 1 for each edge.
	 */
	public void convertToWeighted() {
		this.weighted = true; // slightly evil hack. works fine because
		// all graphs are stored internally as
		// weighted graphs.
	}

	/**
	 * Convert the current graph to a directed graph.
	 * 
	 * If the graph is already directed, it will be unchanged. If it is undirected,
	 * it will now be an equivalent directed graph constructed by replacing each
	 * undirected edge with two directed edges between the same nodes, one in each
	 * direction.
	 */
	public void convertToDirected() {
		this.directed = true; // slightly evil hack. works fine because
		// all graphs are stored internally as
		// directed graphs.
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (directed ? 1231 : 1237);
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		result = prime * result + (weighted ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Graph))
			return false;
		Graph other = (Graph) obj;
		if (directed != other.directed)
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		if (weighted != other.weighted)
			return false;
		return true;
	}

	static final String noPathMsg = " is not a path in the graph ";

	/**
	 * Return the "length" of the given path (i.e. total weight of its edges)
	 * 
	 * For unweighted graphs, the length of the path is the number of edges in it.
	 * For weighted graphs, the "length" is the total weight of its edges. If the
	 * given path is not in fact a path in the current graph, an exception is
	 * raised.
	 * 
	 * @param path
	 *            the sequence p of nodes to be investigated
	 * @return the total weight of the edges in the path
	 * @throws WcbcException
	 *             If the given path is not in fact a path in the current graph, an
	 *             exception is raised.
	 */
	public int pathLength(Path path) throws WcbcException {
		if (!isPath(path)) {
			throw new WcbcException(path.toString() + noPathMsg + this.toString());
		}
		int length = 0;
		for (Edge edge : path) {
			length += getWeight(edge);
		}
		return length;

	}

	static final String noCycleMsg = " is not a cycle in the graph ";

	/**
	 * Return the "length" of the given cycle (i.e. total weight of its edges)
	 * 
	 * For unweighted graphs, the length of the cycle is the number of edges in it.
	 * For weighted graphs, the "length" is the total weight of its edges.
	 * 
	 * See the important note in the documentation for isCycle(): the given cycle
	 * parameter should not explicitly contain the final edge back to the start of
	 * the cycle; it will be added implicitly.
	 * 
	 * @param cycle
	 *            the sequence p of nodes to be investigated
	 * @return the total weight of the edges in the cycle
	 * @throws WcbcException
	 *             If the given cycle is not in fact a cycle in the current graph,
	 *             an exception is raised.
	 */
	public int cycleLength(Path cycle) throws WcbcException {
		if (!isCycle(cycle)) {
			throw new WcbcException(cycle.toString() + noCycleMsg + this.toString());
		}
		if (cycle.getLength() == 0) {
			return 0;
		}
		int pathLen = this.pathLength(cycle);
		Edge finalEdge = new Edge(cycle.end(), cycle.start());
		return pathLen + getWeight(finalEdge);
	}

	/**
	 * Return an arbitrary node in the current graph
	 * 
	 * @return an arbitrary node in the current graph, or null if the graph contains
	 *         no nodes.
	 */
	public String chooseNode() {
		// use a for loop to choose the "first" node in the dictionary
		// of nodes...
		for (String node : this.nodes.keySet()) {
			return node;
		}
		// ...or null if no nodes
		return null;
	}

	/**
	 * Return the total weight of all edges in the graph.
	 * 
	 * For unweighted graphs, each edge has an implicit weight of 1.
	 * 
	 * @return the total weight of all edges in the graph.
	 * @throws WcbcException
	 */
	public int sumEdgeWeights() throws WcbcException {
		int total = 0;
		for (Edge edge : this.edges()) {
			total += getWeight(edge);
		}

		return total;

	}

}
