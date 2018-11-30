package wcbc;

import java.io.IOException;

/**
 * SISO program Tsp.java
 * 
 * Solves the computational problem TSP. Given an input which is a string
 * representing a weighted, undirected graph, it searches for a shortest
 * Hamilton cycle and returns one if it exists.
 * 
 * inString: g where g is a string representation of a weighted, undirected
 * graph (see graph.java).
 * 
 * returns: If a Hamilton cycle exists in g, a shortest such cycle is returned
 * formatted as a sequence of node names separated by commas. If no Hamilton
 * cycle exists, "no" is returned.
 * 
 * Example:
 * 
 * > java wcbc/Tsp "a,b,5 b,c,6 c,d,8 d,a,9 a,c,1 d,b,2"
 * 
 * "a,b,d,c"
 * 
 */
public class Tsp implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		Graph graph = new Graph(inString, true, false);

		// If there are two vertices, our conversion won't work correctly,
		// so treat this as a special case.
		if (graph.getNumNodes() == 2) {
			return "no";
		}

	    // Create an equivalent weighted directed graph.
		Graph equivalentGraph;
		try {
			equivalentGraph = graph.clone();
		} catch (CloneNotSupportedException e) {
			throw new WcbcException("Couldn't clone graph. " + e.getMessage());
		}
	    equivalentGraph.convertToWeighted();
	    equivalentGraph.convertToDirected();
		
		String cycle = new TspDir().siso(equivalentGraph.toString());
		return cycle;
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		Tsp tsp = new Tsp();
		String result = tsp.siso(inString);
		System.out.println(result);
	}
	
}
