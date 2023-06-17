/*
 * Maksym Shkola
 * 5-11-2023
 * Project #4 ; CS143 Spring
 * 
 * Description: The program manages a game of tag played on
 *  college campuses where each player has a target to tag.
 *  Initially, players only know who they are targeting, and as
 *  they tag someone, they get a new target. The game is played
 *  by creating a linked list of tag targets randomly, and when
 *  someone is tagged, the links are rearranged to skip them.
 *  The game ends when there is only one player left in the tag ring.
 *  The TagManager class keeps track of who is trying to tag whom and the history
 *  of who tagged whom by maintaining two linked lists: the tag ring and the out
 *  list. The client program called TagMain reads a file of names, shuffles them,
 *  and constructs a TagManager object to carry out the tasks involved in administering
 *  the game until there is only one player left in the game.
 * 
 */



 import java.util.*;

 /*
  * Constructor TagManager initializes a new tag manager 
  * over the given list of people. Constructs a tag ring 
  * full of linked nodes.
  * 
  * Takes in players parameter and generates a new tag ring
  * 
  * An IllegalArgumentException is thrown if the list is null or has a size of 0
  * 
  * 
  */
 
  public class TagManager {
     private TagNode tagRing; // head of the circular linked list of players in the tag ring
     private TagNode outList; // head of the linked list of players who are out of the game
     
     public TagManager(List<String> players) throws IllegalArgumentException {
         if (players == null || players.size() == 0){ // check if the list is null or empty
             throw new IllegalArgumentException(); // throw an exception if it is
 
         }
 
         // create the tag ring by initializing the head of the circular linked list
         tagRing = new TagNode(players.get(0)); // set the name of the first player
         TagNode temp = tagRing; // create a temporary node and set it to the head
 
         // loop through the list of players and add each one to the tag ring
         for (int i = 1; i < players.size(); i++) {
             temp.next = new TagNode(players.get(i)); // create a new node with the name of the player
             temp = temp.next; // move the temporary node to the next node in the linked list
         }
     }
 
     /*
      * printTagRing does not take any parameters or return any values
      * 
      * this method prints the names of the people in the tag
      *  ring, one per line, indented by two spaces, as
      *  "name is trying to tag name".
      *  The behavior is unspecified if the game is over.
      * 
      */
     
     public void printTagRing() {
         //The method starts by initializing a temporary TagNode variable
         // to the head of the tag ring.
         TagNode temp = tagRing;
 
         //The while loop iterates over the tag ring until it
         // reaches the last element, and for each iteration,
         // it prints out the current node's name and the name of the next node
         // it is trying to tag.
 
         while (temp.next != null) {
             System.out.println(temp.name + " is trying to tag " + temp.next.name);
             temp = temp.next;
         }
 
         //After the loop ends, the last node is printed out as trying to
         // tag the head of the tag ring, which completes the circular nature of the linked list.
         System.out.println(temp.name + " is trying to tag " + tagRing.name);
     }
     
     /*
      * printOutList does not take any parameters and does not return any values
      * 
      * This method prints the names of the people in the outlist, one per
      *  line, with each line indented by two spaces, with output of the
      *  form "name was tagged by name". The method prints the names
      *  in the opposite of the order in which they were tagged
      *  (most recently tagged first, then next more recently tagged, and so on).
      *  printOutList produces no output if the outlist is empty. 
      */
 
      public void printOutList() {
         // Check if the out list is empty or null
         if (outList == null) {
             return; 
         }
         
         // Iterate over the out list and print each name along with the name of the person who tagged them
         TagNode temp = outList; // Start at the head of the out list
         while (temp != null) { // Continue until the end of the out list is reached
             System.out.println("  " + temp.name + " was tagged by " + temp.tagger);
             temp = temp.next; // Move to the next node in the outlist
         }
     }
 
     /*
      * isGameOver method returns "true" if the game is over
      * No passed in parameters
      */
 
     public boolean isGameOver() {
         return tagRing == null || tagRing.next == null;
     }
 
     /*
      *  tagRingContains returns true if the given name is in the current tag ring
      *  and false otherwise. The method ignores case in comparing names
      * 
      * no passed in parameters
      * 
      */
 
     public boolean tagRingContains(String name) {
         // calls findNodeByName to get a condition
         return findNodeByName(tagRing, name) != null;
     }

     /*
      * Private helper method that Returns the first
      * occurrence of a TagNode with the specified name in a linked list of TagNodes
      *
      * Takes in the following parameter values:
      *     1) the head of the linked list of TagNodes to search
      *     2) the name of the TagNode to find (case-insensitive)
      *
      * Returns the first TagNode in the linked list with the specified name, or null if not found
      */
     
      private TagNode findNodeByName(TagNode list, String name) {
        TagNode current = list;
        while (current != null) {
            if (name.equalsIgnoreCase(current.name)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
 
     /*
      * outListContains method returns true if the given name is in the current out list
      *  and false otherwise. The method ignores case in comparing names
      * 
      * name passed in parameter to check if name is in the current out list
      */
 
     public boolean outListContains(String name) {
         // calls findNodeByName to get a condition
         return findNodeByName(outList, name) != null;
     }
 
     /*
      * 
      * Winner method returns the name of the winner of the game, or null if the game is not over.
      * 
      * No passed in parameters
      */
 
     public String winner() {
         // checks if the game is over by calling the isGameOver method and comparing the output
         if (!isGameOver()) {
             return null;
         }
         return tagRing.name;
     }
 
     /*
      * The tag method records the tagging of the person with the passed in name,
      *  transferring the person from the tag ring to the front of the out list.
      *  This operation does not change the relative order of the tag ring
      *  (i.e., the links of who is trying to tag whom should stay the same
      *  other than the person who is being tagged/removed).
      * 
      * Ignores case in comparing names
      * 
      * Throws an IllegalStateException if the game is over, or an
      *  IllegalArgumentException if the given name is not part of the tag ring
      *  (if both of these conditions are true, the IllegalStateException takes precedence).
      * 
      * The parameter value passed is String name, and the method does not return anything
      * 
      */
     
      public void tag(String name) {

        // Generates current and previous tagNode to 
        // find person in the tag ring
        TagNode current = findNodeByName(tagRing, name);
        TagNode prev = tagRing;


        // Throw an exception if the game is over
        if (isGameOver()) {
            throw new IllegalStateException("The game is over.");
        }
        // Throw an exception if the person is already out
        if (outListContains(name)) {
            throw new IllegalArgumentException(name + " is already in the output list.");
        }
        

        // lines 198 -> 210 find the person to tag in the tag ring
        while (current != prev.next && prev.next != null) {
            prev = prev.next;
        }
    
        current.tagger = prev.name;
    
        // If the person to be tagged is the current "it" person
        // Update the "it" person to the next person in the tag ring
        if (tagRing.name.equalsIgnoreCase(name)) {
            tagRing = tagRing.next;
        } 
        
        else {
            prev.next = current.next; // Remove the person to be tagged from the tag ring
        }
    
        current.next = outList;
        outList = current;
        // add person to the outlist
    }
    
 
 }
 