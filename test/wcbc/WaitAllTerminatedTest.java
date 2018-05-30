package wcbc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import wcbc.NonDetSolutionTest.NdSolnThread;

class WaitAllTerminatedTest {

	class FindNum implements Runnable {

		private NonDetSolution nds;
		private int startNum;
		private int increment;
		private int targetNum;

		public FindNum(int startNum, int increment, int targetNum, NonDetSolution nds) {
			this.nds = nds;
			this.startNum = startNum;
			this.increment = increment;
			this.targetNum = targetNum;
		}

		@Override
		public void run() {
			int maxNum = 1000000;
			int theNum = startNum;
			while (theNum < maxNum) {
				if (theNum == targetNum) {
					nds.setSolution(Integer.toString(startNum));
					return;
				} else {
					theNum += increment;
				}
			}
		}

	}

	static final int numThreads = 8;
	static final int increment = 10;

	/**
	 * Run a test with a positive solution
	 * 
	 * @throws WcbcException
	 */
	@Test
	void testWaitAllTerminatedPos() throws WcbcException {
		int targetNum = 100005;
		NonDetSolution nds = new NonDetSolution();
		ArrayList<Thread> threads = new ArrayList<>();
		for (int i = 0; i < numThreads; i++) {
			Thread t = new Thread(new FindNum(i, increment, targetNum, nds));
			threads.add(t);
		}
		String solution = utils.waitForOnePosOrAllNeg(threads, nds);
		assertEquals("5", solution);
	}

	/**
	 * Run a test with a negative solution
	 * 
	 * @throws WcbcException
	 */
	@Test
	void testWaitAllTerminatedNeg() throws WcbcException {
		int targetNum = 100009;
		NonDetSolution nds = new NonDetSolution();
		ArrayList<Thread> threads = new ArrayList<>();
		for (int i = 0; i < numThreads; i++) {
			Thread t = new Thread(new FindNum(i, increment, targetNum, nds));
			threads.add(t);
		}
		String solution = utils.waitForOnePosOrAllNeg(threads, nds);
		assertEquals("no", solution);
	}

}
