package wcbc;

import java.io.IOException;

/**
 * SISO program TspDirK.java
 * 
 * Solves a variant of TSP: we seek a directed shortest cycle that incorporates
 * at least K nodes. That is, given an input which is a string representing (i)
 * a weighted, directed graph and (ii) an integer K, tspDir searches for a
 * shortest directed cycle containing at least K nodes and returns one if it
 * exists.
 * 
 * inString: g where g is a string representation of a weighted, directed graph
 * (see graph.java), followed by a semicolon, followed by K.
 * 
 * returns: If a directed cycle containing at least K nodes exists in g, a
 * shortest such cycle is returned formatted as a sequence of node names
 * separated by commas. If no such cycle exists, "no" is returned.
 * 
 * Example:
 * 
 * > java wcbc/TspDirK "a,b,4 b,a,9 b,c,6 c,a,10;3"
 * 
 * "a,b,c"
 * 
 */
public class TspDirK implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		String components[] = inString.split(";");
		utils.trimAll(components);
		String graphStr = components[0];
		int K = Integer.parseInt(components[1]);
		// treat K=0 as a special case. Since the empty cycle satisfies
		// this, we return a positive solution which is the empty string.
		if (K == 0) {
			return "";
		}

		Graph graph = new Graph(graphStr);

		Path bestCycle = null;
		int bestDistance = Integer.MAX_VALUE;

		for (String firstNode : graph) {
			PathAndLen pl = shortestKCycleWithPrefix(graph, K, Path.fromString(firstNode), 0);
			if (pl != null) {
				if (pl.len < bestDistance) {
					bestDistance = pl.len;
					bestCycle = pl.path;
				}
			}
		}

		if (bestCycle != null) {
			return bestCycle.toString();
		} else {
			return "no";
		}
	}

	// See tspDir.java for documentation on shortestCycleWithPrefix, which is
	// analogous to this function except we permit cycles to have K or more
	// nodes.
	private PathAndLen shortestKCycleWithPrefix(Graph graph, int K, Path prefix, int prefixDistance)
			throws WcbcException {
		PathAndLen pl1 = null;
		PathAndLen pl2 = null;
		if (prefix.getLength() >= K) {
			pl1 = TspDir.completeCycle(graph, prefix, prefixDistance);
		}

		pl2 = tryAllKPrefixExtensions(graph, K, prefix, prefixDistance);
		if (pl1 == null && pl2 == null) {
			return null;
		} else if (pl1 == null && pl2 != null) {
			return pl2;
		} else if (pl1 != null && pl2 == null) {
			return pl1;
		} else if (pl1.len < pl2.len) {
			return pl1;
		} else {
			return pl2;
		}
	}

	// See tspDir.java for documentation on tryAllPrefixExtensions, which is
	// analogous to this function except we permit cycles to have K or more
	// nodes.
	private PathAndLen tryAllKPrefixExtensions(Graph graph, int K, Path prefix, int prefixDistance)
			throws WcbcException {
		if (graph.getNumNodes() == prefix.getLength()) {
			return null;
		}

		String lastNode = prefix.end();
		PathAndLen bestPl = null;

		for (String nextNode : graph.neighbors(lastNode)) {
			if (!prefix.containsNode(nextNode)) {
				Path extendedPrefix = prefix.extend(nextNode);
				int extendedPrefixDistance = prefixDistance + graph.getWeight(new Edge(lastNode, nextNode));
				PathAndLen pl = shortestKCycleWithPrefix(graph, K, extendedPrefix, extendedPrefixDistance);
				if (pl != null) {
					if (bestPl == null || pl.len < bestPl.len) {
						bestPl = pl;
					}
				}
			}
		}
		return bestPl;
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		TspDirK tspDirK = new TspDirK();
		String result = tspDirK.siso(inString);
		System.out.println(result);
	}

}
