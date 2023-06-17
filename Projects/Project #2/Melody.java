/*

Maksym Shkola
4/19/2023
Project #2 ; CS143 Spring

Description: This programming assignment is developing a Melody
class to represent a song, which is made up of a number of notes.
Each line in a text file representing a single note is used as the
input. The Melody class offers a number of methods that enable
functionality akin to that of an MP3 player, including playing,
adding, reversing, and adjusting tempo. The difficulty lies in
handling repeated parts of notes appropriately, making sure
that redundant pieces of notes are only kept once and accurately played back.
*/


import java.util.*;

// Main melody class that provides the media options.
// Inlcudes the following methods: getTotalDuration, toString, changeTempo,
// reverse, append, and play
public class Melody{

    private Queue<Note> qNote = new LinkedList<Note>();

    // Default constructor
    // Once a song is loaded into the app, the song gets saved in the qNote queue
    // the qNote queue gets manipulated in the methods to achieve mp3 functionality
    // that the user input specifies

    public Melody(Queue<Note> song){
        // If the queue is empty, throw an IllegalArgumentException saying that
        // the song cannot be null

        if(song.isEmpty()){
            throw new IllegalArgumentException("Song cannot be null");
        }
        // Otherwise save the song into qNote queue
        qNote = song;

    }

    // getTotalDuration returns the song's duration in seconds.
    // If there are repeating notes in the song, the method includes the
    // repeating notes

    public double getTotalDuration(){
        double length = 0.0;
        if(length != 0){
            return length;
        }
        // temporary queue that will be used to revert the original queue
        // (qNote) back to its original state
        Queue<Note> tempQueue = new LinkedList<Note>();

        // The while loop that this code is a part of runs as long as the queue
        // "qNote" is not empty. During the loop, the code uses the remove() method
        // to remove the first element from the queue and assigns it to a temporary
        // variable called "tempNote".

        while(!qNote.isEmpty()){
            Note tempNote = qNote.remove();
            // The duration of the note is retrieved using the getDuration()
            // method of the Note object, and the duration is added to a variable called "length".
            length += tempNote.getDuration();
            //the temporary note is added to another queue called "tempQueue" using the add() method.
            tempQueue.add(tempNote);
        }
        // Reverts qNote back to its original state using the temporary queue
        while(!tempQueue.isEmpty()){
            qNote.add(tempQueue.remove());
         }
        return length;
    } 

    // toString method returns a String that contains details about each note.
    // Outputs each note on its own line.
    // Accurately reflects the modifications made to the music (qNote).

    @Override
    public String toString(){
        String newString = "";

        //creates a temporary queue to iterate over the notes in the original queue without removing them.
        Queue<Note> tempQueue = new LinkedList<Note>();
        //The notes in the original queue are removed one by one, and their String representation is appended to the string builder.
        while(!qNote.isEmpty()){
            Note tempNote = qNote.remove();
            newString += tempNote.toString() + "\n";
            tempQueue.add(tempNote);
        }
        // Original queue gets reverted back using the temp queue
        while(!tempQueue.isEmpty()){
            qNote.add(tempQueue.remove());
         }
        return newString;
        
    }

    // changeTempo method adjusts each note's tempo to be a percentage of its original tempo.
    // The tempo will remain the same if tempo 1.0 is passed.
    // Each note will be twice as long with a speed of 2.0.
    // Each note will be half as long at a 0.5 pace.
    // If the tempo is negative, an IllegalArgumentException is thrown.
    // tempo is recieved from user input and passed into this method
    public void changeTempo(double tempo){
        if (tempo < 0.0)
            throw new IllegalArgumentException("Tempo cannot be negative.");

        // getting the percentage
        double durationFactor = 1.0 / tempo;
        Queue<Note> noteQueue = new LinkedList<>(qNote);

        // applying and manupulating the tempo of the song
        while (!noteQueue.isEmpty()) {
            Note note = noteQueue.remove();
            double newDuration = note.getDuration() * durationFactor;
            note.setDuration(newDuration);
        }
    }

    // Reverse method reverses the song's note order so that subsequent
    // calls to its play methods will play the notes in the opposite order
    // from that in which reverse was called.

    public void reverse(){
        Stack<Note> tempStack = new Stack<>();

        // uses the temporary stack to reverse the note order
        // by pushing and popping
        while(!qNote.isEmpty())
            tempStack.push(qNote.remove());

        while(!tempStack.isEmpty())
            qNote.add(tempStack.pop());
    }

    // Append method adds all notes from the given other song to the end of this (qNote) song.
    // If the qNote is null, an IllegalArgumentException is thrown
    
    public void append(Melody other){
        if(other == null)
            throw new IllegalArgumentException("Other melody is null");

            // Adds other song to the end of qNote
        while(!other.qNote.isEmpty())
            qNote.add(other.qNote.remove());
    }

    // The play method plays the song (qNote) by calling each note's play method.
    // Plays the melody by iterating through the notes in the "qNote" queue and
    // playing each note using its play() method.
    //This method also handles repeated sections within the melody
    // by storing the repeated notes in a "tempQ".
    //
    public void play () {

        // Create a new queue to store repeated notes
        Queue<Note> tempQ = new LinkedList<>();
        // Initialize a boolean variable to keep track of whether the melody is
        // currently in a repeated section

        boolean repeatQ = false;
 
        // Loop through all notes in "qNote" and "tempQ"
        while (!qNote.isEmpty() || !tempQ.isEmpty()) {
            Note note;
            // Check whether "tempQ" is empty or the first note in "qNote" is a repeat
            if (tempQ.isEmpty() || qNote.peek().isRepeat()) {
                // If so, remove the first note from "qNote"
                note = qNote.remove();
            }
            
            else {
                // Otherwise, remove the first note from "tempQ"
                note = tempQ.remove();
            }
            // Play the note using its play() method
            note.play();
            // If the note is a repeated section marker
            if (note.isRepeat()) {
                // Check whether the melody is currently in a repeated section
                if (repeatQ) {
                    // If so, play all notes in "tempQ" and add them to "qNote"
                    while (!tempQ.isEmpty()) {
                        Note repeatedNote = tempQ.remove();
                        repeatedNote.play();
                        qNote.add(repeatedNote);
                    }
                    // Reset the "repeatQ" flag
                    repeatQ = false;
                }
                
                else {
                    // Otherwise, set the "repeatQ" flag to true
                    repeatQ = true;
                }
            }
             else {
                // If the note is not a repeated section marker
                if (repeatQ) {
                    // If the melody is currently in a repeated section, add the note to both "qNote" and "tempQ"
                    tempQ.add(note);
                    qNote.add(note);
                }
            }
        }
 
    }
 }

