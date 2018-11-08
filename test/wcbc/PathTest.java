package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PathTest {

	class PathStrVals {
		String[] nodes;
		String asString;

		public PathStrVals(String[] nodes, String asString) {
			this.nodes = nodes;
			this.asString = asString;
		}
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testPathStr() {

		String[] emptyArray = {};
		ArrayList<PathStrVals> testVals = new ArrayList<>();
		testVals.add(new PathStrVals(emptyArray, ""));
		String[] arrayWithEmptyStr = { "" };
		testVals.add(new PathStrVals(arrayWithEmptyStr, "exception"));
		String[] a1Array = { "a1" };
		testVals.add(new PathStrVals(a1Array, "a1"));
		String[] ab1Array = { "ab1" };
		testVals.add(new PathStrVals(ab1Array, "ab1"));
		String[] twoEltArray = { "ab1", "zxy" };
		testVals.add(new PathStrVals(twoEltArray, "ab1,zxy"));
		String[] abcArray = "a,b,c".split(",");
		testVals.add(new PathStrVals(abcArray, "a,b,c"));
		String[] abcEmptyArray = "a,b,,c".split(",");
		testVals.add(new PathStrVals(abcEmptyArray, "exception"));
		String[] a1b1c1Array = "a1,b1,c1".split(",");
		testVals.add(new PathStrVals(a1b1c1Array, "a1,b1,c1"));

		for (PathStrVals v : testVals) {
			Path p = null;
			boolean threwException = false;
			try {
				p = new Path(v.nodes);
			} catch (WcbcException e) {
				threwException = true;
			}
			if (v.asString.equals("exception") && threwException) {
				continue;
			}
			String pStr = p.toString();
			assertEquals(v.asString, pStr);
		}
	}

	class PathReverseVals {
		String[] nodes;
		Path path;

		public PathReverseVals(String[] nodes, Path path) {
			this.nodes = nodes;
			this.path = path;
		}
	}

	@Test
	void testPathReverse() throws WcbcException {

		ArrayList<PathReverseVals> testVals = new ArrayList<>();
		String[] emptyArray = {};
		testVals.add(new PathReverseVals(emptyArray, new Path(emptyArray)));
		String[] oneArray = { "1" };
		testVals.add(new PathReverseVals(oneArray, new Path(oneArray)));
		String[] twoArray = { "1", "2" };
		String[] twoArrayRev = { "2", "1" };
		testVals.add(new PathReverseVals(twoArray, new Path(twoArrayRev)));
		testVals.add(new PathReverseVals("a,b,c".split(","), new Path("c,b,a".split(","))));
		testVals.add(new PathReverseVals("a1,b1,c1".split(","), new Path("c1,b1,a1".split(","))));
		testVals.add(new PathReverseVals("a1,b1,c1,d1".split(","), new Path("d1,c1,b1,a1".split(","))));

		for (PathReverseVals v : testVals) {
			Path p = new Path(v.nodes);
			Path revP = p.reversed();
			assertEquals(v.path, revP);
		}

	}

	class PathRotateVals {
		String[] nodes;
		String node;
		Path path;

		public PathRotateVals(String[] nodes, String node, Path path) {
			this.nodes = nodes;
			this.node = node;
			this.path = path;
		}
	}

	@Test
	void testPathRotateToFront() throws WcbcException {

		ArrayList<PathRotateVals> testVals = new ArrayList<>();
		String[] xArr = { "x" };
		testVals.add(new PathRotateVals(xArr, "x", new Path(xArr)));
		String[] xyArr = { "x", "y" };
		testVals.add(new PathRotateVals(xyArr, "y", new Path("y,x".split(","))));
		testVals.add(new PathRotateVals(xyArr, "x", new Path("x,y".split(","))));
		String[] xyzArr = { "x", "y", "z" };
		testVals.add(new PathRotateVals(xyzArr, "x", new Path("x,y,z".split(","))));
		testVals.add(new PathRotateVals(xyzArr, "y", new Path("y,z,x".split(","))));
		testVals.add(new PathRotateVals(xyzArr, "z", new Path("z,x,y".split(","))));
		String[] zxyzArr = { "z", "x", "y", "z" };
		testVals.add(new PathRotateVals(zxyzArr, "x", new Path("x,y,z,z".split(","))));
		testVals.add(new PathRotateVals(zxyzArr, "z", new Path("z,x,y,z".split(","))));

		for (PathRotateVals v : testVals) {
			Path p = new Path(v.nodes);
			Path rotP = p.rotateToFront(v.node);
			assertEquals(v.path, rotP);
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
	void testPathEquals() throws WcbcException {

		ArrayList<PathEqualsVals> testVals = new ArrayList<>();
		String[] emptyArray = {};
		String[] emptyArray2 = {};
		String[] xArr = { "x" };
		String[] xArr2 = { "x" };
		testVals.add(new PathEqualsVals(new Path(emptyArray), new Path(emptyArray), true));
		testVals.add(new PathEqualsVals(new Path(emptyArray), new Path(emptyArray2), true));
		testVals.add(new PathEqualsVals(new Path(emptyArray), new Path(xArr), false));
		testVals.add(new PathEqualsVals(new Path(xArr), new Path(xArr), true));
		testVals.add(new PathEqualsVals(new Path(xArr), new Path(xArr2), true));
		String[] xyArr = { "x", "y" };
		String[] xyzArr = { "x", "y", "z" };
		String[] xyzArr2 = { "x", "y", "z" };
		testVals.add(new PathEqualsVals(new Path(xyArr), new Path(xyArr), true));
		testVals.add(new PathEqualsVals(new Path(xyArr), new Path(xyzArr), false));
		testVals.add(new PathEqualsVals(new Path(xyzArr), new Path(xyzArr), true));
		testVals.add(new PathEqualsVals(new Path(xyzArr), new Path(xyzArr2), true));
		testVals.add(new PathEqualsVals(new Object(), new Path(xyzArr2), false));
		testVals.add(new PathEqualsVals("asdf", new Path(xyzArr2), false));

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

	@Test
	void testIterator5Elts() throws WcbcException {
		Path p = new Path("a,b,c,d,e".split(","));
		Edge e0 = new Edge("a,b".split(","));
		Edge e1 = new Edge("b,c".split(","));
		Edge e2 = new Edge("c,d".split(","));
		Edge e3 = new Edge("d,e".split(","));
		Edge[] edges = { e0, e1, e2, e3 };
		int i = 0;
		for (Edge e : p) {
			assertEquals(edges[i], e);
			i++;
		}
		assertEquals(edges.length, i);
	}

	@Test
	void testIteratorEmpty() throws WcbcException {
		String[] empty = {};
		Path p = new Path(empty);
		Edge[] edges = {};
		int i = 0;
		for (Edge e : p) {
			assertEquals(edges[i], e);
			i++;
		}
		assertEquals(edges.length, i);
	}

	@Test
	void testIterator1Elt() throws WcbcException {
		String[] aArr = { "a" };
		Path p = new Path(aArr);
		Edge[] edges = {};
		int i = 0;
		for (Edge e : p) {
			assertEquals(edges[i], e);
			i++;
		}
		assertEquals(edges.length, i);
	}

	@Test
	void testIterator2Elts() throws WcbcException {
		String[] aArr = { "a", "b" };
		Path p = new Path(aArr);
		Edge e0 = new Edge("a,b".split(","));
		Edge[] edges = { e0 };
		int i = 0;
		for (Edge e : p) {
			assertEquals(edges[i], e);
			i++;
		}
		assertEquals(edges.length, i);
	}

	@Test
	void testContains() throws WcbcException {
		Path p = new Path("a,b,c,d,e".split(","));
		Edge e1 = new Edge("a,b".split(","));
		assertTrue(p.containsEdge(e1));
		Edge e2 = new Edge("b,c".split(","));
		assertTrue(p.containsEdge(e2));
		Edge e3 = new Edge("c,d".split(","));
		assertTrue(p.containsEdge(e3));
		Edge e4 = new Edge("d,e".split(","));
		assertTrue(p.containsEdge(e4));
		Edge e5 = new Edge("b,a".split(","));
		assertFalse(p.containsEdge(e5));
		Edge e6 = new Edge("b,d".split(","));
		assertFalse(p.containsEdge(e6));

		String[] empty = {};
		Path p2 = new Path(empty);
		assertFalse(p2.containsEdge(e6));
		
		
		
	}

	@Test
	void testCompareTo() throws WcbcException {
		String[] empty = {};
		Path p1 = new Path(empty);
		Path p2 = new Path("a".split(","));
		Path p3 = new Path("a,b".split(","));
		Path p4 = new Path("b,a".split(","));
		Path p5 = new Path("a,b,c".split(","));
		Path p6 = new Path("a,b,c".split(","));
		Path p7 = new Path("b,b,c".split(","));
		Path p8 = new Path("a,b,c,d,e".split(","));
		Path p9 = new Path("a,b,e,d,e".split(","));
		Path p10 = new Path("a,b,c,d,f".split(","));
		
		assertTrue(p1.compareTo(p1) == 0);
		assertTrue(p1.compareTo(p2) < 0);
		assertTrue(p8.compareTo(p1) > 0);
		assertTrue(p2.compareTo(p3) < 0);
		assertTrue(p3.compareTo(p4) < 0);
		assertTrue(p5.compareTo(p6) == 0);
		assertTrue(p7.compareTo(p6) > 0);
		assertTrue(p9.compareTo(p8) > 0);
		assertTrue(p8.compareTo(p10) < 0);
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
}
