package wcbc;

/**
 * Manages solutions to nondeterministic programs.
 * 
 * NonDetSolution is a class that can be used to arrange for nondeterministic
 * (i.e. multithreaded) Java programs to return a value. For an example of how
 * to use it, see the program NdContainsNANA.java (the Python version of which
 * is also explained in the book). The basic idea is to create a single
 * NonDetSolution object nds to be used by the nondeterministic program. The nds
 * object will be passed to each thread created, then nds and the list of
 * threads will be passed to utils.waitForOnePosOrAllNeg() in order to obtain
 * the program's solution.
 */
public class NonDetSolution {

	// Stores the solution to the problem being solved. By default,
	// it has the value "no".
	private String solution = "no";

	// False until either a positive solution has been found or all threads have
	// terminated with negative solutions.
	private boolean done = false;

	/**
	 * Wait until we receive the signal that a positive solution has been found or
	 * all threads have terminated negatively.
	 * 
	 * @throws WcbcException
	 */
	synchronized public void waitUntilDone() throws WcbcException {
		while (!done) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				throw new WcbcException("NonDetSolution object received unexpected interruption");
			}
		}
	}

	/**
	 * Send the signal that a positive solution has been found or all threads have
	 * terminated negatively
	 */
	synchronized private void setDone() {
		done = true;
		this.notifyAll();
	}

	/**
	 * Set the solution to the given value, and signal if it's positive.
	 * 
	 * This is a setter for the solution attribute. In addition, if the new value
	 * for the solution attribute is positive (i.e. anything other than the string
	 * "no"), we signal this object's event via notifyAll(). This will enable other
	 * threads to become aware that a positive solution has been found.
	 * 
	 * @param solution
	 *            new value for solution
	 */
	synchronized public void setSolution(String solution) {
		// We only take action for positive solutions. If the given
		// solution is "no", we leave the default value of "no"
		// untouched -- and if another thread has meanwhile set the
		// solution to a positive value, we should certainly not set it
		// back to "no" because positive solutions take precedence
		// anyway.
		if (!solution.equals("no")) {
			this.solution = solution;
			setDone();
		}
	}

	/**
	 * Return the stored value of the solution.
	 * 
	 * @return the stored value of the solution
	 */
	synchronized public String getSolution() {
		return solution;
	}

}
