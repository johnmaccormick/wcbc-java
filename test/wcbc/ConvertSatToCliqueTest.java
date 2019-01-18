package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ConvertSatToCliqueTest {

	private void doOneSisoTest(String satInstance) throws WcbcException, IOException {
		ConvertSatToClique convertSatToClique = new ConvertSatToClique();
		Sat sat = new Sat();
		String cliqueInstance = convertSatToClique.siso(satInstance);
		String satSolution = sat.siso(satInstance);

	}

	@Test
	void testSiso() {
		fail("Not yet implemented");
	}

}
