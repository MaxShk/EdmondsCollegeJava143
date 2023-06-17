/*
 * Maksym Shkola
 * 5-26-2023
 * Project #6 ; CS143 Spring
 * 
 * Description: Huffman coding is an algorithm devised by
 * David A. Huffman of MIT in 1952 for compressing text data
 * to make a file occupy a smaller number of bytes. HuffmanTree
 * represents the overall tree of character frequencies.
 * 
 * In the Huffman coding algorithm, the first step is to examine
 * the source file's contents and count the number of occurrences
 * of each character. These character frequencies are then used
 * to construct a binary tree known as the HuffmanTree.
 * 
 * Uses HuffmanNode.java that stores information about a single node
 *  
 */


import java.util.*;
import java.io.*;


public class HuffmanTree {
   
   private HuffmanNode mainRoot;

   /*
    * HuffmanTree constructor is passed an array of frequencies where count[i] is the count
      of the character with ASCII value i. These counts are used to build the Huffman
      tree using a priority queue.
    * 
    */

    public HuffmanTree(int[] counts) {
      // Create a priority queue to store Huffman nodes
      Queue<HuffmanNode> countQueue = new PriorityQueue<HuffmanNode>();
      
      // Iterate over the counts array
      int i = 0;
      while (i < counts.length) {
          // If the count for the character at index i is not zero, add a new HuffmanNode to the queue
          if (counts[i] != 0) {
              countQueue.add(new HuffmanNode(i, counts[i]));
          }
          i++;
      }
      
      // Add a special HuffmanNode to represent the end-of-file character
      countQueue.add(new HuffmanNode(counts.length, 1));
      
      // Build the Huffman tree by merging nodes until there is only one node left in the queue
      while (countQueue.size() > 1) {
          // Remove the two nodes with the lowest counts from the queue
          HuffmanNode childOne = countQueue.remove();
          HuffmanNode childTwo = countQueue.remove();
          
          // Create a new parent node with count equal to the sum of the child counts
          // The parent node has a value of 0 and its children are the two removed nodes
          countQueue.add(new HuffmanNode(0, childOne.frequency + childTwo.frequency, childOne, childTwo));
      }
      
      // The last remaining node in the queue is the root of the Huffman tree
      mainRoot = countQueue.remove();
    }
  
/*
 * The decode method reads individual bits from the input stream and writes
 *  the corresponding characters to the output. Stops reading when it encounters a
 *  character with value equal to the eof parameter.
 * 
 * Parameters:
 *    BitInputStream input
 *    PrintStream output
 *    int eof
 */


    public void decode(BitInputStream input, PrintStream output, int eof) {
        // Decode the input stream using the Huffman tree and write the decoded values to the output stream
        
        // Start decoding by reading the initial value
        int currValue = decode(input, mainRoot);

        // Continue decoding until the current value is equal to the end-of-file marker
        while (currValue != eof) {
            // Write the current value to the output stream
            output.write(currValue);
            
            // Read the next value for decoding
            currValue = decode(input, mainRoot);
        }
    }
  
   /*
    * The writeHelper method is used in the context of Huffman encoding.
    * It performs a recursive traversal of the Huffman tree and writes the character
    * values and their corresponding binary codes to the output stream.
    *
    * Parameters: PrintStream output, HuffmanNode exactRoot, String formBinary
    *
    */
   
    private void writeHelper(PrintStream output, HuffmanNode exactRoot, String formBinary) {
        // If the current root is a leaf node
        if (exactRoot.isLeaf()) {
            // Write the character and its binary code to the output stream
            output.println(exactRoot.character);
            output.println(formBinary);
        }
        else {
            // Recursively traverse the left (zero) branch of the tree
            writeHelper(output, exactRoot.zero, formBinary += "0");
            
            // Remove the last added binary digit from the formBinary string
            formBinary = formBinary.substring(0, formBinary.length() - 1);
    
            // Recursively traverse the right (one) branch of the tree
            writeHelper(output, exactRoot.one, formBinary += "1");
        }
    }
    

    /*
     * TreeBuilderHelper private method builds a Huffman tree by recursively traversing
     * through the binary code for each character. The root parameter represents the current
     * node being processed, and character is the value of the character. The formBinary
     * parameter is a binary string representing the path to the desired position
     * of the character in the Huffman tree.
     * 
     */

     private HuffmanNode TreeBuilderHelper(HuffmanNode root, int value, String formBinary) {
        // If the current root is null, create a new HuffmanNode
        if (root == null) {
            root = new HuffmanNode();
        }
        
        // If there are no more binary digits left, create a leaf node with the given value and count of 0
        if (formBinary.isEmpty()) {
            return new HuffmanNode(value, 0);
        } else {
            // If the first binary digit is '0', recursively build the tree by traversing the left (zero) branch
            if (formBinary.charAt(0) == '0') {
                root.zero = TreeBuilderHelper(root.zero, value, formBinary.substring(1));
            }
            // If the first binary digit is '1', recursively build the tree by traversing the right (one) branch
            else {
                root.one = TreeBuilderHelper(root.one, value, formBinary.substring(1));
            }
            
            // Return the root node
            return root;
        }
    }
    
  

  /*
   * This HuffmanTree constructor reconstructs a tree from a code file.
   * The passed in values are Scanner input
   * 
   * Reads input data containing character frequencies and binary representations,
   * and it constructs a Huffman tree based on that data
   * 
   */

   public HuffmanTree(Scanner input) {
      while (input.hasNext()) {
         mainRoot = TreeBuilderHelper(mainRoot, Integer.parseInt(input.nextLine()), 
                       input.nextLine());
      }
    }

   /*
    * The private decode method is a recursive method used to decode a binary message
    * using a Huffman tree. It takes a BitInputStream object and the root of the
    * Huffman tree as parameters.
    *
    * Parameters: BitInputStream input, HuffmanNode root
    *
    * Returns: The decode method returns an integer value representing
    * the decoded character from the binary message.
    */
   
   // Method to decode the input stream using the Huffman tree
    private int decode(BitInputStream input, HuffmanNode root) {
        // Check if the current node is a leaf node
        if (root.isLeaf()) {
            // If it is a leaf node, return the character stored in the node
            return root.character;
        }
        
        else {
            // If it is not a leaf node, read a bit from the input stream
            int bit = input.readBit();
            if (bit == 0) {
                // If the bit is 0, recursively decode using the left child node
                return decode(input, root.zero);
            }
            
            else {
                // If the bit is 1, recursively decode using the right child node
                return decode(input, root.one);
            }
        }
    }


  /*
   * The write() method writes the tree to the given output stream in the standard format.
   * 
   * The passed in value is the PrintStream output
   * 
   * Calls writeHelper method to write the binary representation of each character
   *  in a Huffman tree to a PrintStream output
   */

   public void write(PrintStream output) {
    // Initialize an empty string to store the binary representation
      String formBinary = "";
      // Call the helper method to recursively write the binary representation to the output
      writeHelper(output, mainRoot, formBinary);
    }
   
}