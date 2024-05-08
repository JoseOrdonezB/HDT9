import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {

    public static void writeEncodedToFile(String encodedText, String outputPath) {
        // Check if input parameters are valid
        if (encodedText == null || encodedText.isEmpty() || outputPath == null || outputPath.isEmpty()) {
            System.err.println("Invalid input: encodedText or outputPath is null or empty.");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(outputPath);
             DataOutputStream dos = new DataOutputStream(fos)) {

            // Write the length of the encoded text to the file
            writeBitLength(encodedText.length(), dos);

            // Write the encoded text to the file
            writeEncodedText(encodedText, dos);

            // Print a message indicating successful write
            System.out.println("Encoded text written to file: " + outputPath);

        } catch (IOException e) {
            // Handle the exception with more detail
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to write the length of the encoded text to the file
    private static void writeBitLength(int bitLength, DataOutputStream dos) throws IOException {
        dos.writeInt(bitLength);
    }

    // Method to write the encoded text to the file
    private static void writeEncodedText(String encodedText, DataOutputStream dos) throws IOException {
        int bitBuffer = 0;
        int bitCount = 0;

        for (int i = 0; i < encodedText.length(); i++) {
            bitBuffer = (bitBuffer << 1) | (encodedText.charAt(i) == '1' ? 1 : 0);
            bitCount++;

            if (bitCount == 8) {
                // Write a byte to the file when the bit count reaches 8
                dos.write(bitBuffer);
                bitBuffer = 0;
                bitCount = 0;
            }
        }

        // Write any remaining bits to the file
        if (bitCount > 0) {
            bitBuffer <<= (8 - bitCount);
            dos.write(bitBuffer);
        }
    }
}
