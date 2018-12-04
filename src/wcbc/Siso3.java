package wcbc;

import java.io.IOException;

/**
 * Interface Siso3.java
 * 
 * The WCBC book describes the concept known as a SISO program, which stands for
 * "string in string out." Most SISO programs receive only a single parameter,
 * but sometimes we need additional parameters. Siso3.java is an interface
 * providing the abstract function siso(), which receives three String
 * parameters and returns a String object. Often, this 3-parameter functionality
 * is used by verifiers, in which case the three parameters are a problem
 * instance I, a proposed solution S, and a hint H.
 *
 */
public interface Siso3 {
	/**
	 * Provides the 3-parameter variant of "string in string out" (i.e. SISO)
	 * functionality required for SISO programs as described in the book. In
	 * principle, the parameters could be any three strings representing any
	 * quantities whatsoever. In practice, most often the 3-parameter variant is
	 * used for verifiers, in which case the parameters have the specific meanings
	 * given below.
	 * 
	 * @param inString
	 *            Instance of the problem under consideration.
	 * @param solution
	 *            The proposed solution to be verified.
	 * @param hint
	 *            A hint string which may assist verification.
	 * @return The result computed by the SISO function. For verifiers, this will be
	 *         "correct" if the proposed solution could be successfully verified,
	 *         and otherwise "unsure".
	 * @throws WcbcException
	 * @throws IOException
	 */
	String siso(String inString, String solution, String hint) throws WcbcException, IOException;
}
