package wcbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * SISO program AllSubsets.java
 * 
 * Given a set, computes all subsets of that set.
 * 
 * inString: Represents a set S of strings with elements separated by
 * whitespace.
 * 
 * returns: All subsets of S, formatted according to the conventions described
 * in utils.formatSetOfSets().
 * 
 * Example:
 * 
 * > java wcbc/AllSubsets("4 5 6")
 * 
 * {} {4} {5} {4,5} {6} {4,6} {5,6} {4,5,6}
 * 
 * 
 */
public class AllSubsets implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {
		// the elements from which we can construct subsets
		String[] elems = {}; // handle empty string separately...
		if (!inString.equals("")) {
			elems = inString.split("\\s+");;
		}
		// start with an empty list of subsets
		ArrayList<Collection<String>> theSubsets = new ArrayList<>();
	    // add the empty set to the list of subsets
		String[] empty = {}; 
	    theSubsets.add( Arrays.asList(empty) );
		
	    // For each element of the input, append copies of all
	    // previously computed subsets, but with the additional new element
	    // included.
	    for(String element:elems) {
	    	ArrayList<ArrayList<String>> newSets = new ArrayList<>();
	    	for(Collection<String> thisSet: theSubsets) {
	            // create a new subset that includes the current element,
	            // and append it to the list of subsets
	    		ArrayList<String> newSet = new ArrayList<>(thisSet);
	    		newSet.add(element);
	    		newSets.add(newSet);
	    	}
	        // Update the master list of subsets with all the newly created
	        // subsets. This doubles the number of subsets in theSubsets.
	    	theSubsets.addAll(newSets);
	    }
	    
		return utils.formatSetOfSets(theSubsets);
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		AllSubsets allSubsets = new AllSubsets();
		String result = allSubsets.siso(inString);
		System.out.println(result);
	}

}
