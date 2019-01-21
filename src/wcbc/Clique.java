package wcbc;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * SISO program Clique.java
 * 
 * Solves the computational problem Clique. Given an input in the form "graph ;
 * K", it searches for a clique of size K and returns such a clique if it
 * exists.
 * 
 * inString: "g ; K" where g is a string representation of an unweighted,
 * undirected graph (see Graph.java) and K is an integer.
 * 
 * returns: If a clique of size K exists in g, it is returned formatted as a
 * sequence of node names separated by commas. If no such clique exists, "no" is
 * returned.
 * 
 * Example:
 * 
 * > java wcbc/Clique "a,b a,c a,d b,c b,d c,d c,e b,e d,e ; 4"
 * 
 * "d,e,b,c" [or another 4-clique]
 * 
 */
public class Clique implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		String[] components = inString.split(";");
		utils.trimAll(components);
		String graphString = components[0];
		String KString = components[1];
		Graph G = new Graph(graphString, false, false);
		int K = Integer.parseInt(KString);
		// special case: if K=0, we have a positive solution but it's the empty string
		if (K == 0) {
			return "";
		}

		Set<String> nodes = G.getNodesAsSet();
		Set<String> emptyClique = new HashSet<>();
		Set<String> clique = tryExtendClique(G, K, emptyClique, nodes);
		if (clique != null) {
			return utils.join(clique, ",");
		} else {
			return "no";
		}
	}

	/**
	 * Return True if adding newNode produces a new clique, and otherwise return
	 * False.
	 * 
	 * @param graph
	 *            Underlying graph in which we seek a clique
	 * @param clique
	 *            a clique in the graph; each of the strings in the set should be
	 *            the name of a node in the graph
	 * @param newNode
	 *            the name of a node in the graph
	 * @return Return True if adding newNode to clique produces a new clique in
	 *         graph, and otherwise return False.
	 * @throws WcbcException
	 */
	boolean extendsClique(Graph graph, Set<String> clique, String newNode) throws WcbcException {
		for (String node : clique) {
			if (!graph.containsEdge(new Edge(node, newNode))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Attempt to extend the given clique to a clique of size K.
	 * 
	 * If the given clique can be extended to a clique of size K by adding nodes
	 * from the remainingNodes, return such a clique. Otherwise, return null.
	 * 
	 * @param graph
	 *            Underlying graph in which we seek a clique
	 * @param K
	 *            the size of the clique we are trying to create by extending the
	 *            current clique
	 * @param clique
	 *            a clique in the graph; each of the strings in the set should be
	 *            the name of a node in the graph
	 * @param remainingNodes
	 *            a set of nodes from which we can choose in order to extend the
	 *            clique
	 * @return
	 * @throws WcbcException
	 */
	Set<String> tryExtendClique(Graph graph, int K, Set<String> clique, Set<String> remainingNodes)
			throws WcbcException {
		if (clique.size() == K) {
			return clique;
		} else if (remainingNodes.size() == 0) {
			return null;
		} else {
			// pick a remaining element
			String nextNode = remainingNodes.iterator().next();
			// create new set of remaining nodes with the chosen element eliminated
			Set<String> newRemainingNodes = new HashSet<>(remainingNodes);
			newRemainingNodes.remove(nextNode);

			if (extendsClique(graph, clique, nextNode)) {
				Set<String> newClique = new HashSet<>(clique);
				newClique.add(nextNode);
				Set<String> extendedClique = tryExtendClique(graph, K, newClique, newRemainingNodes);
				if (extendedClique != null) {
					return extendedClique;
				}
			}
			return tryExtendClique(graph, K, clique, newRemainingNodes);
		}
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		Clique clique = new Clique();
		String result = clique.siso(inString);
		System.out.println(result);
	}

}
