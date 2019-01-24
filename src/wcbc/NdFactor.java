package wcbc;

import java.io.IOException;
import java.util.ArrayList;

/**
 * SISO program NdFactor.java
 * 
 * Solves the computational problem Factor nondeterministically. Given an input
 * integer M, it returns a nontrivial factor of M, or "no" if no such factor
 * exists.
 * 
 * inString: an integer M.
 * 
 * returns: a nontrivial factor of M, or "no" if no such factor exists. Because
 * a nondeterministic algorithm is used, different factors could be returned on
 * different runs with the same inputs.
 * 
 * Example:
 * 
 * > java wcbc/NdFactor 15
 * 
 * "3"
 * 
 */
public class NdFactor implements Siso {



	@Override
	public String siso(String inString) throws WcbcException, IOException {
		int M = Integer.parseInt(inString);
		int low = 2;
		int high = M;
		NonDetSolution nonDetSolution = new NonDetSolution();

		FactorHelper fh = new FactorHelper(M, low, high, nonDetSolution);
		fh.run();
		
		return nonDetSolution.getSolution();
	}

	
	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		NdFactor ndFactor = new NdFactor();
		String result = ndFactor.siso(inString);
		System.out.println(result);
	}	
	
//	/**
//	 * This helper function searches nondeterministically for a factor m of M in the
//	 * range low<=m<high), storing any positive solution found in the given
//	 * nonDetSolution. The function uses a divide and conquer approach, splitting
//	 * the given interval into two smaller intervals and launching new threads to
//	 * search those intervals -- unless the interval already has size 1, in which
//	 * case the only possible value of m is tested.
//	 */
//	private void ndFactorHelper(int M, int low, int high, NonDetSolution nonDetSolution) {
//		if (high <= low) {
//			// There is nothing to search.
//			return;
//		} else if (high - low == 1) {
//			// The search interval includes only one possibility (low), so test it.
//			if (M % low == 0) {
//				// low is a factor of M, so store this solution
//				nonDetSolution.setSolution(Integer.toString(low));
//			}
//			return;
//		} else {
//			// Split the search interval in two, and launch new threads to
//			// search those intervals.
//			ArrayList<Thread> threads = new ArrayList<>();
//			NonDetSolution childNdSoln = new NonDetSolution();
//			int mid = (high + low) / 2;
//
//			// t1 = Thread(target=ndFactorHelper, args = (M,low,mid,childNdSoln))
//
//		}
//	}

	/**
	 * This helper class searches nondeterministically for a factor m of M in the
	 * range low<=m<high), storing any positive solution found in the given
	 * nonDetSolution. The function uses a divide and conquer approach, splitting
	 * the given interval into two smaller intervals and launching new threads to
	 * search those intervals -- unless the interval already has size 1, in which
	 * case the only possible value of m is tested.
	 */
	class FactorHelper implements Runnable {

		private int M;
		private int low;
		private int high;
		private NonDetSolution ndSoln;

		public FactorHelper(int M, int low, int high, NonDetSolution ndSoln) {
			this.M = M;
			this.low = low;
			this.high = high;
			this.ndSoln = ndSoln;
		}

		@Override
		public void run() {
			if (high <= low) {
				// There is nothing to search.
				return;
			} else if (high - low == 1) {
				// The search interval includes only one possibility (low), so test it.
				if (M % low == 0) {
					// low is a factor of M, so store this solution
					ndSoln.setSolution(Integer.toString(low));
				}
				return;
			} else {
				// Split the search interval in two, and launch new threads to
				// search those intervals.
				ArrayList<Thread> threads = new ArrayList<>();
				NonDetSolution childNdSoln = new NonDetSolution();
				int mid = (high + low) / 2;

				Thread t1 = new Thread(new FactorHelper(M, low, mid, childNdSoln));
				Thread t2 = new Thread(new FactorHelper(M, mid, high, childNdSoln));
				threads.add(t1);
				threads.add(t2);
				String solution = null;
				try {
					solution = utils.waitForOnePosOrAllNeg(threads, childNdSoln);
				} catch (WcbcException e) {
					throw new RuntimeException(e.getMessage());
				}
				ndSoln.setSolution(solution);
			}
		}
	}
}
