package wcbc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * SISO program Universal.java
 * 
 * This is a universal Java program -- that is, a Java program that executes
 * other SISO Java programs. Given a program P and input I, universal.java
 * returns P(I), provided that P(I) is defined. As an extra convenience when
 * running from the command line, if the first argument is "-f" then the
 * following argument will be interpreted as a filename whose contents should be
 * used as the string P. For ease of implementation and simplicity,
 * Universal.java also has a significant restriction: it will only execute SISO
 * Java programs P that already exist in the wcbc Java package. So, if the
 * parameter progString is not precisely the same as the source of one of the
 * Java files in the wcbc source directory, Universal.java will throw an
 * exception. Note that it is not too difficult to write a truly universal Java
 * program. The author has done it and tested it using
 * net.openhft.compiler.CompilerUtils. However, this approach requires several
 * additional libraries to be installed and was therefore not suitable for the
 * self-contained wcbc package.
 * 
 * progString: a SISO Java program P stored as an ASCII string.
 * 
 * inString: an ASCII string I to be used as input to P.
 * 
 * returns: If the string P(I) is defined, it is returned. (Recall that P(I) is
 * the output of program P on input I.)
 * 
 * Example:
 * 
 * > java wcbc/universal -f containsGAGA.java CCGAGACC
 * 
 * yes
 */
public class Universal implements Siso2 {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso2#siso(java.lang.String)
	 */
	@Override
	public String siso(String progString, String inString) throws WcbcException {
		// Determine the name of the SISO class defined in progString
		String className = utils.extractClassName(progString);

		// Check that the SISO class exists in the wcbc package. We do this because of
		// the restriction mentioned above in the documentation for this class. A truly
		// universal program would not enforce this restriction, but it keeps our
		// implementation simple.
		checkMirrorsExistingClass(className, progString);

		// So far we have the name of the desired class as a String. Now we convert it
		// into a reference to a Java data type.
		Class<?> progClass;
		try {
			progClass = Class.forName("wcbc." + className);
		} catch (ClassNotFoundException e) {
			String msg = "The Java class " + className + " couldn't be found, "
					+ "so our simple implementation of universal computation will not succeed.";
			throw new WcbcException(msg);
		}

		// Create an instance of the desired class.
		Object progObject;
		try {
			progObject = progClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			String msg = "The Java class " + className + " caused an unexpected problem during instantiation, "
					+ "so our simple implementation of universal computation will not succeed.";
			throw new WcbcException(msg);
		}

		// Obtain a reference to the siso() method of the desired class.
		Method sisoMethod;
		try {
			sisoMethod = progClass.getMethod("siso", String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			String msg = "The Java class " + className
					+ " caused an unexpected problem during extraction of the siso() method, "
					+ "so our simple implementation of universal computation will not succeed.";
			throw new WcbcException(msg);
		}

		// Invoke the desired siso() method and store the returned string.
		String s;
		try {
			s = (String) sisoMethod.invoke(progObject, inString);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			String msg = "The Java class " + className
					+ " caused an unexpected problem during invocation of the siso() method, "
					+ "so our simple implementation of universal computation will not succeed.";
			throw new WcbcException(msg);
		}

		return s;
	}

	// Check that the SISO class exists in the wcbc package. See the comment above,
	// where this method is invoked, for a more detailed explanation.
	private void checkMirrorsExistingClass(String className, String progString) throws WcbcException {
		String fileName = className + ".java";
		String fullName = fileName;
		String fileContents;
		try {
			fileContents = utils.readFile(fullName);
		} catch (IOException e) {
			String msg = "The Java file " + fileName + " couldn't be found in the wcbc directory, "
					+ "so our simple implementation of universal computation will not succeed.";
			throw new WcbcException(msg);
		}
		if (!progString.equals(fileContents)) {
			String msg = "The program string is not the same as the contents of the corresponding Java file, "
					+ "so our simple implementation of universal computation will not succeed.";
			throw new WcbcException(msg);
		}
	}

	public static void main(String[] args) throws IOException, WcbcException {
		utils.checkSiso2Args(args);
		String progString = "";
		String inString = "";
		if (args[0].equals("-f")) {
			progString = utils.readFile(args[1]);
			inString = args[2];
		} else {
			progString = args[0];
			inString = args[1];
		}
		Universal universal = new Universal();
		String result = universal.siso(progString, inString);
		System.out.println(result);
	}

}
