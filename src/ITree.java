import java.util.Map;

public interface ITree {
    void buildTree(String text);

    String code(String text);

    String decode(String encodedText, int bitLength);

    void buildTreeFromFrequencies(Map<Character, Integer> frequencies);

    Map<Character, Integer> getFrequencies();
}
