package wcbc;

import java.io.IOException;
import java.util.ArrayList;

/**
 * SISO program ConvertDHCtoUHC.java
 * 
 * Convert an instance of directed Hamilton cycle (DHC) into an equivalent
 * instance of undirected Hamilton cycle (UHC).
 * 
 * inString: an instance of DHC. This consists of a directed, unweighted graph.
 * See the textbook or graph.java for details of representing a graph as a
 * string.
 * 
 * returns: an instance of UHC. This consists of an undirected, unweighted graph
 * in string format. The instance is created via the "triplet" construction
 * described in the textbook.
 * 
 * Example:
 * 
 * 
 * > java wcbc/ConvertDHCtoUHC "a,b b,c c,a"
 * 
 * 
 * "aA,aB aA,bC aB,aC aC,cA bA,bB bA,cC bB,bC cA,cB cB,cC"
 */
public class ConvertDHCtoUHC implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		// the original instance
		Graph graph = new Graph(inString, false, true);

		// newG is the converted instance -- create an empty graph using empty string
		Graph newG = new Graph("", false, false);

		// Create all of the nodes of newG -- a triplet of A, B, and C
		// nodes in newG for every node in G. Also create an A-B edge and
		// a B-C edge within each triplet.
		for (String node : graph) {
			// Create triplet.
			String nodeA = node + "A";
			String nodeB = node + "B";
			String nodeC = node + "C";
			String[] newNodes = { nodeA, nodeB, nodeC };
			for (String newNode : newNodes) {
				newG.addNode(newNode);
			}
			// Create A-B and B-C edges within the triplet.
			newG.addEdge(new Edge(nodeA, nodeB));
			newG.addEdge(new Edge(nodeB, nodeC));

		}

		// Create all the remaining edges of newG, i.e., create an undirected
		// A-C edge corresponding to each directed edge in G.
		for (Edge edge : graph.edges()) {
			String node1 = edge.start();
			String node2 = edge.end();
			Edge newEdge = new Edge(node1 + "A", node2 + "C");
			newG.addEdge(newEdge);
		}

		return newG.toString();
	}

	// need this for testing.
	public String revertSolution(String uhcSoln) {
		if (uhcSoln.equals("no")) {
			return "no";
		}
		String[] uhcSolnNodes = utils.splitCheckEmpty(uhcSoln, ",");
	    // extract every third element (i.e., one of each triplet)
		ArrayList<String> nodes = new ArrayList<>();
		for(int i=0; i<uhcSolnNodes.length; i+=3) {
			nodes.add(uhcSolnNodes[i]);
		}
	    // delete final character (the "A", for A-child)
		ArrayList<String> renamedNodes = new ArrayList<>();
		for(String node: nodes) {
			renamedNodes.add(node.substring(0, node.length()-1));
		}

		return utils.join(renamedNodes, ",");
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		ConvertDHCtoUHC conv = new ConvertDHCtoUHC();
		String result = conv.siso(inString);
		System.out.println(result);
	}

}
