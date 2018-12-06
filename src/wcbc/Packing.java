package wcbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SISO program Packing.java
 * 
 * Solves the computational problem Packing. Given an input which is a string
 * representing a list of integer weights together with a low threshold L and
 * high threshold H, it searches for a feasible packing i.e. a subset of the
 * weights that sums to at least L and at most H. The recursive search algorithm
 * enumerates all subsets of the weights and therefore requires exponential time
 * in the worst case.
 * 
 * inString: the integer weights are separated by whitespace from each other; L
 * and H are separated from each other and from the weights by semicolons.
 * 
 * returns: If a feasible packing exists, it is returned formatted as a sequence
 * of weights separated by space characters. Otherwise "no" is returned.
 * 
 * Example:
 * 
 * > java wcbc/Packing "1 2 3000 4 5 6 7 8000 9; 10000 ; 15000"
 * 
 * "3000 8000"
 * 
 */
public class Packing implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {

		String[] components = inString.split(";");
		utils.trimAll(components);
		String weightString = components[0];
		int[] weights = utils.intArrayFromString(weightString);
		String LStr = components[1];
		int L = Integer.parseInt(LStr);
		String HStr = components[2];
		int H = Integer.parseInt(HStr);

		// Use a recursive strategy to search for a feasible packing.
		List<Integer> prefix = new ArrayList<>();
		int prefixWeight = 0;
		List<Integer> remainingWeights = utils.intListFromString(weightString);
		List<Integer> aPacking = packingHelper(prefix, prefixWeight, remainingWeights, L, H);
		if (aPacking == null) {
			return "no";

		} else {
			return utils.joinInts(aPacking, " ");
		}
	}

	/**
	 * Helper function for recursively finding feasible packing.
	 * 
	 * @param prefix
	 *            list of integer weights that are already in the packing we are
	 *            searching for
	 * @param prefixWeight
	 *            precomputed sum of the weights in prefix, passed as a parameter to
	 *            prevent needless recomputation.
	 * @param remainingWeights
	 *            list of integer weights that are still available to be used in the
	 *            packing we are searching for.
	 * @param L
	 *            the low threshold
	 * @param H
	 *            the high threshold
	 * @return A list P of integers that includes prefix and possibly some of
	 *         remainingWeights, such that L<=sum(P)<=H. If no such list exists,
	 *         null is returned.
	 */
	private List<Integer> packingHelper(List<Integer> prefix, int prefixWeight, List<Integer> remainingWeights, int L,
			int H) {
		if (L <= prefixWeight && prefixWeight <= H) {
			// We have found a feasible packing, so return it.
			return prefix;
		} else if (remainingWeights.size() == 0) {
			// There is no feasible packing with this prefix, so return null.
			return null;
		} else {
			// We don't have a solution yet, so we will attempt two recursive
			// calls to packingHelper(): the first one will not use the next
			// weight and the second one will use the next weight.
			List<Integer> newWeights = remainingWeights.subList(1, remainingWeights.size());
			int nextWeight = remainingWeights.get(0);
			// 1. Recursive call *without* the next weight
			List<Integer> aPacking = packingHelper(prefix, prefixWeight, newWeights, L, H);
			if (aPacking != null) {
				return aPacking;
			} else {
				// 2. Recursive call *with* the next weight
				List<Integer> newPrefix = new ArrayList<>(prefix);
				newPrefix.add(nextWeight);
				int newPrefixWeight = prefixWeight + nextWeight; // adds numerical weights
				aPacking = packingHelper(newPrefix, newPrefixWeight, newWeights, L, H);
				return aPacking;
			}
		}
	}

	
	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		Packing packing = new Packing();
		String result = packing.siso(inString);
		System.out.println(result);
	}
	

}
