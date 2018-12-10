package wcbc;

import java.io.IOException;

/**
 * SISO program TspDir.java
 * 
 * Solves the *directed* version of the computational problem TSP. Given an
 * input which is a string representing a weighted, directed graph, it searches
 * for a shortest Hamilton cycle and returns one if it exists.
 * 
 * inString: g where g is a string representation of a weighted, directed graph
 * (see graph.java).
 * 
 * returns: If a Hamilton cycle exists in g, a shortest such cycle is returned
 * formatted as a sequence of node names separated by commas. If no Hamilton
 * cycle exists, "no" is returned.
 * 
 * Example:
 * 
 * > java wcbc/TspDir "a,b,5 b,c,6 c,d,8 d,a,9 a,c,1 d,b,2"
 * 
 * "a,b,c,d"
 * 
 */
public class TspDir implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		Graph graph = new Graph(inString);
		String firstNode = graph.chooseNode();
		if (firstNode == null) {
			// graph contains no vertices. So this is a positive instance -- we return the
			// empty cycle.
			return "";
		}

		PathAndLen pl = shortestCycleWithPrefix(graph, Path.fromString(firstNode), 0);

		if (pl != null) {
			return pl.path.toString();
		} else {
			return "no";
		}
	}


	/**
	 * Find the shortest Hamilton cycle in the given graph that extends the given
	 * prefix. Parameter graph is a Graph object, parameter prefix is a list of
	 * nodes that form a path in the graph, and prefixDistance is the length of that
	 * path. Return value is a 2-tuple consisting of the Hamilton cycle found
	 * together with its total distance, or (None, None) if no such cycle exists.
	 * 
	 * @param graph
	 *            the underlying graph g
	 * @param prefix
	 *            nodes that form a path p in g
	 * @param prefixDistance
	 *            the length of p
	 * @return a 2-tuple consisting of the Hamilton cycle found together with its
	 *         total distance, or null if no such cycle exists
	 * @throws WcbcException
	 */
	private PathAndLen shortestCycleWithPrefix(Graph graph, Path prefix, int prefixDistance) throws WcbcException {
		if (prefix.getLength() < graph.getNumNodes()) {
			// At least one more node must be added to the path. For each
			// node N that can be reached from the final node of prefix, if N
			// is not already in prefix, append N to prefix and call
			// shortestCycleWithPrefix() recursively.
			return tryAllPrefixExtensions(graph, prefix, prefixDistance);
		} else {
			// Recursion has bottomed out -- the prefix path already includes
			// every node, so we complete the cycle back to the first node,
			// and return it (or return None if there is no edge back to the
			// first node)
			return completeCycle(graph, prefix, prefixDistance);
		}
	}

	/**
	 * The parameters are exactly as for shortestCycleWithPrefix() above. If this
	 * path can be completed into a cycle via a single edge from last to first node,
	 * the cycle and its weight are returned as a 2-tuple, otherwise null is
	 * returned.
	 * 
	 * @throws WcbcException
	 */
	static PathAndLen completeCycle(Graph graph, Path prefix, int prefixDistance) throws WcbcException {
		String firstNode = prefix.start();
		String lastNode = prefix.end();
		Edge edgeCompletingCycle = new Edge(lastNode, firstNode);
		if (!graph.containsEdge(edgeCompletingCycle)) {
			return null;
		} else {
			int cycleDistance = prefixDistance + graph.getWeight(edgeCompletingCycle);
			return new PathAndLen(prefix, cycleDistance);
		}
	}

	/**
	 * See shortestCycleWithPrefix() for documentation. The only difference is that
	 * tryAllPrefixExtensions() is invoked with a guarantee that the given path,
	 * prefix, does not yet consist of all nodes so we must try to extend it.
	 * 
	 * @throws WcbcException
	 */
	private PathAndLen tryAllPrefixExtensions(Graph graph, Path prefix, int prefixDistance) throws WcbcException {
		if (graph.getNumNodes() == prefix.getLength()) {
			throw new WcbcException("tryAllPrefixExtensions() received a path that includes every node");
		}
		String lastNode = prefix.end();
		Path bestCycle = null;
		int bestDistance = Integer.MAX_VALUE;
		for (String nextNode : graph.neighbors(lastNode)) {
			if (!prefix.containsNode(nextNode)) {
				Path extendedPrefix = prefix.extend(nextNode);
				int extendedPrefixDistance = prefixDistance + graph.getWeight(new Edge(lastNode, nextNode));
				PathAndLen pl = shortestCycleWithPrefix(graph, extendedPrefix, extendedPrefixDistance);
				if (pl != null) {
					if (pl.len < bestDistance) {
						bestDistance = pl.len;
						bestCycle = pl.path;
					}
				}
			}
		}

		if (bestCycle == null) {
			return null;
		} else {
			return new PathAndLen(bestCycle, bestDistance);
		}
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		TspDir tspDir = new TspDir();
		String result = tspDir.siso(inString);
		System.out.println(result);
	}

}
