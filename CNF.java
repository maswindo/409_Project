import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CNF {

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
