package wcbc;

/**
 * Interface Siso2.java
 * 
 * The WCBC book describes the concept known as a SISO program, which stands for
 * "string in string out." Most SISO programs receive only a single parameter,
 * but sometimes we need to parameters. Siso2.java is an interface providing the
 * abstract function siso2(), which receives two String parameters and returns a
 * String object. Often, the two parameters passed to a siso2 function consist
 * of a Java program P and and input string I.
 *
 */
public interface Siso2 {
	/**
	 * Provides the 2-parameter variant of "string in string out" (i.e. SISO)
	 * functionality required for SISO programs as described in the book.
	 * 
	 * @param progString
	 *            Input to the SISO function: "progString" is an abbreviation of
	 *            "program string," because the 2-parameter siso functionality is
	 *            most often used to pass both a program P and input I. Therefore,
	 *            this parameter will often be the source code of a siso Java
	 *            program P.
	 * @param inString
	 *            Input to the SISO function: "inString" is an abbreviation of
	 *            "input string."
	 * @return The result computed by the SISO function.
	 * @throws WcbcException 
	 */
	String siso(String progString, String inString) throws WcbcException;
}
