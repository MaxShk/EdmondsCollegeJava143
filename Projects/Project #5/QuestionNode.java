/*
 * Maksym Shkola
 * 5-19-2023
 * Project #5 ; CS143 Spring
 * 
 * Description: In the "20 Questions" guessing game, the computer attempts to
 * guess an object by asking a series of yes or no questions. The game begins
 * with the human player thinking of an object. The computer uses a binary tree
 * to keep track of questions and answers. Each node in the tree represents a question
 * or answer, with question nodes having a left "yes" subtree and a right "no" subtree,
 * and answer nodes being leaf nodes.
 * 
 * The game progresses as the computer asks the
 * player a series of questions based on the traversal of the tree.
 * The goal is for the computer to gather enough information through the questions
 * to make an educated guess about the object the player is thinking of. Once the
 * computer believes it knows the object, it makes a guess. If the guess is correct,
 * the computer wins; otherwise, the player wins.
 * 
 * It's worth noting that the game is
 * not limited to 20 questions, and the tree can have any height. The traversal of the
 * tree allows the computer to narrow down the possibilities and make more accurate guesses.
 * 
 * The QuestionTree.java The given class, QuestionNode, represents a node in a binary tree
 * used in the "20 Questions" guessing game. It has three member variables: data, left, and right.
 */


public class QuestionNode {

    public String data;
    public QuestionNode left;
    public QuestionNode right;

    /*
     * The first constructor QuestionNode(String data) initializes
     * a QuestionNode with the given data. It sets the data variable to the provided
     * data and initializes both left and right to null.
     * 
     * 
     */

    public QuestionNode(String data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    /*
     * The second constructor QuestionNode(String data, QuestionNode left,
     * QuestionNode right) initializes a QuestionNode with the given data,
     * left child, and right child.
     * It sets the data variable to the provided data and assigns the given
     * left and right nodes to the corresponding variables.
     * 
     * 
     */
    
    public QuestionNode(String data, QuestionNode left, QuestionNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    /*
     * The isLeaf() method returns a boolean value indicating whether the current node is a leaf node or not.
     */

    public boolean isLeaf() {
        return left == null && right == null;
    }
}
