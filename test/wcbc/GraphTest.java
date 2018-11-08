package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest {

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

	@BeforeEach
	void setUp() throws Exception {
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
		testVals.add(new GraphStrVals("a11,a11,9", null, null,  "a11,a11,9"));
		testVals.add(new GraphStrVals("b,a a,a", false, false, "a,a a,b"));
		testVals.add(new GraphStrVals("b,a,3 a,a,4", true, false, "a,a,4 a,b,3"));
		testVals.add(new GraphStrVals("b,a,3 a,a,4", null, null,  "a,a,4 b,a,3"));
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
		testVals.add(new GraphStrVals("b,a,3 a,,4", null, null,  "exception"));

		
		for (GraphStrVals v : testVals) {
			Graph g = null;
			boolean threwException = false;
			try {
				if(v.weighted==null) {
					g = new Graph(v.desc);
				} else if(v.directed==null) {
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


}
