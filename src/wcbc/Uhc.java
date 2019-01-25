package wcbc;

import java.io.IOException;

/**
 * SISO program Uhc.java
 * 
 * Solves the computational problem UHC. Given an input which is a string
 * representing an unweighted, undirected graph, it searches for a Hamilton
 * cycle and returns one if it exists.
 * 
 * inString: g where g is a string representation of an unweighted, undirected
 * graph (see graph.java).
 * 
 * returns: If a Hamilton cycle exists in g, it is returned formatted as a
 * sequence of node names separated by commas. If no such cycle exists, "no" is
 * returned.
 * 
 * Example: 
 *
 * > java wcbc/Uhc "a,b b,c c,a a,d d,b"
 * 
 * "a,c,b,d"
 * 
 */
public class Uhc implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		Graph graph = new Graph(inString, false, false);

		// If there are two vertices, our conversion won't work correctly,
		// so treat this as a special case.
		if (graph.getNumNodes() == 2) {
			return "no";
		}

		// Convert to an equivalent weighted graph.
		graph.convertToWeighted();

		String newGraphString = graph.toString();
		String cycle = new Tsp().siso(newGraphString);
		return cycle;
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		Uhc uhc = new Uhc();
		String result = uhc.siso(inString);
		System.out.println(result);
	}

}
