package wcbc;

import java.io.IOException;
import java.util.Set;

/**
 * SISO program ConvertUHCtoDHC.java
 * 
 * Convert an instance of undirected Hamilton cycle (UHC) into an equivalent
 * instance of directed Hamilton cycle (DHC).
 * 
 * inString: an instance of UHC. This consists of an undirected, unweighted
 * graph. See the textbook or graph.java for details of representing a graph as
 * a string.
 * 
 * returns: an instance of DHC. This consists of a directed, unweighted graph in
 * string format. The instance is created via the construction described in the
 * textbook, in which each undirected edge is replaced by two directed edges.
 * 
 * Example:
 * 
 * > java wcbc/ConvertUHCtoDHC "a,b b,c c,d d,a"
 * 
 * "a,b a,d b,a b,c c,b c,d d,a d,c"
 * 
 */
public class ConvertUHCtoDHC implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		// the original instance
		Graph graph = new Graph(inString, false, false);

		// newG is the converted instance -- create an empty graph using empty string
		Graph newG = new Graph("", false, true);

		// add all nodes of graph to newG
		for (String node : graph) {
			newG.addNode(node);
		}

		Set<Edge> undirectedEdges = graph.edges();
		// special case: single edge gets converted to single edge
		if (undirectedEdges.size() == 1) {
			// extract the one and only edge using a for loop
			for (Edge edge : undirectedEdges) {
				newG.addEdge(edge);
				return newG.toString();
			}
		}

		// add all original edges and their reverse to the new graph
		for (Edge edge : undirectedEdges) {
			// New edge is the reverse of the old one.
			Edge newEdge = edge.reversed();
			// Add original and reversed edge to the new graph
			newG.addEdge(edge);
			newG.addEdge(newEdge);
		}
		return newG.toString();
	}

	// need this for testing. In fact, reverting the solution is trivial
	// for this conversion.
	public String revertSolution(String dhcSoln) {
		return dhcSoln;
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		ConvertUHCtoDHC conv = new ConvertUHCtoDHC();
		String result = conv.siso(inString);
		System.out.println(result);
	}
}
