import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class TreeSaver {
    
    // Method to save a Huffman tree to a file
    public static void saveTree(Map<Character, Integer> frequencies, String outputPath) throws IOException {
        // Check for null or empty input
        if (outputPath == null || frequencies == null || frequencies.isEmpty()) {
            throw new IllegalArgumentException("Invalid input: frequencies map or output path is null or empty.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            // Sort the frequencies by key (character)
            Map<Character, Integer> sortedFrequencies = new TreeMap<>(frequencies);

            // Write the sorted frequencies to the file
            for (Map.Entry<Character, Integer> entry : sortedFrequencies.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
            // Print success message after saving the tree
            System.out.println("Tree saved to file: " + outputPath);
        } catch (IOException e) {
            // Print error message if saving the tree fails
            System.err.println("Error saving the tree to file: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw the exception
        }
    }
}
