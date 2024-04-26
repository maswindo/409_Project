import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CNF {

<<<<<<< HEAD
    private final HashMap<String, Set<String[]>> grammar = new HashMap<>();

    public CNF(String input) {
        parseGrammar(input);
    }

    private void parseGrammar(String content) {
        String[] lines = content.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue; // Skip empty lines
=======
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
			String translation[] = new String[30];// Represents definition Ex: a|YE|XC
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

	static HashMap<String, ArrayList<String>> productions = new HashMap<>();


    public static void initializeProductions() {
        // Assuming 'definition' is filled from your CNF.parseGrammar method
        for (Map.Entry<String, String[]> entry : definition.entrySet()) {
            for (String prod : entry.getValue()) {
                productions.computeIfAbsent(prod, k -> new ArrayList<>()).add(entry.getKey());
>>>>>>> 8603d5053755d9e781eb23997fe41667418a2563
            }
            String[] parts = line.split("\\s+->\\s+");
            if (parts.length != 2) {
                System.out.println("Invalid production rule: " + line);
                continue;
            }
            String lhs = parts[0].trim();
            String[] rhsParts = parts[1].split("\\s*\\|\\s*"); // Split productions by "|"
            Set<String[]> productions = grammar.getOrDefault(lhs, new HashSet<>());
            for (String rhs : rhsParts) {
                String[] productionParts = rhs.trim().split("\\s+"); // Ensure productions are split into separate non-terminals
                productions.add(productionParts);
            }
            grammar.put(lhs, productions);
            System.out.println("Loaded production for " + lhs + ": " + Arrays.deepToString(productions.toArray()));
        }
    }

	public boolean CYKAlgorithm(String s) {
		int n = s.length();
		Set<String> nonTerminals = grammar.keySet();
		HashMap<String, Integer> nonTerminalIndex = new HashMap<>();
		boolean[][][] P = new boolean[n][n][nonTerminals.size()];
	
		// Populate nonTerminalIndex map
		int index = 0;
		for (String nonTerminal : nonTerminals) {
			nonTerminalIndex.put(nonTerminal, index++);
		}
	
		// Initialize CYK table for single character substrings
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			for (String nonTerminal : nonTerminals) {
				Set<String[]> productions = grammar.get(nonTerminal);
				for (String[] production : productions) {
					if (production.length == 1 && production[0].equals(String.valueOf(c))) {
						P[i][i][nonTerminalIndex.get(nonTerminal)] = true;
						System.out.println("Initialized P[" + i + "][" + i + "][" + nonTerminal + "] = true");
					}
				}
			}
		}
	
		// Debugging: Print initial CYK table
		System.out.println("Initial CYK Table:");
		printCYKTable(P, nonTerminals);
	
		// Fill table for substrings of length 2 to n
		for (int len = 2; len <= n; len++) {
			for (int i = 0; i <= n - len; i++) {
				int j = i + len - 1;
				for (int k = i; k < j; k++) {
					for (String nonTerminal : nonTerminals) {
						Set<String[]> productions = grammar.get(nonTerminal);
						for (String[] production : productions) {
							if (production.length == 2) {
								Integer B = nonTerminalIndex.get(production[0]);
								Integer C = nonTerminalIndex.get(production[1]);
								if (B != null && C != null && P[i][k][B] && P[k + 1][j][C]) {
									P[i][j][nonTerminalIndex.get(nonTerminal)] = true;
									System.out.println("Updated P[" + i + "][" + j + "][" + nonTerminal + "] = true");
                        			System.out.println("Non-terminal: " + nonTerminal);
                       				System.out.println("Indices: " + i + ", " + j);
                        			System.out.println("Production: " + Arrays.toString(production));
                    
								}
							}
						}
					}
					
				}
			}
			// Debugging: Print CYK table after each iteration
			System.out.println("CYK Table after iteration " + (len - 1) + ":");
			printCYKTable(P, nonTerminals);
		}
	
		// Debugging: Print final CYK table
		System.out.println("Final CYK Table:");
		printCYKTable(P, nonTerminals);
		// Debugging: Print final CYK table
		System.out.println("Final CYK Table for string '" + s + "':");
		printCYKTable(P, nonTerminals);

		// Check if "S" exists in the map before retrieving its value
		Integer sIndex = nonTerminalIndex.get("S");
		if (sIndex == null) {
    		throw new IllegalArgumentException("Start symbol 'S' not found in non-terminal index map.");
		}

		System.out.println("Can the string '" + s + "' be derived from the grammar? " + P[0][n - 1][nonTerminalIndex.get("S")]);

		// Check if the start symbol "S" can generate the entire string
		return P[0][n - 1][nonTerminalIndex.get("S")];



	}
	
	// Helper method to print CYK table
	private void printCYKTable(boolean[][][] table, Set<String> nonTerminals) {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				for (int k = 0; k < table[i][j].length; k++) {
					if (table[i][j][k]) {
						System.out.println("P[" + i + "][" + j + "][" + nonTerminals.toArray()[k] + "] = true");
					}
				}
			}
		}
	}
	

    public HashMap<String, Set<String[]>> getGrammar() {
        return grammar;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : grammar.keySet()) {
            sb.append(key).append(" -> ");
            Set<String[]> productions = grammar.get(key);
            for (String[] production : productions) {
                sb.append(Arrays.toString(production)).append(" | ");
            }
            sb.delete(sb.length() - 3, sb.length()); // To remove the last " | "
            sb.append("\n");
        }
        return sb.toString();
    }
}
