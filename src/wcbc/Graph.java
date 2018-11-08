package wcbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
public class Graph {

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

}
