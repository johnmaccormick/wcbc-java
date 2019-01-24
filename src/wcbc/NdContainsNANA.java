package wcbc;

import java.io.IOException;
import java.util.ArrayList;

/**
 * SISO program NdContainsNANA.java
 * 
 * This program searches an input string for any of the four strings "CACA",
 * "GAGA", "TATA", "AAAA", returning "yes" if any of them is found and "no"
 * otherwise. The program performs the search in a nondeterministic manner,
 * using a separate thread for each of the four strings.
 * 
 * inString: the string to be searched
 * 
 * returns: "yes" if any of the four strings "CACA", "GAGA", "TATA", "AAAA" is
 * found, and "no" otherwise.
 * 
 */
public class NdContainsNANA implements Siso {

	// the list of strings to look for
	static final String[] strings = { "CACA", "GAGA", "TATA", "AAAA" };

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		// create an empty list that will store our threads
		ArrayList<Thread> threads = new ArrayList<>();

		// create an object that can store a nondeterministic solution computed by
		// other threads
		NonDetSolution ndSoln = new NonDetSolution();

		// create the threads that will perform the nondeterministic computation
		for (String s : strings) {
			// create a thread that will execute findString(s, inString, ndSoln)
			// when started; findString is a helper function defined below
			Thread t = new Thread(new FindString(s, inString, ndSoln));
			threads.add(t);
		}

		// Perform the nondeterministic computation. By definition, this means
		// that each thread is started, and we get a return value if either (a)
		// any thread reports a positive solution, or (b) all threads report
		// negative solutions.
		String solution = utils.waitForOnePosOrAllNeg(threads, ndSoln);

		return solution;
	}

	/**
	 * FindString is a helper class for performing string searches in a separate
	 * thread.
	 */
	class FindString implements Runnable {

		private String substring;
		private String text;
		private NonDetSolution ndSoln;

		/**
		 * @param substring
		 *            The string for which we will be searching
		 * @param text
		 *            The text that will be searched for the given substring
		 * @param ndSoln
		 *            A NonDetSolution object for storing any positive solution found
		 */
		public FindString(String substring, String text, NonDetSolution ndSoln) {
			this.substring = substring;
			this.text = text;
			this.ndSoln = ndSoln;
		}

		@Override
		public void run() {
			if (text.contains(substring)) {
				ndSoln.setSolution("yes");
			}
		}
	}

    public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		NdContainsNANA ndContainsNANA = new NdContainsNANA();
		String result = ndContainsNANA.siso(inString);
		System.out.println(result);
	}


}
