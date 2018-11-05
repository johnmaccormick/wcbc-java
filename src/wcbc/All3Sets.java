package wcbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * SISO program All3Sets.java
 * 
 * Given a set, computes all subsets of size 3.
 * 
 * inString: Represents a set S of strings with elements separated by
 * whitespace.
 * 
 * returns: All subsets of S containing exactly 3 elements, formatted according
 * to the conventions described in utils.formatSetOfSets().
 * 
 * Example:
 * 
 * > java wcbc/All3Sets("4 5 6 7")
 * 
 * {4,5,6} {4,5,7} {4,6,7} {5,6,7}
 * 
 */
public class All3Sets implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {
		// the elements from which we can construct subsets
		String[] elems = inString.split("\\s+");
		// start with an empty list of 3-sets
		ArrayList<Collection<String>> threeSets = new ArrayList<>();
		// append each 3-set to the list threeSets
		for (int i = 0; i < elems.length; i++) {
			for (int j = i + 1; j < elems.length; j++) {
				for (int k = j + 1; k < elems.length; k++) {
					String[] this3Set = { elems[i], elems[j], elems[k] };
					threeSets.add(Arrays.asList(this3Set));
				}
			}
		}
		return utils.formatSetOfSets(threeSets);
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		All3Sets all3Sets = new All3Sets();
		String result = all3Sets.siso(inString);
		System.out.println(result);
	}

}
