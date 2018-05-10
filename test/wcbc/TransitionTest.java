package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransitionTest {

	Transition    t1 = new Transition("a", "b", "c", "d", TuringMachine.Direction.LEFT);
	Transition    t1b = new Transition("a", "b", "c", "d", TuringMachine.Direction.LEFT);
	Transition    t2 = new Transition("a", "b", "qwerty", "d", TuringMachine.Direction.LEFT);
	Transition    t2b = new Transition("a", "b", "xx", "d", TuringMachine.Direction.LEFT);
	Transition    t3 = new Transition("a", "b", "c", "a", TuringMachine.Direction.RIGHT);
	Transition    t4 = new Transition("u", "v", "w", "x", TuringMachine.Direction.RIGHT);
	ArrayList<Transition> tList = null;
	Transition    t5 = null;
	Transition    t6 = new Transition("a", "b", "cqwertyxx", "d", TuringMachine.Direction.LEFT);
	
	
	@BeforeEach
	void setUp() throws Exception {
		tList = new ArrayList<>();
		tList.add(t1);tList.add(t2);tList.add(t2b);
		
	}

	@Test
	void testTransition() {
		assertEquals(t1.getSourceState(), "a");
	}

	@Test
	void testIsCompatible() {
		assertTrue(t1.isCompatible(t1b));
		assertTrue(t1.isCompatible(t2));
		assertTrue(!t1.isCompatible(t3));
	}

	@Test
	void testEqualsObject() {
		assertTrue(t1.equals(t1b));
		assertTrue(!t1.equals(t2));
	}

	@Test
	void testUnify() throws WcbcException {
		t5 = Transition.unify(tList);
		assertTrue(t5.equals(t6));
	}	
	
}
