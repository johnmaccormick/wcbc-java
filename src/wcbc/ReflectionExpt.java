package wcbc;

public class ReflectionExpt {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		Class<?> c = Class.forName("wcbc.ContainsGAGA");
		ContainsGAGA containsGAGA = (ContainsGAGA) c.newInstance();
		String s = containsGAGA.siso("asdf");
		System.out.println(s);
	}

}
