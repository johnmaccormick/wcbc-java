package wcbc;

import java.io.IOException;

public class IgnoreInput implements Siso {

	
	
	@Override
	public String siso(String inString) throws WcbcException, IOException {
		String progString = utils.readFile("progString.txt");
		String newInString = utils.readFile("inString.txt");
		Universal universal = new Universal();
		String result = universal.siso(progString, newInString);
		return result;
	}
	
	public static void main(String[] args) throws IOException, WcbcException {
		String progString = utils.readFile("containsGAGA.java");
//		utils.wr
		String inString = "CCCCCCCCCCAAGAGATT";
		if (args[0].equals("-f")) {
			progString = utils.readFile(utils.prependWcbcPath(args[1]));
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
