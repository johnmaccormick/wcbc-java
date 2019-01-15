package wcbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Utilities for the wcbc package.
 *
 */
public class utils {

	/**
	 * A random number generator that can be used by all functions that want one.
	 */
	public static Random aRandom = new Random();

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
	 * Write a file, overwriting any existing content with the given content.
	 * 
	 * @param fileName
	 *            The name of the file to be written or overwritten.
	 * @param fileContents
	 *            The contents of the file to be written, stored as a single string
	 *            that may contain newlines.
	 * @throws IOException
	 */
	public static void writeFile(String fileName, String fileContents) throws IOException {
		try (PrintWriter out = new PrintWriter(fileName)) {
			out.print(fileContents);
		}

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
	 * Check that there are at least three string argument for a SISO3 function.
	 * 
	 * @param args
	 *            The commandline arguments passed to a SISO3 program.
	 */
	public static void checkSiso3Args(String[] args) {
		if (args.length < 3) {
			System.out.println("Error: SISO3 programs require at least three string arguments.");
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

	public static String join(Collection<String> c) {
		return join(c, "");
	}

	/**
	 * Join all elements of a string collection together with a specified piece of
	 * glue between each element.
	 * 
	 * @param c
	 *            collection of strings to be joined together
	 * @param glue
	 *            string to be inserted between each item in the collection
	 * @return string of all items joined together with the specified glue between
	 *         each item
	 */
	public static String join(Collection<String> c, String glue) {
		StringBuilder sb = new StringBuilder();
		for (String s : c) {
			sb.append(s);
			sb.append(glue);
		}
		// remove the last piece of glue, if it exists
		int end;
		if (c.size() > 0) {
			end = sb.length() - glue.length();
		} else {
			end = sb.length();
		}
		return sb.substring(0, end);
	}

	public static String joinChars(Collection<Character> c) {
		return joinChars(c, "");
	}

	/**
	 * Join all elements of a Character collection together with a specified piece
	 * of glue between each element.
	 * 
	 * @param c
	 *            collection of Characters to be joined together
	 * @param glue
	 *            string to be inserted between each item in the collection
	 * @return string of all items joined together with the specified glue between
	 *         each item
	 */
	public static String joinChars(Collection<Character> c, String glue) {
		StringBuilder sb = new StringBuilder();
		for (Character s : c) {
			sb.append(s);
			sb.append(glue);
		}
		// remove the last piece of glue, if it exists
		int end;
		if (c.size() > 0) {
			end = sb.length() - glue.length();
		} else {
			end = sb.length();
		}
		return sb.substring(0, end);
	}

	public static String joinInts(Collection<Integer> c, String glue) {
		List<String> intStrings = new ArrayList<>();
		for (Integer i : c) {
			intStrings.add(i.toString());
		}
		return join(intStrings, glue);
	}

	/**
	 * Split a string using String.split(), but return an empty array on the empty
	 * string.
	 * 
	 * @param target
	 *            The string to be split
	 * @param separator
	 *            The separator to be used for splitting
	 * @return An array of the separated components
	 */
	public static String[] splitCheckEmpty(String target, String separator) {
		String[] components;
		if (target.length() == 0) {
			components = new String[] {};
		} else {
			components = target.split(separator);
		}
		return components;
	}

	/**
	 * 
	 * Encode two strings as a single string.
	 * 
	 * ESS is an acronym for Encode as Single String. This function uses the
	 * encoding method suggested in the textbook: the encoding consists of the
	 * length of the first string, followed by a space character, followed by the
	 * two strings concatenated together.
	 * 
	 * @param inString1
	 *            The first string to be encoded
	 * @param inString2
	 *            The second string to be encoded
	 * @return A single string encoding inString1 and inString2
	 */
	public static String ESS(String inString1, String inString2) {
		StringBuilder b = new StringBuilder();
		b.append(Integer.toString(inString1.length()));
		b.append(" ");
		b.append(inString1);
		b.append(inString2);
		return b.toString();
	}

	/**
	 * Decode a single string into two strings (inverse of ESS).
	 * 
	 * DESS is an acronym for DEcode from Single String. This function uses the
	 * method suggested in the textbook for converting a single string that encodes
	 * two strings back into the original two strings. DESS is the inverse of the
	 * function ESS.
	 * 
	 * 
	 * @param inString
	 *            The string to be decoded
	 * @return A 2-element array containing the two strings that were decoded from
	 *         the input.
	 */
	public static String[] DESS(String inString) {
		String[] components = inString.split(" ", 2);
		String theLengthStr = components[0];
		String remainder = components[1];
		int theLength = Integer.parseInt(theLengthStr);
		String inString1 = remainder.substring(0, theLength);
		String inString2 = remainder.substring(theLength);
		String[] inStrings = { inString1, inString2 };
		return inStrings;
	}

	public static final String alphanumericChars = "abcdefghijklmnopqstuvwxyzABCDEFGHIJKLMNOPQSTUVWXYZ0123456789";
	public static final int numAlphanumericChars = utils.alphanumericChars.length();

	public static String randomLenAlphanumericString(int maxLength) {
		int length = aRandom.nextInt(maxLength);
		return randomAlphanumericString(length);

	}

	public static String randomAlphanumericString(int length) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < length; i++) {
			b.append(utils.alphanumericChars.charAt(aRandom.nextInt(numAlphanumericChars)));
		}
		return b.toString();
	}

	public static String randomLenAlphanumericString() {
		return randomLenAlphanumericString(20);
	}

	public static void trimAll(String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			strings[i] = strings[i].trim();
		}
	}

	/**
	 * Return True if integer M is prime, and False otherwise.
	 * 
	 * This is used for testing certain functions; see e.g. factor.java. A simple,
	 * inefficient algorithm is employed.
	 * 
	 * @param M
	 *            the integer whose primality is to be tested
	 * @return true if integer M is prime, and false otherwise.
	 */
	public static boolean isPrime(int M) {
		for (int i = 2; i < M; i++) {
			if (M % i == 0) {
				return false;
			}
		}
		return true;
	}

	// global object for signaling halts to computations
	public static Object haltComputations = new Object();

	/**
	 * Enter an infinite loop, but with features that facilitate testing.
	 * 
	 * This function supposedly enters an infinite loop. The intention is that it
	 * should be used for simulating infinite loops, but in fact it is more
	 * sophisticated. The function waits on the utils.haltComputations event, and
	 * exits immediately if that event is signaled. This facilitates testing of code
	 * that deliberately enters infinite loops. In addition, this function times out
	 * after 3 seconds. This prevents background threads looping indefinitely.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public static void loop() throws WcbcException {
		// timeout is in milliseconds
		final long timeout = 3000;

		try {
			synchronized (haltComputations) {
				haltComputations.wait(timeout);
			}
		} catch (InterruptedException e) {
			throw new WcbcException("InterruptedException: " + e.getMessage());
		}
	}

	/**
	 * Exit Java, which also kills all Java threads.
	 * 
	 * This is useful for debugging and in certain other situations.
	 */
	public static void killAllThreadsAndExit() {
		System.exit(0);
	}

	public static String waitForOnePosOrAllNeg(ArrayList<Thread> threads, NonDetSolution nonDetSolution)
			throws WcbcException {
		final int maxThreads = 500;
		if (threads.size() + java.lang.Thread.activeCount() > maxThreads) {
			final String message = "Fatal error in waitForOnePosOrAllNeg: you attempted to run more than " + maxThreads
					+ " threads simultaneously.  \n" + "In theory this isn't a problem, but in practice your Python\n"
					+ "implementation may encounter difficulties. To avoid these potential\n"
					+ "problems, all threads will now be killed.";
			System.out.println(message);
			killAllThreadsAndExit();
		}

		// start each thread
		for (Thread t : threads) {
			t.start();
		}

		Thread allTerminatedThread = new Thread(new WaitAllTerminated(threads, nonDetSolution));
		allTerminatedThread.start();
		nonDetSolution.waitUntilDone();
		return nonDetSolution.getSolution();
	}

	/**
	 * Format a set of strings as a string.
	 * 
	 * The given set is returned enclosed by braces and with elements separated by
	 * commas.
	 * 
	 * Example: formatASet({"abc", "d", "ef"})
	 * 
	 * "{d,ef,abc}"
	 * 
	 * @param theSet
	 *            The set of strings to be formatted.
	 * @return A string representing theSet, enclosed by braces and with elements
	 *         separated by commas.
	 */
	public static String formatASet(Collection<String> theSet) {
		return "{" + join(theSet, ",") + "}";
	}

	/**
	 * Format a set of sets of strings as a single string.
	 * 
	 * Each set of strings is formatted using utils.formatASet(), and the resulting
	 * strings are separated by space characters.
	 * 
	 * Args:
	 * 
	 * theSets (set of frozensets of str): The set of frozensets to be formatted.
	 * 
	 * Returns:
	 * 
	 * str: A string representing theSets.
	 * 
	 * Example:
	 * 
	 * String[] set1 = {"abc", "d", "ef"}; String[] set2 = {"w", "xy", "z"};
	 * ArrayList<Collection<String>> sets = new ArrayList<>();
	 * sets.add(Arrays.asList(set1)); sets.add(Arrays.asList(set2)); String
	 * formattedSets = formatSetOfSets(sets); System.out.println(formattedSets);
	 * 
	 * {abc,d,ef} {w,xy,z}
	 * 
	 * @param theSets
	 *            The set of sets to be formatted.
	 * @return A string representing theSets.
	 */
	public static String formatSetOfSets(Collection<Collection<String>> theSets) {
		ArrayList<String> strings = new ArrayList<>();
		for (Collection<String> theSet : theSets) {
			strings.add(formatASet(theSet));
		}
		return join(strings, " ");
	}

	// public static String formatSetOfSets2(Collection<String[]> theSets) {
	// ArrayList<String> strings = new ArrayList<>();
	// for (Collection<String> theSet : theSets) {
	// strings.add(formatASet(theSet));
	// }
	// return join(strings, " ");
	// }

	public static String[] splitOnWhitespace(String s) {
		return splitCheckEmpty(s, "\\s+");
	}

	public static List<String> splitOnWhitespaceList(String s) {
		return Arrays.asList(splitOnWhitespace(s));
	}

	public static IntStream integerStreamFromString(String s) {
		List<String> intStrs = splitOnWhitespaceList(s);
		IntStream intStream = intStrs.stream().mapToInt(Integer::valueOf);
		return intStream;
	}

	/**
	 * Split the given string on whitespace, convert each component to an integer
	 * and return an array of those integers. The input must consist only of strings
	 * representing integers separated by whitespace.
	 * 
	 * @param s
	 *            The string that will be split
	 * @return an array of the integers in s
	 */
	public static int[] intArrayFromString(String s) {
		return integerStreamFromString(s).toArray();
	}

	public static List<Integer> intListFromString(String s) {
		// there must be of much better way of doing this, but let's stick with
		// something that works for now
		ArrayList<Integer> intList = new ArrayList<>();
		int[] intArray = intArrayFromString(s);
		for (Integer i : intArray) {
			intList.add(i);
		}
		return intList;
	}

	/**
	 * Split the given string on whitespace, convert each component to an integer
	 * and return the sum of those integers. The input must consist only of strings
	 * representing integers separated by whitespace.
	 * 
	 * @param s
	 *            The string that will be split and summed
	 * @return the sum of the integers in s
	 */
	public static int sumIntsInString(String s) {
		return integerStreamFromString(s).sum();
	}

	public static List<Character> geneticAlphabetAsList() {
		return new ArrayList<>(Arrays.asList('A', 'C', 'G', 'T'));
	}

	public static List<Character> asciiAlphabetAsList() {
		List<Character> ascii = new ArrayList<>();
		for (char i = 0; i < 128; i++) {
			ascii.add(new Character(i));
		}
		return ascii;
	}

	// this will be initialized lazily.
	public static List<Character> asciiAlphabet = null;

	/**
	 * Return the next string in shortlex ordering on a given alphabet.
	 * 
	 * Shortlex is an ordering that lists strings according to length, with strings
	 * of the same length being ordered lexicographically. This function takes a
	 * string on some particular alphabet as input, and returns the next string on
	 * that alphabet in the shortlex ordering.
	 * 
	 * @param s
	 *            The string whose successor will be returned.
	 * @param alphabet
	 *            A list of characters in the alphabet to be used.
	 * @return The successor of s in the shortlex ordering, assuming the given
	 *         alphabet.
	 */
	public static String nextShortLex(String s, List<Character> alphabet) {
		int alphabetLen = alphabet.size();
		Character first = alphabet.get(0);
		Character last = alphabet.get(alphabetLen - 1);
		if (s.equals("")) {
			return first.toString();
		}
		List<Character> chars = new ArrayList<>();
		for (int i = 0; i < s.length(); i++) {
			chars.add(s.charAt(i));
		}
		int L = chars.size();

		// The Boolean variable 'overflow' will indicate whether or not this
		// is the last string of the current length (and hence whether we
		// need to "overflow" to the first string with length one greater)
		boolean overflow = true;
		int i;
		Character currentChar = null;
		for (i = L - 1; i >= 0; i--) {
			currentChar = chars.get(i);
			if (!currentChar.equals(last)) {
				overflow = false;
				break;
			}

		}

		// Either we overflowed (and i=0), or we didn't overflow, in which
		// case the value of i is now the index of the rightmost character
		// that can be incremented. Let's remember all the needed
		// information about that character.
		int incrementIndex = i;
		int alphabetIndex = alphabet.indexOf(currentChar);

		if (overflow) {
			// Treat overflow as a special case and return a string of
			// length L+1 consisting entirely of the first character in the
			// alphabet.
			List<Character> result = new ArrayList<>();
			for (int j = 0; j < L + 1; j++) {
				result.add(first);
			}
			return joinChars(result);
		} else {
			// We didn't overflow, so manipulate the array of characters to
			// produce the next string in lexicographic order. The
			// rightmost character that can be incremented gets
			// incremented...
			chars.set(incrementIndex, alphabet.get(alphabetIndex + 1));
			// ...then all the characters to the right of that roll over to
			// the first character in the alphabet.
			for (int j = incrementIndex + 1; j < L; j++) {
				chars.set(j, first);
			}
			return joinChars(chars);
		}
	}

	/**
	 * Return the successor of ASCII string s in the shortlex ordering.
	 * 
	 * For a detailed explanation, see the documentation of nextShortLex(). This
	 * function is the same as nextShortLex(), for the special case where the
	 * alphabet is the ASCII alphabet.
	 * 
	 * @param s
	 *            The ASCII string whose successor will be returned.
	 * @return The successor of ASCII string s in the shortlex ordering.
	 */
	public static String nextASCII(String s) {
		if (asciiAlphabet == null) {
			asciiAlphabet = asciiAlphabetAsList();
		}
		return nextShortLex(s, asciiAlphabet);
	}

	public static void main(String[] args) throws IOException, WcbcException, InterruptedException {
		// String[] set1 = { "abc", "d", "ef" };
		// String[] set2 = { "w", "xy", "z" };
		// ArrayList<Collection<String>> sets = new ArrayList<>();
		// sets.add(Arrays.asList(set1));
		// sets.add(Arrays.asList(set2));
		// String formattedSets = formatSetOfSets(sets);
		// System.out.println(formattedSets);

		// String[] c = {"abc", "def", "ghi"};
		// ArrayList<String> cArr = new ArrayList<>(Arrays.asList(c));
		// String d = utils.join(cArr);
		// String e = utils.join(cArr, "\n");
		// System.out.println(d);
		// System.out.println(e);

		// String progString =
		// utils.readFile(utils.prependWcbcPath("containsGAGA.java"));
		// // String progString = "asdf\npublic class foo";
		// String val = utils.extractClassName(progString);
		// System.out.println(val);

		// String a = "abc";
		// String b = "defg";
		// String c = utils.ESS(a, b);
		// String[] d = utils.DESS(c);
		// System.out.println(c);
		// System.out.println(d[0]);
		// System.out.println(d[1]);

		// for (int i = 0; i < 10; i++) {
		// System.out.println(utils.randomLenAlphanumericString());
		// }

		// utils.writeFile(utils.prependWcbcPath("test.txt"), "asdf");

		// utils.loop();

		// int[] iArr = intArrayFromString("4 5 asd 6");
		// System.out.println(iArr.length);

		// System.out.println(splitOnWhitespace("").length);

		// System.out.println("".split(",").length);
		// System.out.println(splitCheckEmpty("", ",").length);

		List<Character> alphabet = new ArrayList<>(Arrays.asList('a', 'b', 'c'));
		// List<Character> alphabet = new ArrayList<>(Arrays.asList('0', '1'));
		String s = "";
		for (int i = 0; i < 20; i++) {
			System.out.println(s);
			s = nextShortLex(s, alphabet);
		}

		System.out.println(geneticAlphabetAsList());
		System.out.println(asciiAlphabetAsList());

	}

}
