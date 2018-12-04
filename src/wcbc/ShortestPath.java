package wcbc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * SISO program ShortestPath.java
 * 
 * Solves the computational problem ShortestPath. The algorithm employed is
 * Bellman-Ford. It is in O(numVertices * numEdges), hence certainly O(n^2),
 * where n is the length of the string input.
 * 
 * inString: consists of a weighted, undirected graph G (in the ASCII format
 * described in the textbook) followed by source vertex v and destination vertex
 * w. G, v, and w are separated by semicolons and optional whitespace.
 * 
 * returns: The length of the shortest path from v to w in G, or "no" if there
 * is no such path.
 * 
 * Example:
 * 
 * > java wcbc/ShortestPath "a,b,5 b,c,6 c,d,8 d,a,9 a,c,1 d,b,2 ; a; d"
 * 
 * "7"
 */
public class ShortestPath implements Siso {

	class ShortestAndPredMaps {
		HashMap<String, Integer> shortest = new HashMap<>();
		HashMap<String, String> predecessor = new HashMap<>();

		public ShortestAndPredMaps(HashMap<String, Integer> shortest, HashMap<String, String> predecessor) {
			this.shortest = shortest;
			this.predecessor = predecessor;
		}
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

		// Bellman-Ford works on directed graphs.
		graph.convertToDirected();

		// This will give us the shortest path lengths to every vertex, and
		// the paths themselves.
		ShortestAndPredMaps maps = bellmanFord(graph, source);

		// Of course we wanted the distance only to the destination, so
		// that is what we return.
		int dist = maps.shortest.get(dest);
		if (dist == Integer.MAX_VALUE) {
			return "no";
		} else {
			return Integer.toString(dist);
		}
	}

	// copied from page 86 of Cormen, Algorithms Unlocked. See that book
	// for detailed documentation.
	// For simplicity, we treat Integer.MAX_VALUE as a special value that represents
	// positive infinity. We assume the actual values encountered will not come
	// close to Integer.MAX_VALUE. Otherwise this method will silently overflow and
	// produce incorrect results.
	private void relax(String u, String v, int weight, HashMap<String, Integer> shortest,
			HashMap<String, String> pred) {
		// treat Integer.MAX_VALUE as infinite
		int newDist = Integer.MAX_VALUE;
		int u_val = shortest.get(u);
		if (u_val < Integer.MAX_VALUE) {
			newDist = u_val + weight;
		}
		if (newDist < shortest.get(v)) {
			shortest.put(v, newDist);
			pred.put(v, u);
		}

	}

	// copied from page 102 of Cormen, Algorithms Unlocked. See that book
	// for detailed documentation.
	// For simplicity, we treat Integer.MAX_VALUE as a special value that represents
	// positive infinity. We assume the actual values encountered will not come
	// close to Integer.MAX_VALUE. Otherwise this method will silently overflow and
	// produce incorrect results.
	private ShortestAndPredMaps bellmanFord(Graph graph, String source) throws WcbcException {
		HashMap<String, Integer> shortest = new HashMap<>();
		HashMap<String, String> pred = new HashMap<>();
		for (String v : graph.getNodesAsSet()) {
			shortest.put(v, Integer.MAX_VALUE);
			pred.put(v, null);
		}
		shortest.put(source, 0);
		Map<Edge, Integer> edges = graph.getEdgesAsDict();
		Edge edge = null;
		int weight = -1;
		String u = null;
		String v = null;
		for (int i = 0; i < graph.getNumNodes() - 1; i++) {
			for (Entry<Edge, Integer> entry : edges.entrySet()) {
				edge = entry.getKey();
				weight = entry.getValue();
				u = edge.start();
				v = edge.end();
				relax(u, v, weight, shortest, pred);
			}
		}
		return new ShortestAndPredMaps(shortest, pred);
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		ShortestPath shortestPath = new ShortestPath();
		String result = shortestPath.siso(inString);
		System.out.println(result);
	}

}
