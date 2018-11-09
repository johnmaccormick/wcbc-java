package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	class GraphStrVals {
		String desc;
		Boolean weighted;
		Boolean directed;
		String asString;

		public GraphStrVals(String desc, Boolean weighted, Boolean directed, String asString) {
			super();
			this.desc = desc;
			this.weighted = weighted;
			this.directed = directed;
			this.asString = asString;
		}
	}

	@Test
	void testGraphStr() {

		ArrayList<GraphStrVals> testVals = new ArrayList<>();
		testVals.add(new GraphStrVals("", null, null, ""));
		testVals.add(new GraphStrVals("", false, null, ""));
		testVals.add(new GraphStrVals("", false, false, ""));
		testVals.add(new GraphStrVals("a", null, null, "a"));
		testVals.add(new GraphStrVals("a,a", false, null, "a,a"));
		testVals.add(new GraphStrVals("a,b", false, null, "a,b"));
		testVals.add(new GraphStrVals("a,b c", false, null, "a,b c"));
		testVals.add(new GraphStrVals("x,z x,y c", false, null, "c x,y x,z"));
		testVals.add(new GraphStrVals("x,z dd x x,y cc", false, null, "cc dd x,y x,z"));
		testVals.add(new GraphStrVals("a,a", false, false, "a,a"));
		testVals.add(new GraphStrVals("a,b", false, false, "a,b"));
		testVals.add(new GraphStrVals("b,a", false, false, "a,b"));
		testVals.add(new GraphStrVals("a,b,5", null, null, "a,b,5"));
		testVals.add(new GraphStrVals("a11,a11", false, null, "a11,a11"));
		testVals.add(new GraphStrVals("a11,a11,9", null, null, "a11,a11,9"));
		testVals.add(new GraphStrVals("b,a a,a", false, false, "a,a a,b"));
		testVals.add(new GraphStrVals("b,a,3 a,a,4", true, false, "a,a,4 a,b,3"));
		testVals.add(new GraphStrVals("b,a,3 a,a,4", null, null, "a,a,4 b,a,3"));
		testVals.add(new GraphStrVals("bb,a,3 a,a,4 a,ccc,5 bb,d,6", true, true, "a,a,4 a,ccc,5 bb,a,3 bb,d,6"));
		testVals.add(new GraphStrVals("bb,a,3 a,a,4 a,ccc,5 bb,d,6", true, false, "a,a,4 a,bb,3 a,ccc,5 bb,d,6"));
		testVals.add(new GraphStrVals("bb,a a,a a,ccc bb,d", false, true, "a,a a,ccc bb,a bb,d"));
		testVals.add(new GraphStrVals("bb,a a,a a,ccc bb,d", false, false, "a,a a,bb a,ccc bb,d"));
		testVals.add(new GraphStrVals("bb,a a,a a,ccc a,bb bb,d", false, false, "exception"));
		testVals.add(new GraphStrVals("bb,a a,a a,ccc bb,a bb,d", false, false, "exception"));
		testVals.add(new GraphStrVals("bb,a a,a a,ccc a,bb bb,d", false, true, "a,a a,bb a,ccc bb,a bb,d"));
		testVals.add(new GraphStrVals("bb,a a,a a,ccc bb,a bb,d", false, true, "exception"));
		testVals.add(new GraphStrVals("bb,a a,a a,ccc a,bb,4 bb,d", false, false, "exception"));
		testVals.add(new GraphStrVals("bb,a,1 a,a,1 a,ccc a,bb,4 bb,d,5", true, false, "exception"));
		testVals.add(new GraphStrVals("b,a,3 a,,4", null, null, "exception"));
		testVals.add(new GraphStrVals("b,a,3 a,a,4 c", true, false, "a,a,4 a,b,3 c"));

		for (GraphStrVals v : testVals) {
			Graph g = null;
			boolean threwException = false;
			try {
				if (v.weighted == null) {
					g = new Graph(v.desc);
				} else if (v.directed == null) {
					g = new Graph(v.desc, v.weighted);
				} else {
					g = new Graph(v.desc, v.weighted, v.directed);
				}
			} catch (WcbcException e) {
				threwException = true;
			}
			if (v.asString.equals("exception") && threwException) {
				continue;
			}
			String pStr = g.toString();
			assertEquals(v.asString, pStr);
		}
	}

	class GraphContainsVals {
		String desc;
		Boolean weighted;
		Boolean directed;
		String node;
		Boolean isContained;

		public GraphContainsVals(String desc, Boolean weighted, Boolean directed, String node, Boolean isContained) {
			super();
			this.desc = desc;
			this.weighted = weighted;
			this.directed = directed;
			this.node = node;
			this.isContained = isContained;
		}
	}

	@Test
	void testGraphContains() throws WcbcException {

		ArrayList<GraphContainsVals> testVals = new ArrayList<>();
		testVals.add(new GraphContainsVals("", null, null, "a", false));
		testVals.add(new GraphContainsVals("", false, null, "a", false));
		testVals.add(new GraphContainsVals("", false, false, "a", false));
		testVals.add(new GraphContainsVals("a", null, null, "a", true));
		testVals.add(new GraphContainsVals("a", null, null, "b", false));
		testVals.add(new GraphContainsVals("a,a", false, null, "a", true));
		testVals.add(new GraphContainsVals("a,b", false, null, "b", true));
		testVals.add(new GraphContainsVals("a,b c", false, null, "a", true));
		testVals.add(new GraphContainsVals("a,b c", false, null, "b", true));
		testVals.add(new GraphContainsVals("a,b c", false, null, "c", true));
		testVals.add(new GraphContainsVals("a,b c", false, null, "d", false));
		testVals.add(new GraphContainsVals("x,z x,y c", false, null, "d", false));
		testVals.add(new GraphContainsVals("x,z dd x x,y cc", false, null, "dd", true));
		testVals.add(new GraphContainsVals("a,a", false, false, "c", false));
		testVals.add(new GraphContainsVals("a,b", false, false, "c", false));
		testVals.add(new GraphContainsVals("b,a", false, false, "a", true));
		testVals.add(new GraphContainsVals("a,b,5", null, null, "ab", false));
		testVals.add(new GraphContainsVals("a11,a11", false, null, "a1", false));
		testVals.add(new GraphContainsVals("a11,a11,9", null, null, "a11", true));
		testVals.add(new GraphContainsVals("bb,a,1 a,a,1 a,ccc,3 a,bbb,4 bb,d,5", true, false, "ccc", true));
		testVals.add(new GraphContainsVals("bb,a,1 a,a,1 a,ccc,3 a,bbb,4 bb,d,5", true, false, "cc", false));

		for (GraphContainsVals v : testVals) {
			Graph g = null;
			if (v.weighted == null) {
				g = new Graph(v.desc);
			} else if (v.directed == null) {
				g = new Graph(v.desc, v.weighted);
			} else {
				g = new Graph(v.desc, v.weighted, v.directed);
			}
			assertEquals(v.isContained, g.containsNode(v.node));
		}
	}

	class GraphAddNodeVals {
		String desc;
		Boolean weighted;
		Boolean directed;
		String node;
		String asString;

		public GraphAddNodeVals(String desc, Boolean weighted, Boolean directed, String node, String asString) {
			this.desc = desc;
			this.weighted = weighted;
			this.directed = directed;
			this.node = node;
			this.asString = asString;
		}

	}

	@Test
	void testAddNode() throws WcbcException {

		ArrayList<GraphAddNodeVals> testVals = new ArrayList<>();
		testVals.add(new GraphAddNodeVals("", null, null, "a", "a"));
		testVals.add(new GraphAddNodeVals("a", null, null, "a", "exception"));
		testVals.add(new GraphAddNodeVals("", false, null, "a", "a"));
		testVals.add(new GraphAddNodeVals("", false, false, "a", "a"));
		testVals.add(new GraphAddNodeVals("a,a", false, null, "b", "a,a b"));
		testVals.add(new GraphAddNodeVals("a,a", false, null, "a", "exception"));
		testVals.add(new GraphAddNodeVals("a,b", false, null, "a", "exception"));
		testVals.add(new GraphAddNodeVals("b,a,3 a,a,4", true, false, "c", "a,a,4 a,b,3 c"));
		testVals.add(
				new GraphAddNodeVals("b,a,3 a,a,4 a,ccc,5 b,d,6", null, null, "aaa", "a,a,4 a,ccc,5 aaa b,a,3 b,d,6"));

		for (GraphAddNodeVals v : testVals) {
			Graph g = null;
			if (v.weighted == null) {
				g = new Graph(v.desc);
			} else if (v.directed == null) {
				g = new Graph(v.desc, v.weighted);
			} else {
				g = new Graph(v.desc, v.weighted, v.directed);
			}

			boolean threwException = false;
			try {
				g.addNode(v.node);
			} catch (WcbcException e) {
				threwException = true;
			}

			if (v.asString.equals("exception") && threwException) {
				continue;
			}

			assertEquals(v.asString, g.toString());
		}
	}

	class GraphCloneVals {
		String desc;
		Boolean weighted;
		Boolean directed;
		String asString;

		public GraphCloneVals(String desc, Boolean weighted, Boolean directed, String asString) {
			super();
			this.desc = desc;
			this.weighted = weighted;
			this.directed = directed;
			this.asString = asString;
		}
	}

	@Test
	void testGraphClone() throws WcbcException, CloneNotSupportedException {

		ArrayList<GraphCloneVals> testVals = new ArrayList<>();
		testVals.add(new GraphCloneVals("", null, null, ""));
		testVals.add(new GraphCloneVals("", false, null, ""));
		testVals.add(new GraphCloneVals("", false, false, ""));
		testVals.add(new GraphCloneVals("a", null, null, "a"));
		testVals.add(new GraphCloneVals("a,a", false, null, "a,a"));
		testVals.add(new GraphCloneVals("a,b", false, null, "a,b"));
		testVals.add(new GraphCloneVals("a,b c", false, null, "a,b c"));
		testVals.add(new GraphCloneVals("x,z x,y c", false, null, "c x,y x,z"));
		testVals.add(new GraphCloneVals("x,z dd x x,y cc", false, null, "cc dd x,y x,z"));
		testVals.add(new GraphCloneVals("a,a", false, false, "a,a"));
		testVals.add(new GraphCloneVals("a,b", false, false, "a,b"));
		testVals.add(new GraphCloneVals("b,a", false, false, "a,b"));
		testVals.add(new GraphCloneVals("a,b,5", null, null, "a,b,5"));
		testVals.add(new GraphCloneVals("a11,a11", false, null, "a11,a11"));
		testVals.add(new GraphCloneVals("a11,a11,9", null, null, "a11,a11,9"));
		testVals.add(new GraphCloneVals("b,a a,a", false, false, "a,a a,b"));
		testVals.add(new GraphCloneVals("b,a,3 a,a,4", true, false, "a,a,4 a,b,3"));
		testVals.add(new GraphCloneVals("b,a,3 a,a,4", null, null, "a,a,4 b,a,3"));
		testVals.add(new GraphCloneVals("bb,a,3 a,a,4 a,ccc,5 bb,d,6", true, true, "a,a,4 a,ccc,5 bb,a,3 bb,d,6"));
		testVals.add(new GraphCloneVals("bb,a,3 a,a,4 a,ccc,5 bb,d,6", true, false, "a,a,4 a,bb,3 a,ccc,5 bb,d,6"));
		testVals.add(new GraphCloneVals("bb,a a,a a,ccc bb,d", false, true, "a,a a,ccc bb,a bb,d"));
		testVals.add(new GraphCloneVals("bb,a a,a a,ccc bb,d", false, false, "a,a a,bb a,ccc bb,d"));
		testVals.add(new GraphCloneVals("bb,a a,a a,ccc a,bb bb,d", false, true, "a,a a,bb a,ccc bb,a bb,d"));
		testVals.add(new GraphCloneVals("b,a,3 a,a,4 c", true, false, "a,a,4 a,b,3 c"));

		for (GraphCloneVals v : testVals) {
			Graph g = null;
			if (v.weighted == null) {
				g = new Graph(v.desc);
			} else if (v.directed == null) {
				g = new Graph(v.desc, v.weighted);
			} else {
				g = new Graph(v.desc, v.weighted, v.directed);
			}
			Graph g2 = g.clone();
			assertEquals(g.toString(), g2.toString());
		}
	}

	@Test
	void testIter() throws WcbcException {
		String gStr = "b,a,3 a,a,4 a,ccc,5 b,d,6";
		String[] gNodes = {"a", "b", "ccc", "d"}; 
		Graph g = new Graph(gStr);
		List<String> nodes = new ArrayList<>();
		for(String node: g) {
			nodes.add(node);
		}
		List<String> gNodesList = Arrays.asList(gNodes);
		Collections.sort(nodes);
		Collections.sort(gNodesList);
		assertEquals(nodes, gNodesList);
	}
	
}
