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
		String[] split = graphString.split("\\s+");
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

	private Map<Edge, Integer> getEdgesAsDict() throws WcbcException {
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

	private void checkContainsNode(String node) throws WcbcException {
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
		if(path.getLength()==0) {
			return true;
		}
		if(source==null) {
			source = path.start();
		}
		if(dest==null) {
			dest = path.end();
		}
		if(!path.start().equals(source) || !path.end().equals(dest)) {
			return false;
		}
		
        // use a set to check for repeated edges, which aren't allowed
        // in our definition of a path
        HashSet<Edge> edges = new HashSet<>();
        for(Edge e:path) {
        	if(!this.containsEdge(e)) {
    			return false;        		
        	}
            // repeated edges are not allowed
        	if(edges.contains(e)) {
    			return false;        		        		
        	}
            // remember this edge, and its reverse if this is not a
            // directed graph
        	edges.add(e);
        	if(!this.directed) {
        		edges.add(e.reversed());
        	}
        }
		return true;
	}

	
	/**
	 * See isPath(Path path, String source, String dest)
	 * @throws WcbcException
	 */
	public boolean isPath(Path path) throws WcbcException {
		return isPath(path, null, null);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
