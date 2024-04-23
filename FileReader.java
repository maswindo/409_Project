import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.io.File;
	import java.io.IOException;
	
public class FileReader {
	

	
	public FileReader(String input) {
		
		String storage=GetAllBytes(input);
		CNF test=new CNF(storage);
		//System.out.println(test.toString());
	}
		
	public static void main(String[] args) {
		try {
				CNF temp = new CNF(args[0]);
				//temp.lex.Lex();
				//System.out.println(temp.toString());
			} catch (Exception e) {
			     System.out.println(e);
            }
			
	}
			
		
		
		//converts a file into String
		public String GetAllBytes(String file){
			
			Path myPath = Paths.get(file);
			String content="";
			try {
				content = new String(Files.readAllBytes (myPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return content;
		}
	}
		



