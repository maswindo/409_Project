public class Tester{
	public static void main(String[] args) throws Exception {
		FileReader test1=new FileReader("Example.txt");
		test1.getCNF().parseGrammar();
		FileReader test2 = new FileReader("question1.txt");
		test2.getCNF().parseGrammar();
		FileReader test3 = new FileReader("question2.txt");
		test3.getCNF().parseGrammar();
		FileReader test4 = new FileReader("question3.txt");
		test4.getCNF().parseGrammar();
		FileReader test5 = new FileReader("question4.txt");
		test5.getCNF().parseGrammar();
		//System.out.println(test1.getCNF().toString());
	}
}