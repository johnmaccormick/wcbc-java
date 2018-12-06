package wcbc;

import java.io.IOException;

/**
 * SISO program Dhc.java
 * 
 * Solves the computational problem DHC. Given an input which is a string
 * representing an unweighted, directed graph, it searches for a directed
 * Hamilton circuit and returns one if it exists.
 * 
 * inString: g where g is a string representation of an unweighted, directed
 * graph (see graph.java).
 * 
 * returns: If a directed Hamilton circuit exists in g, it is returned formatted
 * as a sequence of node names separated by commas. If no such circuit exists,
 * "no" is returned.
 * 
 * Example:
 * 
 * > java wcbc/Dhc "a,b b,c c,a a,d d,b"
 * 
 * "a,d,b,c"
 * 
 */
public class Dhc implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		Graph graph = new Graph(inString, false, true);
		// Convert to an equivalent weighted graph.
		graph.convertToWeighted();

		String newGraphString = graph.toString();
		String cycle = new TspDir().siso(newGraphString);
		return cycle;
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		Dhc dhc = new Dhc();
		String result = dhc.siso(inString);
		System.out.println(result);
	}
	
	
}
