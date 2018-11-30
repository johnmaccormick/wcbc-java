package wcbc;

import java.io.IOException;

/**
 * SISO program TspPath.java
 * 
 * Solves the computational problem TSPPath. Given an input which is a string
 * representing a weighted, undirected graph together with a source node and
 * destination node, it searches for a shortest Hamilton path from source to
 * destination and returns one if it exists.
 * 
 * inString: a string representation of a weighted, undirected graph g (see
 * graph.py), followed by the source and destination nodes separated by
 * semicolons
 * 
 * returns: If a Hamilton path from source to dest exists in g, a shortest such
 * path is returned formatted as a sequence of node names separated by commas.
 * If no such path exists, "no" is returned.
 * 
 * Example:
 * 
 * > java wcbc/TspPath "a,b,5 b,c,6 c,d,8 d,a,9 a,c,1 d,b,2 ; a ; b"
 * 
 * "a,c,d,b"
 */
public class TspPath implements Siso {

	public TspPath() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		String components[] = inString.split(";");
		utils.trimAll(components);
		String graphStr = components[0];
		String source = components[1];
		String dest = components[2];
		Graph graph = new Graph(graphStr, true, false);

		graph.checkContainsNode(source);
		graph.checkContainsNode(dest);

		// If there are two vertices, our conversion won't work correctly,
		// so treat this as a special case.
		if (graph.getNumNodes() == 2) {
			Edge e = new Edge(source, dest);
			if (graph.containsEdge(e)) {
				return e.toString();
			} else {
				return "no";
			}
		}

		// add an overwhelmingly negative edge from source to dest, thus
		// forcing that to be part of a shortest Ham cycle.
		int sumOfWeights = graph.sumEdgeWeights();
		Edge fakeEdge = new Edge(source, dest);
		graph.removeEdge(fakeEdge);
		graph.addEdge(fakeEdge, -sumOfWeights);
		String tspSolnStr = new Tsp().siso(graph.toString());
		if (tspSolnStr.equals("no")) {
			return "no";
		}

		Path tspSoln = Path.fromString(tspSolnStr);
		Path rotatedSoln = tspSoln.rotateToFront(source);
		if(!rotatedSoln.end().equals(dest)) {
	        // Probably oriented the wrong way (or else fakeEdge wasn't
	        // used -- see below). Reverse then rotate source to front
	        // again.
	        rotatedSoln = rotatedSoln.reversed().rotateToFront(source);
		}

	    // If dest still isn't at the end of the cycle, then the orginal graph didn't
	    // have a Ham path from source to dest (but it did have a Ham
	    // cycle -- a cycle that did *not* include fakeEdge).
		if(!rotatedSoln.end().equals(dest)) {
			return "no";			
		}		else {
			return rotatedSoln.toString();
		}
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		TspPath tspPath = new TspPath();
		String result = tspPath.siso(inString);
		System.out.println(result);
	}
	
}
