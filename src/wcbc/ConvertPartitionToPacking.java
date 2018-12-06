package wcbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SISO program ConvertPartitionToPacking.java
 * 
 * Convert an instance of Partition into an equivalent instance of Packing.
 * 
 * inString: an instance of Partition, represented as integer weights separated
 * by whitespace.
 * 
 * returns: an instance of Packing, represented as integer weights separated by
 * whitespace followed by the low and high thresholds, separated by semicolons.
 * 
 * Example:
 * 
 * > java wcbc/ConvertPartitionToPacking "6 6 7 7"
 * 
 * "6 6 7 7;13;13"
 */
public class ConvertPartitionToPacking implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		int totalWeight = utils.sumIntsInString(inString);

		if (totalWeight % 2 == 1) {
			// if the total weight is odd, no partition is possible,
			// so return any negative instance of Packing
			return "0;1;1";

		} else {
			// use thresholds that are half the total weight
			String targetWeight = Integer.toString(totalWeight / 2);
			return inString + ";" + targetWeight + ";" + targetWeight;
		}
	}

	
	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		ConvertPartitionToPacking convertPartitionToPacking = new ConvertPartitionToPacking();
		String result = convertPartitionToPacking.siso(inString);
		System.out.println(result);
	}
}
