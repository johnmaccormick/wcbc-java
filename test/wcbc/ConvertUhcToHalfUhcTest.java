package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ConvertUhcToHalfUhcTest {

	@Test
	void testSiso() throws WcbcException, IOException {
		ConvertUhcToHalfUhc conv = new ConvertUhcToHalfUhc();
		Uhc uhc = new Uhc();
		HalfUhc halfUhc = new HalfUhc();
		
		String[] instances = { "",
                "a,a",
                "a,b",
                "a,b b,c c,a",
                "a,b b,c c,d",
                "a,b b,c c,d d,a",
                "a,b b,c c,d a,d d,b c,a",
                };

		for (String instance : instances) {
			String convertedInstance = conv.siso(instance);
			String instanceSolution = uhc.siso(instance);
			String convertedInstanceSolution = halfUhc.siso(convertedInstance);
			String revertedSolution = conv.revertSolution(convertedInstanceSolution);

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
