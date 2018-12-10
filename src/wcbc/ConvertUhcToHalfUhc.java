package wcbc;

import java.io.IOException;
import java.util.ArrayList;

/**
 * SISO program ConvertUhcToHalfUhc.java
 * 
 * Convert an instance of undirected Hamilton cycle (UHC) into an equivalent
 * instance of half UHC.
 * 
 * inString: an instance of UHC. This consists of an undirected, unweighted
 * graph. See the textbook or graph.java for details of representing a graph as
 * a string.
 * 
 * returns: an instance of half UHC. This consists of an undirected, unweighted
 * graph in string format, which is essentially two copies of the original
 * input.
 * 
 * Example:
 * 
 * > java wcbc/ConvertUhcToHalfUhc "a,b b,c c,a"
 * 
 * "aA,bA aA,cA aB,bB aB,cB bA,cA bB,cB"
 * 
 */
public class ConvertUhcToHalfUhc implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		// the original instance G
		Graph graph = new Graph(inString, false, false);

		// newG is the converted instance -- create an empty graph using empty string
		Graph newG = new Graph("", false, false);

		// Create all of the nodes of newG -- a pair of ``twin"" A-, and B-
		// nodes in newG for every node in G.
		for (String node : graph) {
			// Create twins.
			String nodeA = node + "A";
			String nodeB = node + "B";
			String[] newNodes = { nodeA, nodeB};
			for (String newNode : newNodes) {
				newG.addNode(newNode);
			}
		}

	    // Create all the remaining edges of newG, i.e., create twin edges
	    // corresponding to each edge in G.
		for (Edge edge : graph.edges()) {
			String node1 = edge.start();
			String node2 = edge.end();
			Edge newEdgeA = new Edge(node1 + "A", node2 + "A");
			Edge newEdgeB = new Edge(node1 + "B", node2 + "B");
			newG.addEdge(newEdgeA);
			newG.addEdge(newEdgeB);
		}

		return newG.toString();
	}

	// need this for testing.
	public String revertSolution(String halfUhcSoln) {
		if (halfUhcSoln.equals("no")) {
			return "no";
		}
		String[] halfUhcSolnNodes = utils.splitCheckEmpty(halfUhcSoln, ",");
	    // delete final character (the 'A' or 'B' that was added)
		ArrayList<String> renamedNodes = new ArrayList<>();
		for(String node: halfUhcSolnNodes) {
			renamedNodes.add(node.substring(0, node.length()-1));
		}

		return utils.join(renamedNodes, ",");
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		ConvertUhcToHalfUhc conv = new ConvertUhcToHalfUhc();
		String result = conv.siso(inString);
		System.out.println(result);
	}

	
	
}
