
public class CNF {
	String words;
	public CNF(String input) {
		words=input;
		System.out.println(words);
	}
	//I'm not sure this is the right way to do. 
	//We might need to make a parser
	public boolean question1() {
		boolean result=false;
		char firstChar=words.charAt(0);
		char lastChar=words.charAt(words.length()-1);
		if(firstChar==lastChar) {
			result=true;
		}
		return result;
	}
	
	public String toString() {
		return words;
	}
}
