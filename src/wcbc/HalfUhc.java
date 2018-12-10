package wcbc;

import java.io.IOException;

/**
 * SISO program HalfUhc.java
 * 
 * Solves the computational problem halfUHC. Given an input which is a string
 * representing an unweighted, undirected graph, it searches for a cycle that
 * visits at least half of the nodes and returns one if it exists.
 * 
 * inString: g where g is a string representation of an unweighted, undirected
 * graph (see graph.java).
 * 
 * returns: If a half Hamilton cycle exists in g, it is returned formatted as a
 * sequence of node names separated by commas. If no such cycle exists, "no" is
 * returned.
 * 
 * Example:
 * 
 * > java wcbc/HalfUhc "a,b b,c c,a a,d d,b"
 * 
 * "a,b,c"
 * 
 */
public class HalfUhc implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		Graph graph = new Graph(inString, false, false);

		// K is the smallest integer that is at least half the number of
		// nodes.
		int K = (graph.getNumNodes() + 1) / 2;

		// If there are two or three vertices, our conversion won't work
		// correctly, so treat this as a special case. In this special case,
		// a self edge produces a half-ham-cycle.
		if (graph.getNumNodes() == 2 || graph.getNumNodes() == 3) {
			for (String node : graph) {
				if (graph.containsEdge(new Edge(node, node))) { // that's a self edge
					return node;
				}
			}
		}
		if (graph.getNumNodes() == 2) {
			return "no";
		}

	    // a cheap hack that happens to make the case of three and four nodes
	    // work correctly
		if (graph.getNumNodes() == 3 || graph.getNumNodes() == 4) {
			K = 3;
		}

	    // Convert to an equivalent weighted, directed graph.
	    Graph newGraph;
		try {
			newGraph = graph.clone();
		} catch (CloneNotSupportedException e) {
			throw new WcbcException("Couldn't clone graph. " + e.getMessage());
		}
	    newGraph.convertToWeighted();
	    newGraph.convertToDirected();

		String newGraphString = newGraph.toString();
		String cycle = new TspDirK().siso(newGraphString+";"+Integer.toString(K));
		return cycle;
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		HalfUhc halfUhc = new HalfUhc();
		String result = halfUhc.siso(inString);
		System.out.println(result);
	}

}
