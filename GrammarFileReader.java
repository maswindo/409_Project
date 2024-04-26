import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class GrammarFileReader {
    private Path filePath;

    public GrammarFileReader(String filePath) {
        this.filePath = Path.of(filePath);
    }

    public String readAllBytes() {
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java GrammarFileReader <path_to_grammar_file>");
            return;
        }
        GrammarFileReader reader = new GrammarFileReader(args[0]);
        String content = reader.readAllBytes(); // Corrected, no argument passed here
        if (content != null) {
            CNF cnf = new CNF(content);
            // Optionally print the grammar to verify
            System.out.println("Grammar loaded: " + cnf.getGrammar());
        }
    }
}
