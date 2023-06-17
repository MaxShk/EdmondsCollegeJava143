/*
 * Maksym Shkola
 * 4-28-2023
 * Project #3 ; CS143 Spring
 * 
 * Description: The WordGuesser class keeps track of the state of a game
 * in which the user guesses a letter in a word or phrase. Similar to Wordle or
 * the spelling bee, In our scenario, the computer will select a word, and the
 * computer user will attempt to guess the word by guessing one letter at a time
 * until the entire word is revealed. However, the number of guesses is limited.
 * This class has a default constructor, three getter methods (words, guessesLeft, guesses)
 * pattern method with three private helper methods (patternSelection and patternGenerator)
 * and a record method.
 * If the user guesses the word in under the guesses cap amount,
 * they are rewarded with a message, likewise if they do not guess under the maximum 
 * amount of guesses.
 * 
 */

import java.util.*;

public class WordGuesser {

    // Declares variables and datastructures to manipulate the state
    // of the game

    private Set<Character> guesses;
    private Set<String> words;
    private int guessesLeft;
    private String resultPattern;
    
    /*
     * Default constructor. Initializes the state of the game by using the passed
     * in values. The passed in values are: collection type words that contain the
     * full dictionary of words, the length of the word the user will guess, and
     * the maximum number of guesses allowed. The constructor throws an
     * IllegalArgumentException exception if the passed in length is less than
     * 1 or if the passed in max is less than 0.
     */

    public WordGuesser(Collection<String> words, int length, int max) throws IllegalArgumentException {
        if(length < 1 || max < 0){
            throw new IllegalArgumentException("Length has to be greater " +
            "than 1\n or max has to be greater than 0");

        }

        // instance variables
        // guesses - a TreeSet that will hold the letters guessed by the user

        // words - a TreeSet that will hold all the words in the collection
        // that have the same length as the word to be guessed

        // guessesLeft: an integer representing the number of guesses
        // the user has left, initialized to the max number of guesses allowed

        this.guesses = new TreeSet<Character>();
        this.words = new TreeSet<String>();
        this.guessesLeft = max;

        // The method then loops through each word in the
        // collection and adds it to the words TreeSet if it has the same
        // length as the word to be guessed.

        for (String word : words) {
            if (word.length() == length) {
                this.words.add(word);
            }
        }

        // The method initializes a String variable called resultPattern,
        // which will hold the current state of the word to be guessed, initially
        // set to all dashes ('-').

        resultPattern = "";

        for (int i = 0; i < length; i++) {
            resultPattern += '-';
        }
    }
    
    // - words(): a public method that returns a Set
    // of Strings representing all the words that are
    // eligible to be guessed

    public Set<String> words() {
        return this.words;
    }

    // guessesLeft(): a public method that returns an integer
    // representing the number of guesses the user has left
    
    public int guessesLeft() {
        return this.guessesLeft;
    }
    
    // guesses(): a public method that returns a Set of Characters
    // representing all the letters guessed by the user so far

    public Set<Character> guesses() {
        return this.guesses;
    }

    /*
     * The pattern method  generates a visual
     * representation of the current state of the word
     * to be guessed, with each letter represented by a dash,
     * and any correctly guessed letters revealed.
     * 
     * The pattern method throws an IllegalStateException
     * if the words treeset is empty
     * 
     * The pattern method returns a string of the current 
     * state of the word being guessed
     * 
     */
    
    public String pattern() throws IllegalStateException {
        if (words.isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
    
        /*
         * Initializes a StringBuilder called patternBuilder,
         * and loops through each character in the resultPattern String instance
         * variable, which holds the current state of the word to be guessed.
         * It appends each character to the StringBuilder followed by a space.
         */

        StringBuilder patternBuilder = new StringBuilder();
        for (int i = 0; i < resultPattern.length(); i++) {
            patternBuilder.append(resultPattern.charAt(i)).append(" ");
        }
        // sets proper length
        patternBuilder.setLength(patternBuilder.length() - 1);
        
        return patternBuilder.toString();
    }
    
    /*
     * patternSelection is a private method that takes a Map of Strings to Sets of Strings
     *  called tempPattern as its only parameter.
     * It doesn't return anything.
     * patternSelection method selects the pattern from the tempPattern map that contains
     * the most words, removes all the words from the other patterns in the
     * tempPattern map, and sets the resultPattern instance variable to the selected pattern.
     * This method is a critical step in the WordGuesser algorithm, as it narrows down
     * the possible words that the user can guess.
     */

    private void patternSelection(Map<String, Set<String>> tempPattern) {
        int max = -1;
        String result = null;
        
        /*
         * The loop below loops through each key in tempPattern, which represents a
         * possible pattern for the word to be guessed. For each key, it checks the
         * size of the corresponding set of words in the tempPattern map. If the size
         * of the set is greater than max, the max variable is updated and the result
         * variable is set to the current pattern.
         * 
         */

        for (String pattern : tempPattern.keySet()) {
            if (tempPattern.get(pattern).size() > max) {
                max = tempPattern.get(pattern).size();
                result = pattern;
            }
        }

        /*
         * The loop below loops through each key in tempPattern again,
         * and for each key that is not equal to result, it loops through
         * each word in the corresponding set and removes it from the words
         * instance variable, which is a TreeSet that holds all the possible
         * words that could be the answer.
         * 
         */
        
        for (String pattern : tempPattern.keySet()) {
            if (!pattern.equals(result)) {
                for (String word : tempPattern.get(pattern)) {
                    words.remove(word);
                }
            }
        }

        // Represents the pattern that is most likely to
        // contain the word to be guessed and returns
        resultPattern = result;
    }

    /*
     * patternGenerator is then used to narrow down the possible characters
     * that could be the answer.
     * The patternGenerator() method is called every time the user makes a
     * new guess, and it plays a key role in the iterative process of eliminating
     * possibilities and guessing the correct answer.
     * 
     * patternGenerator method takes in a char guess as input and
     * generates a mapping from possible patterns of the unknown word to
     * a set of possible words that match the pattern given the current guess.
     * 
     * does not return anything, calls patternSelection method
     */

    private void patternGenerator(char guess) {
        Map<String, Set<String>> patternToWords = new HashMap<>();

        /*
         * This code is iterating through each word in the words set, and for
         * each word, it creates a new StringBuilder object called patternBuilder
         * initialized with the resultPattern. Then it iterates over each character
         * in the word and if the character matches the guess, it sets the corresponding
         * character in the patternBuilder to the guess. 
         * 
         */

        for (String word : words) {
            StringBuilder patternBuilder = new StringBuilder(resultPattern);
            for (int i = 0; i < word.length(); i++) {
                if (guess == word.charAt(i)) {
                    patternBuilder.setCharAt(i, guess);
                }
            }

            /*
             * The resulting string is then converted
             * to a pattern variable. If patternToWords does not already contain the pattern,
             * it adds the pattern as a new key with an empty HashSet value.
             * Finally, it adds the current word to the HashSet associated with that pattern key.
             * This process generates a mapping between each possible pattern and the set of words
             * that match that pattern with the given guess.
             * 
             */


            String pattern = patternBuilder.toString();
            if (!patternToWords.containsKey(pattern)) {
                patternToWords.put(pattern, new HashSet<>());
            }
            patternToWords.get(pattern).add(word);
        }
        
        patternSelection(patternToWords);
    }
    

    /**

    Records the next guess of the user and decrements the number of guesses
    if the character guessed did not change the current pattern
    @param guess the char to be guessed
    @return the number of times the guessed character appears in the pattern
    @throws IllegalStateException if the set of words is empty or the number of
     guesses left is less than 1
    @throws IllegalArgumentException if the character being guessed was
     guessed previously

    */
    
    public int record(char guess) throws IllegalStateException, IllegalArgumentException {

        if (words().isEmpty() || guessesLeft < 1){
            throw new IllegalStateException("number of guesses left is not at \n" +
             "least 1 or the set of words is empty.");

        }
        
        if (guesses.contains(guess)){
            throw new IllegalArgumentException("previous exception was not thrown \n" +
             "and the character being guessed was guessed previously");

        }
         
        // Add the current guess to the set of previous guesses
        guesses.add(guess);
        // Generate a new pattern for the words in the set based on the current guess
        patternGenerator(guess);
        int iterator = 0;

        // Iterate through the new pattern and count the occurrences of the guess
        for (int i = 0; i < resultPattern.length(); i++) {
            if (resultPattern.charAt(i) == guess) {
                iterator++;
            }
        }
        // Decrement the number of remaining guesses if the guess does not occur in the new pattern
        if (iterator == 0) {
            guessesLeft--;
        }

        // Return the number of occurrences of the guess in the new pattern
        return iterator;
    }
    
    
    
}