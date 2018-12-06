package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConvertUHCtoDHCTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		ConvertUHCtoDHC convUD = new ConvertUHCtoDHC();
		Uhc uhc = new Uhc();
		Dhc dhc = new Dhc();

		String[] instances = { "",
				"a",
				"a b",
				"a b c",
                "a,a",
                "a,b",
                "a,b b,c c,a",
                "a,b b,c c,d",
                "a,b b,c c,d d,a",
                "a,b b,c c,d a,d d,b c,a",
                "a,b b,c c,d a,d d,b c,a x y z",
                };

		for (String instance : instances) {
			String convertedInstance = convUD.siso(instance);
			String instanceSolution = uhc.siso(instance);
			String convertedInstanceSolution = dhc.siso(convertedInstance);
			String revertedSolution = convUD.revertSolution(convertedInstanceSolution);

			if (revertedSolution.equals("no")) {
				assertEquals("no", instanceSolution);
			} else {
				Graph graph = new Graph(instance, false, false);
	            Path path = Path.fromString(revertedSolution);
				assertTrue(graph.isHamiltonCycle(path));
			}
		}

	}
}
