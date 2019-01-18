package wcbc;

import java.io.IOException;

/**
 * SISO program Clique.java
 * 
 * Solves the computational problem Clique. Given an input in the form "graph ;
 * K", it searches for a clique of size K and returns such a clique if it
 * exists.
 * 
 * inString: "g ; K" where g is a string representation of an unweighted,
 * undirected graph (see graph.java) and K is an integer.
 * 
 * returns: If a clique of size K exists in g, it is returned formatted as a
 * sequence of node names separated by commas. If no such clique exists, "no" is
 * returned.
 * 
 * Example:
 * 
 * > java wcbc/Clique "a,b a,c a,d b,c b,d c,d c,e b,e d,e ; 4"
 * 
 * "d,e,b,c"
 * 
 */
public class Clique implements Siso {

	@Override
	public String siso(String inString) throws WcbcException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws WcbcException, IOException {
		utils.checkSisoArgs(args);
		String inString = args[0];
		Clique clique = new Clique();
		String result = clique.siso(inString);
		System.out.println(result);
	}
	
	
}
