package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class PackingTest {


	@Test
	void testSiso() throws WcbcException, IOException {
		Packing p = new Packing();
		assertEquals("no", p.siso("; 5; 5"));
		assertEquals("5", p.siso("5; 5; 5"));
		assertEquals("5", p.siso("5 7; 5; 5"));
		assertEquals("7", p.siso("5 7; 7; 7"));
		assertEquals("no", p.siso("5 7; 6; 6"));
		assertEquals("5 7", p.siso("5 7; 10 ; 15 "));
		assertEquals("3000 8000", p.siso("1 2 3000 4 5 6 7 8000 9; 10000 ; 15000 "));
		assertEquals("no", p.siso("1 2 3000 4 5 6 7 8000 9; 100 ; 150 "));
	}

}
