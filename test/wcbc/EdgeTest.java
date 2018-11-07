package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import wcbc.EdgeTest.EdgeVals;
import wcbc.PathTest.PathEqualsVals;

class EdgeTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	class EdgeVals {
		String[] nodes;
		String asString;

		public EdgeVals(String[] nodes, String asString) {
			this.nodes = nodes;
			this.asString = asString;
		}
	}

	@Test
	void testEdge() {

		ArrayList<EdgeVals> testVals = new ArrayList<>();
		String[] xArr = { "x" };
		testVals.add(new EdgeVals(xArr, "exception"));
		String[] xyArr = { "x", "y" };
		testVals.add(new EdgeVals(xyArr, "x,y"));
		String[] xyzArr = { "x", "y", "z" };
		testVals.add(new EdgeVals(xyzArr, "exception"));

		for (EdgeVals v : testVals) {
			Edge p = null;
			boolean threwException = false;
			try {
				p = new Edge(v.nodes);
			} catch (WcbcException e) {
				if (!v.asString.equals("exception")) {
					fail("edge creation should have generated an exception");
				}
				threwException = true;
			}
			if (v.asString.equals("exception")) {
				assertTrue(threwException);
			} else {
				assertEquals(v.asString, p.toString());
			}
		}

	}

	class PathEqualsVals {
		Object o1;
		Object o2;
		boolean eq;

		public PathEqualsVals(Object o1, Object o2, boolean eq) {
			this.o1 = o1;
			this.o2 = o2;
			this.eq = eq;
		}
	}

	@Test
	void testEdgeEquals() throws WcbcException {

		ArrayList<PathEqualsVals> testVals = new ArrayList<>();
		Edge e1 = new Edge("x,y".split(","));
		Edge e2 = new Edge("x,y".split(","));
		Edge e3 = new Edge("y,x".split(","));
		Path p1 = new Path("x,y".split(","));
		Path p2 = new Path("x,y".split(","));
		Path p3 = new Path("x,y,z".split(","));
		testVals.add(new PathEqualsVals(e1, e2, true));
		testVals.add(new PathEqualsVals(e1, p1, true));
		testVals.add(new PathEqualsVals(e2, p2, true));
		testVals.add(new PathEqualsVals(e1, p2, true));
		testVals.add(new PathEqualsVals(p1, p2, true));
		testVals.add(new PathEqualsVals(e1, e3, false));
		testVals.add(new PathEqualsVals(p1, e3, false));
		testVals.add(new PathEqualsVals(e1, p3, false));
		testVals.add(new PathEqualsVals(p1, p3, false));

		for (PathEqualsVals v : testVals) {
			if (v.eq) {
				assertTrue(v.o1.equals(v.o2));
				assertTrue(v.o2.equals(v.o1));
			} else {
				assertFalse(v.o1.equals(v.o2));
				assertFalse(v.o2.equals(v.o1));
			}
		}

	}

}
