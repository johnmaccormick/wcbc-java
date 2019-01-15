package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class BinAdTest {

	@Test
	void testIsWellFormed() {
		assertEquals(false, BinAd.isWellFormed(""));
		assertEquals(false, BinAd.isWellFormed("1"));
		assertEquals(false, BinAd.isWellFormed("1=2"));
		assertEquals(false, BinAd.isWellFormed("=101"));
		assertEquals(false, BinAd.isWellFormed("101="));
		assertEquals(false, BinAd.isWellFormed("111==11"));
		assertEquals(false, BinAd.isWellFormed("1+1+=101"));
		assertEquals(true, BinAd.isWellFormed("1=1"));
		assertEquals(true, BinAd.isWellFormed("1=0"));
		assertEquals(true, BinAd.isWellFormed("101111=111000"));
		assertEquals(true, BinAd.isWellFormed("101+1+0101=1111+1+0+1"));
	}

	@Test
	void testApplyRuleOne() {
		assertEquals(new HashSet<String>(), BinAd.applyRuleOne(""));
		assertEquals(new HashSet<String>(), BinAd.applyRuleOne("1"));
		assertEquals(new HashSet<String>(), BinAd.applyRuleOne("1=2"));
		assertEquals(new HashSet<String>(), BinAd.applyRuleOne("=101"));
		assertEquals(new HashSet<String>(), BinAd.applyRuleOne("101="));
		assertEquals(new HashSet<String>(), BinAd.applyRuleOne("111==11"));
		assertEquals(new HashSet<String>(), BinAd.applyRuleOne("1+1+=101"));
		assertEquals(new HashSet<String>(Arrays.asList("1+1=1+1")), BinAd.applyRuleOne("1=1"));
		assertEquals(new HashSet<String>(Arrays.asList("1+1=1+0")), BinAd.applyRuleOne("1=0"));
		assertEquals(new HashSet<String>(Arrays.asList("1+101111=1+111000")), BinAd.applyRuleOne("101111=111000"));
		assertEquals(new HashSet<String>(Arrays.asList("1+101+1+0101=1+1111+1+0+1")),
				BinAd.applyRuleOne("101+1+0101=1111+1+0+1"));
	}

	@Test
	void testApplyRuleTwo() throws WcbcException {
		assertEquals(new HashSet<String>(), BinAd.applyRuleTwo(""));
		assertEquals(new HashSet<String>(), BinAd.applyRuleTwo("1"));
		assertEquals(new HashSet<String>(), BinAd.applyRuleTwo("1=1"));
		assertEquals(new HashSet<String>(), BinAd.applyRuleTwo("1+1"));
		assertEquals(new HashSet<String>(Arrays.asList("10=10")), BinAd.applyRuleTwo("1+1=10"));
		assertEquals(new HashSet<String>(Arrays.asList("10+1+1=10+10", "1+10+1=10+10", "1+1+10=10+10", "1+1+1+1=100")),
				BinAd.applyRuleTwo("1+1+1+1=10+10"));
		assertEquals(new HashSet<String>(Arrays.asList("10+11+101+101+11=11+11+11+1+1", "1+1+11+1010+11=11+11+11+1+1",
				"1+1+11+101+101+11=110+11+1+1", "1+1+11+101+101+11=11+110+1+1", "1+1+11+101+101+11=11+11+11+10")),
				BinAd.applyRuleTwo("1+1+11+101+101+11=11+11+11+1+1"));
	}

	@Test
	void testApplyRuleThree() throws WcbcException {
		assertEquals(new HashSet<String>(), BinAd.applyRuleThree(""));
		assertEquals(new HashSet<String>(), BinAd.applyRuleThree("1"));
		assertEquals(new HashSet<String>(), BinAd.applyRuleThree("1+10"));
		assertEquals(new HashSet<String>(Arrays.asList("11=11")), BinAd.applyRuleThree("1+10=11"));
		assertEquals(new HashSet<String>(
				Arrays.asList("11+1+10101+1+1010=1+1+100", "1+10+1+10101+1011=1+1+100", "1+10+1+10101+1+1010=1+101")),
				BinAd.applyRuleThree("1+10+1+10101+1+1010=1+1+100"));
	}

	@Test
	void testApplyAllRules() throws WcbcException {
		assertEquals(new HashSet<String>(), BinAd.applyAllRules(""));
		assertEquals(new HashSet<String>(), BinAd.applyAllRules("1"));
		assertEquals(new HashSet<String>(), BinAd.applyAllRules("1+10"));
		assertEquals(new HashSet<String>(Arrays.asList("1+1+10=1+11", "11=11")), BinAd.applyAllRules("1+10=11"));
		assertEquals(
				new HashSet<String>(Arrays.asList("1+1+10+1+10101+1+1010=1+1+1+100", "1+10+1+10101+1+1010=10+100",
						"11+1+10101+1+1010=1+1+100", "1+10+1+10101+1011=1+1+100", "1+10+1+10101+1+1010=1+101")),
				BinAd.applyAllRules("1+10+1+10101+1+1010=1+1+100"));
	}

	@Test
	void testApplyAllRulesToFrontier() throws WcbcException {
		Set<String> provedFormulas = new HashSet<>(Arrays.asList(BinAd.theAxiom));
		Set<String> frontierFormulas = provedFormulas;

		frontierFormulas = BinAd.applyAllRulesToFrontier(provedFormulas, frontierFormulas);
		assertEquals(new HashSet<String>(Arrays.asList("1+1=1+1", "1=1")), provedFormulas);
		assertEquals(new HashSet<String>(Arrays.asList("1+1=1+1")), frontierFormulas);

		frontierFormulas = BinAd.applyAllRulesToFrontier(provedFormulas, frontierFormulas);
		assertEquals(new HashSet<String>(Arrays.asList("1+1=1+1", "1=1", "10=1+1", "1+1+1=1+1+1", "1+1=10")),
				provedFormulas);
		assertEquals(new HashSet<String>(Arrays.asList("10=1+1", "1+1+1=1+1+1", "1+1=10")), frontierFormulas);

		frontierFormulas = BinAd.applyAllRulesToFrontier(provedFormulas, frontierFormulas);
		assertEquals(new HashSet<String>(Arrays.asList("1=1", "1+1+1+1=1+1+1+1", "1+1+1=1+1+1", "1+1=1+1", "1+10=1+1+1",
				"1+1=10", "1+1+1=1+10", "10=10", "10+1=1+1+1", "1+1+1=10+1", "10=1+1")), provedFormulas);
		assertEquals(new HashSet<String>(
				Arrays.asList("1+10=1+1+1", "1+1+1=1+10", "10=10", "10+1=1+1+1", "1+1+1=10+1", "1+1+1+1=1+1+1+1")),
				frontierFormulas);
	}

	@Test
	void testApplyAllRulesToFrontierWithPredecessors() throws WcbcException {
		Set<String> provedFormulas = new HashSet<>(Arrays.asList(BinAd.theAxiom));
		Set<String> frontierFormulas = provedFormulas;
		Map<String, String> predecessors = new HashMap<String, String>();
		predecessors.put(BinAd.theAxiom, "");

		Map<String, String> expected1 = new HashMap<String, String>();
		expected1.put("1+1=1+1", "1=1");
		expected1.put("1=1", "");

		Map<String, String> expected2 = new HashMap<String, String>();
		expected2.put("1+1+1=1+1+1", "1+1=1+1");
		expected2.put("1+1=1+1", "1=1");
		expected2.put("1+1=10", "1+1=1+1");
		expected2.put("10=1+1", "1+1=1+1");
		expected2.put("1=1", "");

		Map<String, String> expected3 = new HashMap<String, String>();
		expected3.put("1=1", "");
		expected3.put("1+1+1=1+10", "1+1+1=1+1+1");
		expected3.put("1+1+1+1=1+1+1+1", "1+1+1=1+1+1");
		expected3.put("1+10=1+1+1", "1+1+1=1+1+1");
		expected3.put("1+1=10", "1+1=1+1");
		expected3.put("10=1+1", "1+1=1+1");
		expected3.put("1+1=1+1", "1=1");
		expected3.put("10=10", "1+1=10");
		expected3.put("1+1+1=1+1+1", "1+1=1+1");
		expected3.put("1+1+1=10+1", "1+1+1=1+1+1");
		expected3.put("10+1=1+1+1", "1+1+1=1+1+1");

		// only check predecessors. provedFormulas and frontierFormulas checked in
		// previous test.
		frontierFormulas = BinAd.applyAllRulesToFrontier(provedFormulas, frontierFormulas, predecessors);
		assertEquals(expected1, predecessors);

		frontierFormulas = BinAd.applyAllRulesToFrontier(provedFormulas, frontierFormulas, predecessors);
		assertEquals(expected2, predecessors);

		frontierFormulas = BinAd.applyAllRulesToFrontier(provedFormulas, frontierFormulas, predecessors);
		assertEquals(expected3, predecessors);
	}

	@Test
	void testGetProofFromPredecessors() throws WcbcException {
		Set<String> provedFormulas = new HashSet<>(Arrays.asList(BinAd.theAxiom));
		Set<String> frontierFormulas = provedFormulas;
		Map<String, String> predecessors = new HashMap<String, String>();
		predecessors.put(BinAd.theAxiom, "");
		frontierFormulas = BinAd.applyAllRulesToFrontier(provedFormulas, frontierFormulas, predecessors);
		frontierFormulas = BinAd.applyAllRulesToFrontier(provedFormulas, frontierFormulas, predecessors);
		frontierFormulas = BinAd.applyAllRulesToFrontier(provedFormulas, frontierFormulas, predecessors);

		List<String> proof = BinAd.getProofFromPredecessors("1+1+1=10+1", predecessors);
		assertEquals("[1=1, 1+1=1+1, 1+1+1=1+1+1, 1+1+1=10+1]", proof.toString());
	}

	@Test
	void testEnumerateStatements() throws WcbcException {
		Set<String> solution = new HashSet<String>(Arrays.asList("1+1+1=1+10", "10+1=1+1+1", "10=10", "1+1=10",
				"10=1+1", "1+1+1+1=1+1+1+1", "1=1", "1+1=1+1", "1+1+1=10+1", "1+1+1=1+1+1", "1+10=1+1+1"));
		Set<String> val = BinAd.enumerateStatements(10);
		assertEquals(solution, val);
	}

	@Test
	void testIsProvable() throws WcbcException {
		assertTrue(BinAd.isProvable("1", 1000).result.startsWith("not"));
		assertTrue(BinAd.isProvable("1=1").result.startsWith("provable"));
		assertTrue(BinAd.isProvable("10=10").result.startsWith("provable"));
		assertTrue(BinAd.isProvable("1+1=10").result.startsWith("provable"));
		assertTrue(BinAd.isProvable("1+1+1=1+1+1+1", 1000).result.startsWith("not"));
		// take a while
		// assertTrue(BinAd.isProvable("1+1=1+1+1").result.startsWith("not"));
		// assertTrue(BinAd.isProvable("1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1=11000").result.startsWith("not"));
	}

	@Test
	void testComputeBinarySum() throws WcbcException {
		assertEquals(1, BinAd.computeBinarySum("1"));
		assertEquals(0, BinAd.computeBinarySum("0"));
		assertEquals(2, BinAd.computeBinarySum("10"));
		assertEquals(1, BinAd.computeBinarySum("01"));
		assertEquals(2, BinAd.computeBinarySum("1+1"));
		assertEquals(25, BinAd.computeBinarySum("101+1+10011"));
		assertEquals(57, BinAd.computeBinarySum("1+11+111+1111+11111"));
	}

	@Test
	void testIsTrue() throws WcbcException {
		assertEquals(false, BinAd.isTrue(""));
		assertEquals(false, BinAd.isTrue("123=321"));
		assertEquals(false, BinAd.isTrue("1"));
		assertEquals(true, BinAd.isTrue("1=1"));
		assertEquals(true, BinAd.isTrue("10=10"));
		assertEquals(true, BinAd.isTrue("1+1=10"));
		assertEquals(true, BinAd.isTrue("1+10+10+1=110"));
		assertEquals(false, BinAd.isTrue("1+1+1=1+1+1+1"));
		assertEquals(true, BinAd.isTrue("1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1+1=11000"));
	}

	@Test
	void testIsProof() throws WcbcException {

		String goodProof1 = "1=1\n1+1=1+1\n1+1+1=1+1+1\n1+1+1=1+10\n1+1+1=11\n10+1=11";
		String goodProof2 = "1=1\n1+1=1+1\n1=1\n1+1+1=1+1+1\n1+1+1=1+10\n1+1+1=11\n1+1+1+1=1+1+1+1\n1+10=1+10\n10+1=11";
		String badProof1 = "1=1\n1+1=1+1\n1+1+1=1+1+1\n1+1+1=11\n10+1=11";
		String badProof2 = "asdfsa\ndfd\n10+1=11";
		String badProof3 = "1=1\n1+1=1+1\n1+1+1=1+1+1\n1+1+1=1+10\n1+1+1=11";

		String target = "10+1=11";

		assertEquals("yes", BinAd.isProof(goodProof1, target));
		assertEquals("yes", BinAd.isProof(goodProof2, target));
		assertEquals("no", BinAd.isProof(badProof1, target));
		assertEquals("no", BinAd.isProof(badProof2, target));
		assertEquals("no", BinAd.isProof(badProof3, target));
	}

}
