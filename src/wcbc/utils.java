package wcbc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities for the wcbc package.
 *
 */
public class utils {
	/**
	 * Read a file, returning its contents as a single string.
	 * 
	 * @param fileName
	 *            The name of the file to be read.
	 * @return The contents of the file.
	 * @throws IOException
	 */
	public static String readFile(String fileName) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get(fileName));
		return new String(bytes);
	}

	/**
	 * Read a file, returning its contents as a single string. This is just an
	 * abbreviated name for the readFile() method.
	 * 
	 * @param fileName
	 *            The name of the file to be read.
	 * @return The contents of the file.
	 * @throws IOException
	 */
	public static String rf(String fileName) throws IOException {
		return utils.readFile(fileName);
	}

	/**
	 * Check that there is at least one string argument for a SISO function.
	 * 
	 * @param args
	 *            The commandline arguments passed to a SISO program.
	 */
	public static void checkSisoArgs(String[] args) {
		if (args.length < 1) {
			System.out.println("Error: SISO programs require a string argument.");
			System.exit(-1);
		}

	}

	/**
	 * Check that there are at least two string argument for a SISO2 function.
	 * 
	 * @param args
	 *            The commandline arguments passed to a SISO2 program.
	 */
	public static void checkSiso2Args(String[] args) {
		if (args.length < 2) {
			System.out.println("Error: SISO2 programs require at least two string arguments.");
			System.exit(-1);
		}

	}

	/**
	 * Given the source code of a SISO Java program as a string, extract the name of
	 * the class defined by the program.
	 * 
	 * @param progString
	 *            the source code of a SISO Java program as a string, extract the
	 *            name of the class defined by the program.
	 * 
	 * @return the name of the class defined by the program
	 * @throws WcbcException
	 */
	public static String extractClassName(String progString) throws WcbcException {
		final Pattern p = Pattern.compile("^public\\s+class\\s+([a-zA-Z0-9_]*)", Pattern.MULTILINE);
		Matcher m = p.matcher(progString);
		boolean b = m.find();
		if (!b) {
			throw new WcbcException("Couldn't find class name in program string.");
		}
		String className = m.group(1);
		return className;
	}

	/**
	 * Prepend "src/wcbc" to a filename in a platform-independent way.
	 * 
	 * @param fileName
	 *            The name of the file that will have a prefix prepended.
	 * @return The resulting path
	 */
	public static String prependWcbcPath(String fileName) {
		String fullName = Paths.get("src", "wcbc", fileName).toString();
		return fullName;
	}

	public static void main(String[] args) throws IOException, WcbcException {
//		String progString = utils.readFile(utils.prependWcbcPath("containsGAGA.java"));
//		// String progString = "asdf\npublic class foo";
//		String val = utils.extractClassName(progString);
//		System.out.println(val);
	}
}
