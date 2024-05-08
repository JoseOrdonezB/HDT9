import java.util.*;

public class HuffmanTree implements ITree {
    private Node root; // Root node of the Huffman tree
    private Map<Character, String> huffmanCodes; // Mapping of characters to their Huffman codes
    private Map<Character, Integer> frequencies = new HashMap<>(); // Mapping of characters to their frequencies in the text

    @Override
    public void buildTree(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty.");
        }

        frequencies.clear(); // Clear the frequency map
        // Count the frequency of each character in the text
        for (char character : text.toCharArray()) {
            frequencies.compute(character, (key, value) -> (value == null) ? 1 : value + 1);
        }

        PriorityQueue<Node> queue = new PriorityQueue<>();
        // Create a priority queue of nodes based on character frequencies
        frequencies.forEach((character, frequency) -> queue.add(new Node(character, frequency)));

        // Build the Huffman tree by merging nodes
        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            Node parent = new Node(left, right); // Create a parent node with merged frequencies
            queue.add(parent);
        }

        root = queue.poll(); // Get the root node of the Huffman tree
        huffmanCodes = new HashMap<>();
        generateHuffmanCodes(root, ""); // Generate Huffman codes for each character in the tree
    }

    // Recursive method to generate Huffman codes for each character in the tree
    private void generateHuffmanCodes(Node node, String code) {
        if (node != null) {
            if (node.left == null && node.right == null) { // Leaf node
                huffmanCodes.put(node.character, code); // Assign Huffman code to the character
            }
            generateHuffmanCodes(node.left, code + "0"); // Traverse left with '0'
            generateHuffmanCodes(node.right, code + "1"); // Traverse right with '1'
        }
    }

    @Override
    public String code(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty.");
        }

        StringBuilder code = new StringBuilder();
        // Encode the text using Huffman codes
        for (char character : text.toCharArray()) {
            String huffmanCode = huffmanCodes.get(character);
            if (huffmanCode == null) {
                throw new IllegalArgumentException("No Huffman code found for character: " + character);
            }
            code.append(huffmanCode); // Append Huffman code for each character
        }
        return code.toString(); // Return the encoded text
    }

    @Override
    public String decode(String encodedText, int bitLength) {
        if (encodedText == null || encodedText.isEmpty()) {
            throw new IllegalArgumentException("Encoded text cannot be null or empty.");
        }

        StringBuilder result = new StringBuilder();
        Node current = root;

        // Decode the encoded text using the Huffman tree
        for (int i = 0; i < bitLength; i++) {
            if (current == null) {
                throw new RuntimeException("Invalid encoded text.");
            }

            current = encodedText.charAt(i) == '1' ? current.right : current.left;

            if (current.left == null && current.right == null) {
                result.append(current.character); // Append the decoded character
                current = root; // Reset to the root for the next character
            }
        }

        return result.toString(); // Return the decoded text
    }

    @Override
    public void buildTreeFromFrequencies(Map<Character, Integer> frequencies) {
        if (frequencies == null || frequencies.isEmpty()) {
            throw new IllegalArgumentException("Frequencies map cannot be null or empty.");
        }

        PriorityQueue<Node> queue = new PriorityQueue<>();
        // Create a priority queue of nodes based on frequencies
        frequencies.forEach((character, frequency) -> queue.add(new Node(character, frequency)));

        // Build the Huffman tree by merging nodes
        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            Node parent = new Node(left, right);
            queue.add(parent);
        }

        this.root = queue.poll(); // Get the root node of the Huffman tree
        this.huffmanCodes = new HashMap<>();
        generateHuffmanCodes(this.root, ""); // Generate Huffman codes for each character in the tree
    }

    @Override
    public Map<Character, Integer> getFrequencies() {
        return Collections.unmodifiableMap(frequencies); // Return unmodifiable view of frequencies map
    }

    public Map<Character, String> getHuffmanCodes() {
        return Collections.unmodifiableMap(huffmanCodes); // Return unmodifiable view of Huffman codes map
    }
}
