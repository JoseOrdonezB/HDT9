import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String File = "text.txt";
    private static final String Huff = "text.huff";
    private static final String Tree = "text.tree";
    private static final HuffmanTree huffmanTree = new HuffmanTree();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int option;

            // Main menu loop
            do {
                displayMenu();
                option = getUserOption(scanner); // Get user option
                switch (option) {
                    case 1:
                        compressText();
                        break;
                    case 2:
                        decompressText();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option, please try again.");
                }
            } while (option != 3); // Repeat until user chooses to exit
        } catch (IOException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }

    private static void displayMenu() {
        System.out.println("Select an option:");
        System.out.println("1. Compress text");
        System.out.println("2. Decompress text");
        System.out.println("3. Exit");
        System.out.print("Option: ");
    }

    private static int getUserOption(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt(); // Get user input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear scanner buffer
            }
        }
    }

    private static void compressText() throws IOException {
        String text = FileReader.readFileToString(File);
        if (text == null) {
            System.out.println("Failed to read the file.");
            return;
        }

        huffmanTree.buildTree(text); // Build Huffman tree

        String encodedText = huffmanTree.code(text); // Encode the text
        System.out.println("Encoded text: " + encodedText);

        TreeSaver.saveTree(huffmanTree.getFrequencies(), Tree); // Save Huffman tree
        System.out.println("Tree saved to: " + Tree);

        FileWriter.writeEncodedToFile(encodedText, Huff); // Write encoded text to file
        System.out.println("Encoded text saved to: " + Huff);
    }

    private static void decompressText() throws IOException {
        // Load the Huffman tree from the .tree file
        HuffmanTree huffmanTree = TreeLoader.loadTree(Tree);
        if (huffmanTree == null) {
            System.out.println("Failed to load the Huffman tree.");
            return;
        }

        // Read the encoded text from the .huff file
        HFileReader.HuffmanReadResult readResult = HFileReader.readEncodedFromFile(Huff);
        if (readResult == null) {
            System.out.println("Failed to read the .huff file.");
            return;
        }

        String encodedText = readResult.encodedText;
        int bitLength = readResult.bitLength;
        System.out.println("Read encoded text: " + encodedText);

        // Decode the encoded text using the Huffman tree
        String decodedText = huffmanTree.decode(encodedText, bitLength);
        System.out.println("Decoded text: " + decodedText);
    }
}
