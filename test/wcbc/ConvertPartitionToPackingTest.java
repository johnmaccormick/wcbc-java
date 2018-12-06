package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ConvertPartitionToPackingTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		ConvertPartitionToPacking cpp = new ConvertPartitionToPacking();
		Partition partition = new Partition();
		Packing packing = new Packing();

		String[] instances = { "5", "5 6", "5 6 7", "5 6 7 8", "", "5 7", "6 6", "6 6 7 7", "10 20 30 40 11 21 31 41" };

		for (String instance : instances) {
			String convertedInstance = cpp.siso(instance);
			String instanceSolution = partition.siso(instance);
			String convertedInstanceSolution = packing.siso(convertedInstance);
			String revertedSolution = convertedInstanceSolution;

			if (revertedSolution.equals("no")) {
				assertEquals("no", instanceSolution);
			} else {
				int total = utils.sumIntsInString(instance);
				int solutionTotal = utils.sumIntsInString(revertedSolution);
				assertEquals(total, solutionTotal * 2);
			}
		}

	}

}
