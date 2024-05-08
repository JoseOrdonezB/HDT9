import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {

    // Reads the contents of a file to a string
    public static String readFileToString(String filePath) throws IOException {
        byte[] encoded;
        try {
            // Read all bytes from the file
            encoded = Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            // If an IOException occurs during reading, throw a new IOException with a descriptive message
            throw new IOException("Failed to read file: " + e.getMessage());
        }
        // Convert the byte array to a string using the detected file encoding
        return new String(encoded, detectFileEncoding(filePath));
    }

    // Detects the encoding of the file
    private static Charset detectFileEncoding(String filePath) throws IOException {
        // Detect file encoding using NIO Files.probeContentType
        String contentType = Files.probeContentType(Paths.get(filePath));
        if (contentType != null && contentType.toLowerCase().contains("charset=")) {
            // If the content type contains a charset, extract and return the charset
            String encoding = contentType.substring(contentType.indexOf("charset=") + 8);
            return Charset.forName(encoding);
        }
        // Default to UTF-8 if encoding detection fails
        return StandardCharsets.UTF_8;
    }
}
