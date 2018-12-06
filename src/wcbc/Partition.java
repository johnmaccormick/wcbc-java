package wcbc;

import java.io.IOException;

/**
 * SISO program Partition.java
 * 
 * Solves the computational problem Partition. Given an input which is a string
 * representing a list of integer weights, it searches for a feasible partition
 * i.e. a subset of the weights that sums to exactly half the total weight. The
 * recursive search algorithm enumerates all subsets of the weights and
 * therefore requires exponential time in the worst case.
 * 
 * inString: the integer weights are separated by whitespace from each other.
 * 
 * returns: If a feasible partition exists, it is returned formatted as a
 * sequence of weights separated by space characters. Otherwise "no" is
 * returned.
 * 
 * Example:
 * 
 * > java wcbc/Partition "6 6 7 7"
 * 
 * "6 7"
 * 
 */
public class Partition implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		int totalWeight = utils.sumIntsInString(inString);

		if (totalWeight % 2 == 1) {
			// If the sum of the weights isn"t even, no partition is
			// possible.
			return "no";

		} else {
			// Reduce the problem to an instance of Packing.
			String targetWeight = Integer.toString(totalWeight / 2);
			String packingString = inString + ";" + targetWeight + ";" + targetWeight;
			return new Packing().siso(packingString);
		}
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		Partition partition = new Partition();
		String result = partition.siso(inString);
		System.out.println(result);
	}

}
