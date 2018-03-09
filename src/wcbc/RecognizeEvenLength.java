package wcbc;

/**
 * SISO program RecognizeEvenLength.java
 * 
 * This program recognizes, but does not decide, the language of strings of even
 * length. Given an input of even length, it will return "yes". Otherwise, it
 * enters an infinite loop.
 * 
 */
public class RecognizeEvenLength implements Siso {

	/*
	 * (non-Javadoc)
	 * 
	 * @see wcbc.Siso#siso(java.lang.String)
	 */
	@Override
	public String siso(String inString) {
		int i = 0;
		while (true) {
			if (inString.length() == i) {
				return "yes";
			} else {
				i = i + 2;
			}
		}
	}

	public static void main(String[] args) {
		utils.checkSisoArgs(args);
		String inString = args[0];
		RecognizeEvenLength recognizeEvenLength = new RecognizeEvenLength();
		String result = recognizeEvenLength.siso(inString);
		System.out.println(result);
	}

}
