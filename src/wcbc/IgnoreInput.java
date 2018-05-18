package wcbc;

import java.io.IOException;

public class IgnoreInput implements Siso {

	
	
	@Override
	public String siso(String inString) throws WcbcException, IOException {
		String progString = utils.readFile(utils.prependWcbcPath("progString.txt"));
		String newInString = utils.readFile(utils.prependWcbcPath("inString.txt"));
		Universal universal = new Universal();
		String result = universal.siso(progString, newInString);
		return result;
	}
	
	public static void main(String[] args) throws IOException, WcbcException {
		String progString = utils.readFile(utils.prependWcbcPath("containsGAGA.java"));
		utils.writeFile(utils.prependWcbcPath("progString.txt"), progString);
		String inString = "CCCCCCCCCCAAGAGATT";
		utils.writeFile(utils.prependWcbcPath("inString.txt"), inString);
		
		IgnoreInput ignoreInput = new IgnoreInput();
		String result = ignoreInput.siso("asdf");
		System.out.println(result);
	}

}
