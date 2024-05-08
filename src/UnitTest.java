import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UnitTest {

    @Test
    public void testEncodingDecodingIntegrity() {
        // Arrange
        HuffmanTree huffmanTree = new HuffmanTree();
        String input = "Hello, world! This is a test message.";
        
        // Act
        huffmanTree.buildTree(input);
        String encoded = huffmanTree.code(input);
        String decoded = huffmanTree.decode(encoded, encoded.length());
        
        // Assert
        assertEquals("The decoded text should match the original", input, decoded);
    }

    @Test
    public void testSpecificEncoding() {
        // Arrange
        HuffmanTree huffmanTree = new HuffmanTree();
        String input = "Hello world";
        String expectedEncoding = "01011101010110000111111000110011";
        
        // Act
        huffmanTree.buildTree(input);
        String encoded = huffmanTree.code(input);
        
        // Assert
        assertEquals("The encoded text should match the expected binary string", expectedEncoding, encoded);
    }

    @Test
    public void testDecodingWithSpecificLength() {
        // Arrange
        HuffmanTree huffmanTree = new HuffmanTree();
        String input = "Hello, world";
        
        // Act
        huffmanTree.buildTree(input);
        String encoded = huffmanTree.code(input);
        String decoded = huffmanTree.decode(encoded, 37); // Use a specific length
        
        // Assert
        assertEquals("The decoded text should match the original", input, decoded);
    }
}
