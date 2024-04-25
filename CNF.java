import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class CNF {

	// public static TreeMap<String,ArrayList<String>> grammar = new TreeMap<>();
	static String words;// input from txt file
	// ArrayList<Character> characters = new ArrayList<>();
	// Key is Symbol and String[] is the definition. Ex: S→Ax
	static HashMap<String[],String> definition = new HashMap< String[],String>();

	public CNF(String input) {
		words = input;
		
		// System.out.println(words);
	}

	public static void parseGrammar() {

		boolean loop = true;// Loops until all the grammer is parsed
		int charStartingIndex = 0;// represents the starting index.
		int indexForTranslation = 0;// index for definitions. EX; Ax|*
		while (loop == true) {
			String startingSymbol = getTranslation(charStartingIndex);// Base symbol. Ex: S
			String translation[] = new String[10];// Represents definition Ex: a|YE|XC
			String result[];
			

			// Loop until we see \n
			for (int i = charStartingIndex; i < words.length(); i++) {
				// Store definitions to array. Ex: a YE XC
				translation[indexForTranslation] = getTranslation(charStartingIndex);
				//Update the starting index.
				charStartingIndex = findNextSpecialChar(charStartingIndex);
				//Increment the index for array of result.
				indexForTranslation++;
				//If I reach the end of txt file, end the loop.
				if (charStartingIndex == -1) {
					loop = false;
					break;
				}

				// If I'm going to read next line, end the loop.
				if (charStartingIndex != 0 && words.charAt(charStartingIndex - 1) == '\n') {

					break;
				}
			}

			// Little clean up here.
			// Since translation contains main symbols too, so I want to take them out.
			// Look for empty String and decrease the size of result by the number of empty
			// Strings.
			for (int i = 1; i < translation.length; i++) {

				if (translation[i] == "") {

					indexForTranslation--;
				}

			}
			// -1 for the first symbol.
			result = new String[indexForTranslation - 1];
			int ignoreFirst = 0;
			// Look for empty String and remove them
			for (int i = 1; i < translation.length; i++) {
				if (translation[i] != "" && translation[i] != null) {
					result[ignoreFirst] = translation[i];
					ignoreFirst++;
				}

			}
			// Printing out result
			System.out.println(startingSymbol + "→" + Arrays.toString(result));
			definition.put(result,startingSymbol);// Store the work to Hashmap
			indexForTranslation = 0;
		}
	}

	// Look for special characters space, or and new line, and return next starting index.
	// If it's the end of txt file, return -1.
	public static int findNextSpecialChar(int start) {
		for (int i = start; i < words.length(); i++) {
			if (words.charAt(i) == '→' || words.charAt(i) == ' ' || words.charAt(i) == '|' || words.charAt(i) == '\n') {
				i++;
				return i;
			}
		}
		return -1;
	}

	// Gets String from starting index to special characters.
	static String getTranslation(int start) {
		boolean loop = true;
		String result = "";
		int index = start;
		while (loop == true) {
			//Look for special characters and break. Else, store Strings to result.
			if (index == words.length() || words.charAt(index) == '→'
					|| (words.charAt(index) == ' ' || words.charAt(index) == '|' || words.charAt(index) == '\n')) {

				break;
			} else {
				result += words.charAt(index);
				index++;
			}

		}

		return result;
	}

	public String toString() {
		return words;
	}
}
