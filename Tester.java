import java.util.Scanner;

public class Tester {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Tester <path_to_grammar_file>");
            return;
        }

        Scanner scanner = new Scanner(System.in);  // Scanner for user input

        for (String filePath : args) {
            GrammarFileReader reader = new GrammarFileReader(filePath);
            String content = reader.readAllBytes();
            if (content == null || content.isEmpty()) {
                System.out.println("Failed to load or empty grammar file: " + filePath);
                continue;
            }

            CNF cnf = new CNF(content);
            System.out.println("Grammar from " + filePath + " has been parsed and loaded.");
            System.out.println("Loaded Grammar:\n" + cnf);

            System.out.println("Enter a string to check with the CYK algorithm (type 'exit' to stop testing this grammar):");
            while (true) {
                System.out.print("Input string: ");  // Prompt for input to make it clear that an input is expected
                String testString = scanner.nextLine();
                if (testString.equalsIgnoreCase("exit")) {
                    break; // Exit the current grammar testing loop
                }
                boolean result = cnf.CYKAlgorithm(testString); // Call CYK algorithm on CNF object
                System.out.println("Can the string '" + testString + "' be derived from the grammar? " + result);
            }
        }

        scanner.close(); // Close the scanner at the end of all testing
        System.out.println("Testing completed. Exiting program.");
    }
}
