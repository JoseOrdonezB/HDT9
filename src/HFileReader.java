import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class HFileReader {

    // Inner class representing the result of reading encoded text from a file
    static class HuffmanReadResult {
        String encodedText;
        int bitLength;

        HuffmanReadResult(String encodedText, int bitLength) {
            this.encodedText = encodedText;
            this.bitLength = bitLength;
        }
    }

    // Method to read encoded text from a file
    public static HuffmanReadResult readEncodedFromFile(String inputPath) {
        StringBuilder sb = new StringBuilder();
        int bitLength = 0;
        try (FileInputStream fis = new FileInputStream(inputPath);
             DataInputStream dis = new DataInputStream(fis)) {

            // Read the length of the encoded text from the file
            bitLength = dis.readInt();
            
            // Read bytes from the file and append them to a StringBuilder
            int bytesRead;
            while ((bytesRead = dis.read()) != -1) {
                // Convert the byte to binary string representation and append to StringBuilder
                sb.append(String.format("%8s", Integer.toBinaryString(bytesRead & 0xFF)).replace(' ', '0'));
            }
        } catch (IOException e) {
            // Handle the exception with more detail
            System.err.println("Error reading from file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        // Return the result containing the encoded text and its bit length
        return new HuffmanReadResult(sb.toString(), bitLength);
    }
}
