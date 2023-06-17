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
 * The QuestionTree.java manages the binary tree of the 20 questions game
 */



import java.io.*;
import java.util.*;

public class QuestionTree {

    // Private variables

    private QuestionNode treeRoot;
    private UserInterface ui;
    private int totalGames;
    private int gamesWon;
    
    /*
     * QuestionTree constructor initializes a new question tree. The parameter
     * is an object representing the user interface for input/output (UserInterface ui).
     * The tree will use this user interface for printing output messages and asking
     * questions in the game. Initially the tree starts out containing only a single
     * answer leaf node with the word "computer" in it. The tree will grow larger as
     * games are played or as a new tree is loaded with the load method
     * 
     * Parameters: UserInterface ui
     * 
     * Throws: IllegalArgumentException
     */

    public QuestionTree(UserInterface ui) throws IllegalArgumentException {
        if (ui == null) {
            throw new IllegalArgumentException();
        }
        // The UserInterface is used for input/output in the game.
        this.ui = ui;

        // The totalGames and gamesWon variables are initialized to 0.
        totalGames = 0;
        gamesWon = 0;

        // The treeRoot is set to a new QuestionNode with the initial answer "computer".
        treeRoot = new QuestionNode("computer");
    }
    
    /*
     * The play() method plays one complete guessing game with the user,
     * asking yes/no questions until reaching an answer object to guess.
     * 
     * A game begins with the root node of the tree and ends upon reaching
     * an answer leaf node. If the computer wins the game, print a message
     * saying so. Otherwise your tree must ask the user what object he/she
     * was thinking of, a question to distinguish that object from the player's
     * guess, and whether the player's object is the yes or no answer for that question. 
     * 
     * After the game is over, the provided client program will prompt the user whether or not to play again
     * 
     * Parameters: none
     * Calls: playHelper(): play helper method
     * 
     */
 

    public void play() {
        treeRoot = playHelper(treeRoot); //calls the play() helper method with the tree Root as the parameter

        // increments the total games played
        totalGames++;
    }

    /*
     * The toSave() method is a private helper method, a recursive method that is
     * responsible for saving the question tree to a file using a PrintStream object.
     * It traverses the tree and writes the tree nodes to the output file in a specific format.
     * 
     * Parameters: QuestionNode treeRoot
     *             PrintStream output
     */

    private void toSave(QuestionNode treeRoot, PrintStream output) {
        // Check if the current node is not null
        if (treeRoot != null) {
            // If the current node is a leaf node (contains an answer)
            if (treeRoot.isLeaf()) {
                // Write the answer to the output file in the format "A:<answer>"
                output.println("A:" + treeRoot.data);
            }
            else {
                // If the current node is a question node
                // Write the question to the output file in the format "Q:<question>"
                output.println("Q:" + treeRoot.data);
            }
            
            // Recursively call toSave for the left child of the current node
            toSave(treeRoot.left, output);
            
            // Recursively call toSave for the right child of the current node
            toSave(treeRoot.right, output);
        }
    }
    

    /*
     * totalGames() method returns the total number of games played
     * 
     */
    public int totalGames() {
        return totalGames;
    }

    /*
     * gamesWon() method returns the total number of games won
     * 
     */
    
    public int gamesWon() {
        return gamesWon;
    }

    /*
     * The lostHelper method is a private helper method that handles the case when
     * the computer loses the guessing game. It is responsible for updating the
     * question tree based on the user's correct answer and providing a new question
     * to distinguish the user's object from the computer's incorrect guess.
     * 
     * Parameters: QuestionNode node
     * 
     * Returns: node
     * 
     * 
     */

    private QuestionNode lostHelper(QuestionNode node) {
        ui.print("I lose. What is the name of your object? ");
        QuestionNode answer = new QuestionNode(ui.nextLine());
        ui.print("Please type a yes/no question to distinguish your item from " + node.data + ": ");
        String question = ui.nextLine();
        ui.print("And what is the answer for your object? ");
        boolean isTrue = ui.nextBoolean();
        
        if (isTrue) {
            node = new QuestionNode(question, answer, node);
        } else {
            node = new QuestionNode(question, node, answer);
        }
        
        return node;
    }

    /*
     * The playHelper() method is a private helper method responsible
     * for playing the guessing game by interacting with the user through
     * the user interface (ui). It takes a QuestionNode parameter node
     * representing the current node in the question tree.
     * 
     * The playHelper method uses recursion to traverse the question tree and continue playing the guessing game.
     * 
     * 
     * 
     * 
     */
    
    
    private QuestionNode playHelper(QuestionNode node) {
        
        /*
         *  First checks if the current node is a leaf node by calling
         *  the isLeaf() method on it. If it is a leaf node, the method
         *  proceeds to handle the outcome of the guess.
         * 
         */

         // base case
        if (node.isLeaf()) {
            ui.print("Would your object happen to be a " + node.data + "?");

            if (ui.nextBoolean()) {
                ui.println("I win!");
                gamesWon++;
            }
            
            else {
                node = lostHelper(node);
            }
        } 

        /*
         * The recursion continues as long as there are more nodes to traverse
         * in the question tree. Each recursive call of playHelper handles the
         * next question or guess based on the user's responses.
         * 
         */
        
        else {
            ui.print(node.data);
            if (ui.nextBoolean()) {
                node.left = playHelper(node.left);
            }
            
            else {
                node.right = playHelper(node.right);
            }
        }


        return node;
    }
    
    /*
     * The save() method stores the current tree state to an output file represented
     * by the given PrintStream.
     * 
     * The current question tree grows each time the user runs the program. A tree is
     * specified by a sequence of lines, one for each node. Each line starts with
     * either Q: to indicate a question node or A: to indicate an answer (a leaf).
     * All characters after these first two represent the text for that node
     * (the question or answer). The nodes appear in the order produced by a
     * preorder traversal of the tree.
     * 
     * Parameters: PrintStream output
     * Throws: IllegalArgumentException
     * Calls: helper method toSave with the following parameters: treeRoot, output
     * 
     */

    public void save(PrintStream output) throws IllegalArgumentException {
        if (output == null) {
            throw new IllegalArgumentException();
        }
        // Calls toSave() helper method to save data
        toSave(treeRoot, output);
    }

    /*
     * toLoad() is a helper method for load(). The method responsible
     * for loading a QuestionNode tree from a Scanner input. It takes two
     * parameters: treeRoot, which represents the root of the current subtree being loaded,
     * and input, which is the Scanner object used to read input lines.
     * 
     * The toLoad() method uses recursion to recursively load the
     * left and right subtrees of the current treeRoot node.
     * 
     * returns: treeRoot
     * 
     */

    private QuestionNode toLoad(QuestionNode treeRoot, Scanner input) {
        //The method first checks if there is a next line available
        // in the input. If there isn't, it returns null, indicating
        // the end of the subtree loading process.
        if (!input.hasNextLine()) {
            return null;
        }

        // If there is a next line available, it reads the line and
        // creates a new QuestionNode with its data set to the substring
        // of the line starting from the 2nd index. This represents either
        // a question or an answer for the current node.

        String line = input.nextLine();
        treeRoot = new QuestionNode(line.substring(2));

        // If the line starts with "A:", indicating an answer node,
        // the method returns the newly created treeRoot as a leaf node.
        // This acts as the base case for the recursion,
        if (line.startsWith("A:")) {
            return treeRoot;
        }

        //If the line does not start with "A:", indicating a question node,
        // the method continues with the recursive process.
        // It recursively calls itself to load the left subtree and assigns the returned value.
        // Then it recursively calls itself again to load the right subtree and assigns
        // the returned value to treeRoot.right

        else {
            treeRoot.left = toLoad(treeRoot.left, input);
            treeRoot.right = toLoad(treeRoot.right, input);
        }
        return treeRoot;
    }
    
    /*
     * The load() method replaces the current tree by reading another tree from a file.
     * The method passes a Scanner that reads from a file and replaces the current
     * tree nodes with a new tree using the information in the file.
     * 
     * Parameters: Sanner input
     * Throws: IllegalArgumentException
     * Calls: toLoad() which is a load() helper method  
     * 
     * 
     */
    
    public void load(Scanner input) throws IllegalArgumentException {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        // calls toLoad() helper method with treeRoot and input as th parameters
        treeRoot = toLoad(treeRoot, input);
    }
    

}