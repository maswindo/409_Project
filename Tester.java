public class Tester{
	public static void main(String[] args) throws Exception {
		FileReader test1=new FileReader("src/Example.txt");
		test1.getCNF().parseGrammar();
		//System.out.println(test1.getCNF().toString());
	}
}