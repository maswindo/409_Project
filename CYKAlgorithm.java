import java.util.*;

public class CYKAlgorithm {
    static HashMap<String, ArrayList<String>> productions = new HashMap<>();
    static HashMap<String, String[]> definition;

    public static void initializeProductions() {
        // Assuming 'definition' is filled from your CNF.parseGrammar method
        for (Map.Entry<String, String[]> entry : definition.entrySet()) {
            for (String prod : entry.getValue()) {
                productions.computeIfAbsent(prod, k -> new ArrayList<>()).add(entry.getKey());
            }
        }
    }

    public static boolean runCYK(String s) {
        int n = s.length();
        ArrayList<String>[][] P = new ArrayList[n + 1][n + 1];

        // Initialize the CYK table with empty lists
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                P[i][j] = new ArrayList<>();
            }
        }

        // Base case: single letters
        for (int i = 1; i <= n; i++) {
            String terminal = s.substring(i - 1, i);
            if (productions.containsKey(terminal)) {
                P[i][1].addAll(productions.get(terminal));
            }
        }

        // Fill the table for substrings longer than 1
        for (int length = 2; length <= n; length++) { // length of the span
            for (int i = 1; i <= n - length + 1; i++) { // start of the span
                for (int k = 1; k <= length - 1; k++) { // partition of the span
                    String part1 = s.substring(i - 1, i + k - 1);
                    String part2 = s.substring(i + k - 1, i + length - 1);
                    for (String left : P[i][k]) {
                        for (String right : P[i + k][length - k]) {
                            String combined = left + right;
                            if (productions.containsKey(combined)) {
                                P[i][length].addAll(productions.get(combined));
                            }
                        }
                    }
                }
            }
        }

        // Check if the start symbol (usually 'S') can generate the entire string
        return P[1][n].contains("S");
    }

    public static void main(String[] args) {
        // Setup and example usage
        definition = CNF.definition; // Get this from your CNF class after parsing
        initializeProductions();
        String inputString = "()()"; // Example input string
        boolean canGenerate = runCYK(inputString);
        System.out.println("Can the grammar generate the string \"" + inputString + "\"? " + canGenerate);
    }
}
