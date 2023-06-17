/*
 * Maksym Shkola
 * 5-26-2023
 * Project #6 ; CS143 Spring
 * 
 * Description: HuffmanNode.java stores information about one character and is a 
 * helper class for HuffmanTree. This code defines a basic structure for a node
 * in a Huffman tree, which is used for constructing the tree and performing
 * Huffman encoding and decoding operations.
 * 
 */


 public class HuffmanNode implements Comparable<HuffmanNode> {

   public int character; // character representing a character
   public int frequency; // Frequency or frequency of the character
   public HuffmanNode zero; // Left child node
   public HuffmanNode one; // Right child node
   
   // default constructor
   public HuffmanNode() {
      this(0, 0, null, null); // Call another constructor with default characters
   }

   // Compare two HuffmanNode objects based on their frequency
   // Takes in HuffmanNode other as a parameter
   public int compareTo(HuffmanNode other) {
      return this.frequency - other.frequency; 
   }
   
   // HuffmanNode constructor provides a convenient way to create a new HuffmanNode
   // object by specifying the character and frequency values, while automatically
   // setting the zero and one properties to null. 
   public HuffmanNode(int character, int frequency) {
      this(character, frequency, null, null); // Call another constructor with provided characters and null child nodes
   }

   /*
    * This constructor allows you to create a new HuffmanNode object
     and initialize its character, frequency, zero, and one properties
      with the provided values. This constructor is used when constructing
       the Huffman tree, linking nodes together to form the tree structure. 
    */
   
   public HuffmanNode(int character, int frequency, HuffmanNode zero, HuffmanNode one) {
      this.character = character; // Set the character of the node
      this.frequency = frequency; // Set the frequency of the node
      this.zero = zero; // Set the left child node
      this.one = one; // Set the right child node
   }

   // Generate a string representation of the HuffmanNode
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Character: ").append(character).append("\n");
      sb.append("Frequency: ").append(frequency).append("\n");
      sb.append("Left Child: ").append(zero != null ? zero.character : "null").append("\n");
      sb.append("Right Child: ").append(one != null ? one.character : "null").append("\n");
      return sb.toString();
  }
   
   
   // isLeaf method checks if the node is a leaf node (no child nodes)
   public boolean isLeaf() {
      return zero == null && one == null; 
   }
}

