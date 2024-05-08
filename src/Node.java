public class Node implements Comparable<Node> {
    char character; // Character stored in the node
    int frequency; // Frequency of the character
    Node left; // Left child node
    Node right; // Right child node

    // Constructor for a leaf node with a character and its frequency
    public Node(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null; // Leaf nodes have no children
        this.right = null; // Leaf nodes have no children
    }

    // Constructor for an internal node, combining two child nodes
    public Node(Node left, Node right) {
        this.frequency = left.frequency + right.frequency; // Combined frequency of the two children
        this.left = left; // Set the left child
        this.right = right; // Set the right child
    }

    // Compare nodes based on their frequencies
    @Override
    public int compareTo(Node that) {
        return this.frequency - that.frequency; // Compare frequencies
    }
}
