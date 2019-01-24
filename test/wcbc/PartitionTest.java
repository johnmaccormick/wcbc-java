package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class PartitionTest {


	@Test
	void testSiso() throws WcbcException, IOException {
		Partition p = new Partition();
		assertEquals("", p.siso(""));
		assertEquals("no", p.siso("5"));
		assertEquals("no", p.siso("5 7"));
		assertEquals("6", p.siso("6 6"));
		assertEquals("6 7", p.siso("6 6 7 7"));
		assertEquals("3 3 1000 1000", p.siso("3 3 3 3 1000 1000 1000 1000"));
	}
}
