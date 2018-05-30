package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class NonDetSolutionTest {

	class NdSolnThread implements Runnable {

		NonDetSolution nds;
		int id;

		public NdSolnThread(NonDetSolution nds, int id) {
			this.nds = nds;
			this.id = id;
		}

		@Override
		public void run() {
			try {
				nds.waitUntilDone();
			} catch (WcbcException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println(id);
		}

	}

	@Test
	void testNonDetSolution() {
		NonDetSolution nds = new NonDetSolution();
		ArrayList<Thread> threads = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new NdSolnThread(nds, i));
			threads.add(t);
			t.start();
		}
		assertEquals("no", nds.getSolution());
		nds.setSolution("no");
		assertEquals("no", nds.getSolution());
		nds.setSolution("asdf");
		assertEquals("asdf", nds.getSolution());
	}


}
