package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

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
		String[] oneArray = {"1"};
		testVals.add(new PathReverseVals(oneArray, new Path(oneArray)));
		String[] twoArray = {"1", "2"};
		String[] twoArrayRev = {"2", "1"};
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

}
