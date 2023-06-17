/*
 * Maksym Shkola
 * 6/8/2023
 * Group Project ; CS143 Spring
 * Group #4
 * 
 * Description: The Personal Information Database Management System is
 * a program that manipulates a person(s) data. The program asks the user
 * to import a file contianing user data. Then the program displays the user with options
 * on how to manipulate that data in addition to encrypting/decrypting the data, and
 * veryfiying whether an individual is at-risk for identity theft.
 * 
 * Main Options:
 *  1) Binary Tree Management - Add, Edit, Remove, and Display All Players
 *  2) Encryption/Decryption - Encrypt and Decrypt a persons information
 *  3) Identity Theft Detector - Evaluates if an individual is at-risk for identity theft
 * 
 * Methods:
 *  1) Person constructor
 *  2) setters
 *  3) getters
 * 
 * Person class constructs an arrayList of person objects. Each line from the user inputted
 * txt file represents an object.
 * 
 *  
 */


import java.io.File;
import java.util.*;

public class Person {

    private String firstName;
    private String lastName;
    private int ssid;
    private String address;
    private String countryOne;
    private String countryTwo;
    private String countryThree;

    // default constructor
    public Person(String firstName, String lastName, int ssid, String address, String countryOne, String countryTwo, String countryThree) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssid = ssid;
        this.address = address;
        this.countryOne = countryOne;
        this.countryTwo = countryTwo;
        this.countryThree = countryThree;
    }

    // Setter and getter methods for firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Setter and getter methods for lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Setter and getter methods for ssid
    public int getSsid() {
        return ssid;
    }

    public void setSsid(int ssid) {
        this.ssid = ssid;
    }

    // Setter and getter methods for address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Setter and getter methods for countryOne
    public String getCountryOne() {
        return countryOne;
    }

    public void setCountryOne(String countryOne) {
        this.countryOne = countryOne;
    }

    // Setter and getter methods for countryTwo
    public String getCountryTwo() {
        return countryTwo;
    }

    public void setCountryTwo(String countryTwo) {
        this.countryTwo = countryTwo;
    }

    // Setter and getter methods for countryThree
    public String getCountryThree() {
        return countryThree;
    }

    public void setCountryThree(String countryThree) {
        this.countryThree = countryThree;
    }
}
