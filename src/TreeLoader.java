import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TreeLoader {

    // Method to load a Huffman tree from a file
    public static HuffmanTree loadTree(String treePath) {
        Map<Character, Integer> frequencies = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(treePath))) {
            String line;
            // Read each line of the tree file
            while ((line = reader.readLine()) != null) {
                // Check if the line contains the character and its frequency
                if (line.contains("=")) {
                    // Split the line to extract character and frequency
                    String[] parts = line.split("=");
                    char character = parts[0].charAt(0);
                    int frequency = Integer.parseInt(parts[1]);
                    frequencies.put(character, frequency);
                }
            }
        } catch (IOException e) {
            // Print error message if reading the tree file fails
            System.err.println("Error reading the tree file: " + e.getMessage());
            return null;
        }

        // Create a new HuffmanTree instance and build the tree from the loaded frequencies
        HuffmanTree huffmanTree = new HuffmanTree();
        huffmanTree.buildTreeFromFrequencies(frequencies);
        return huffmanTree;
    }
}
