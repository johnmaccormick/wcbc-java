package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ConvertDHCtoUHCTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		ConvertDHCtoUHC convDU = new ConvertDHCtoUHC();
		Uhc uhc = new Uhc();
		Dhc dhc = new Dhc();

		String[] instances = { "",
				"a",
				"a b",
				"a b c",
                "a,a",
                "a,b",
                "a,b b,a",
                "a,b b,c c,a",
                "a,b b,c a,c",
                "a,b b,c c,d",
                "a,b b,c c,d d,a",
                "a,b b,c c,d a,d",
                "a,b b,c c,d a,d d,b c,a",
                "a,b b,c c,d a,d d,b c,a x y z",
                };

		for (String instance : instances) {
			String convertedInstance = convDU.siso(instance);
			String instanceSolution = dhc.siso(instance);
			String convertedInstanceSolution = uhc.siso(convertedInstance);
			String revertedSolution = convDU.revertSolution(convertedInstanceSolution);

			if (revertedSolution.equals("no")) {
				assertEquals("no", instanceSolution);
			} else {
				Graph graph = new Graph(instance, false, true);
	            Path path = Path.fromString(revertedSolution);
				assertTrue(graph.isHamiltonCycle(path) || graph.isHamiltonCycle(path.reversed()));
			}
		}

	}


}
