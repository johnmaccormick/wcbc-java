package wcbc;

import java.io.IOException;

/**
 * Interface Siso.java
 * 
 * The WCBC book describes the concept known as a SISO program, which stands for
 * "string in string out." Siso.java is an interface providing the abstract
 * function siso(), which receives a single String parameter and returns a
 * String object. Java classes implementing this interface therefore meet the
 * definition of a SISO program.
 *
 */
public interface Siso {
	/**
	 * Provides the "string in string out" (i.e. SISO) functionality required for
	 * SISO programs as described in the book.
	 * 
	 * @param inString
	 *            Input to the SISO function: "inString" is an abbreviation of
	 *            "input string."
	 * @return The result computed by the SISO function.
	 * @throws WcbcException 
	 * @throws IOException 
	 */
	String siso(String inString) throws WcbcException, IOException;
}
