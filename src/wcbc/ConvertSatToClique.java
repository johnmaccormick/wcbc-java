package wcbc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * SISO program ConvertSatToClique.java
 * 
 * Convert an instance of SAT into an equivalent instance of Clique.
 * 
 * inString: an instance of SAT, formatted as described in the textbook and
 * Sat.java.
 * 
 * returns: an instance of Clique, formatted as described in the textbook.
 * 
 * Example:
 * 
 * > java wcbc/ConvertSatToClique "x1 OR x2 AND NOT x1 OR NOT x2 AND x2"
 * 
 * "x1nC2,x2pC1 x1nC2,x2pC3 x1pC1,x2nC2 x1pC1,x2pC3 x2pC1,x2pC3;3"
 */
public class ConvertSatToClique implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		Sat.CNFformula cnfFormula = Sat.readSat(inString);
		System.out.println("cnfFormula: " + cnfFormula);
		int numClauses = cnfFormula.size();
		Graph graph = new Graph("", false, false);
		for (int clauseID = 0; clauseID < numClauses; clauseID++) {
			Sat.Clause clause = cnfFormula.clauses.get(clauseID);
			// to correspond with textbook's notation, clause IDs start
			// from 1, not 0, so we need "clauseID+1" here:
			String clauseDescriptor = "C" + Integer.toString(clauseID + 1);
			for (String variable : clause.literals.keySet()) {
				int posOrNeg = clause.get(variable);
				if (posOrNeg == 1) {
					graph.addNode(variable + "p" + clauseDescriptor);
				} else if (posOrNeg == -1) {
					graph.addNode(variable + "n" + clauseDescriptor);
				} else { // both pos and neg
					graph.addNode(variable + "p" + clauseDescriptor);
					graph.addNode(variable + "n" + clauseDescriptor);
				}
			}
		}

		for (String node1 : graph) {
			// (literalName1, clauseID1) = node1.rsplit('C', 1)
			int splitIndex = node1.lastIndexOf('C');
			String literalName1 = node1.substring(0, splitIndex);
			String clauseID1 = node1.substring(splitIndex + 1);
			for (String node2 : graph) {
				System.out.println("graph: " + graph);
				System.out.println("considering adding edge between " + node1 + ", " + node2);
				splitIndex = node2.lastIndexOf('C');
				String literalName2 = node2.substring(0, splitIndex);
				String clauseID2 = node2.substring(splitIndex + 1);
				// no edge if in same clause
				if (clauseID1.equals(clauseID2)) {
					continue;
				}
				// no edge if literals are negations of each other
				int len1 = literalName1.length();
				String variableName1 = literalName1.substring(0, len1 - 1);
				String posOrNeg1 = literalName1.substring(len1 - 1);
				int len2 = literalName2.length();
				String variableName2 = literalName2.substring(0, len2 - 1);
				String posOrNeg2 = literalName2.substring(len2 - 1);
				if (variableName1.equals(variableName2) && !posOrNeg1.equals(posOrNeg2)) {
					continue;
				}
				// otherwise, need an edge between the nodes (but skip if
				// already added, which could have happened in the other
				// order since graph is undirected)
				Edge e = new Edge(node1, node2);
				if (!graph.containsEdge(e)) {
					graph.addEdge(e);
				}
			}
		}

		String graphString = graph.toString();
		String k = Integer.toString(numClauses);
		return graphString + ';' + k;
	}

	// need this for testing
	Map<String, Boolean> revertSolution(String cliqueSoln) throws WcbcException {
		if (cliqueSoln.equals("no")) {
			return null;
		}
		String[] nodes = utils.splitCheckEmpty(cliqueSoln, ",");
		// Remove everything after the "C" in each node name
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = nodes[i].substring(0, nodes[i].lastIndexOf('C'));
		}

		// build truth assignment
		Map<String, Boolean> truthAssignment = new HashMap<>();
		for (String node : nodes) {
			String variableName = node.substring(0, node.length() - 1);
			String posNeg = node.substring(node.length() - 1);
			if (posNeg.equals("p")) {
				truthAssignment.put(variableName, true);
			} else if (posNeg.equals("n")) {
				truthAssignment.put(variableName, false);
			} else {
				throw new WcbcException("Unexpected node name" + node);
			}
		}
		return truthAssignment;
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		ConvertSatToClique convertSatToClique = new ConvertSatToClique();
		String result = convertSatToClique.siso(inString);
		System.out.println(result);
	}
}
