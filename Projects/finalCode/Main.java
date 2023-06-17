/*
 * Maksym Shkola
 * 6/8/2023
 * Update Log: Modified by Jeffrey Tang 06/09/2023
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
 *  1) Introduction
 *  2) Option Selector
 *  3) Parse File
 *  4) binaryTreeManipulation
 *  5) addPersonHelper
 *  6) removeHelper
 *  7) fileSaver
 * 
 * Main Program that deals with user input and option selection
 * 
 */

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.*;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

/*
 * 
 * Main method that creates an arraylist full of objects, presents the user with
 * an introduction (user input), creates a binary tree using personList. Check if the 
 * file the user it attempting to load in exists. Displays data management options.
 */


public class Main {

    public static void main(String[] args) {
        ArrayList<Person> personList = new ArrayList<>();
        boolean fileExists = introduction(personList);
        BinaryTreeManagement binaryTreeManagement = new BinaryTreeManagement(personList);
        IdentityTheft identity = new IdentityTheft();
        

            boolean isFirstIteration = true;

        if(fileExists == true){
            while (true) {
                if(!isFirstIteration) {
                System.out.println("\nReturned to the main menu.");
            }
                String option = optionSelector();

                if(option.equals("1")){                
                    System.out.print("\n\n\n");

                    // Will have the user make binary manipulations until selected to end
                    boolean manipulate = binaryTreeManipulation(personList, binaryTreeManagement);
                    while(manipulate == true){
                        manipulate = binaryTreeManipulation(personList, binaryTreeManagement);
                    }                                            
                }
                else if(option.equals("2")){                
                    System.out.print("User selected option: " + option);
                    //call the method
                    IdentityTheft(personList, identity);
                }
                else if(option.equals("3")){                
                    DataProtection dp = new DataProtection();
                    dp.protectData(personList);                
                }
                else if(option.equals("4")){
                    System.out.println("Thank you for using our program. Goodbye!");
                    break; 
                }
                else {
                    System.out.println("Invalid option. Please choose a valid option (1-4).");
                }
                isFirstIteration = false;
            }
        }
        else{
            System.out.print("File does not exist\n");            
        }        
    }

    /*
     * The introduction method takes an ArrayList of Person objects as a parameter.
     *  It interacts with the user to gather information about a text file
     *  containing person information. It prompts the user to input the file path,
     *  checks if the file exists, and if so, it calls the parseFile method to
     *  parse the file and populate the personList with the extracted information.
     *  The method returns a boolean value indicating whether the file exists or not.
     * 
     */
    public static boolean introduction(ArrayList<Person> personList) {
        boolean fileExists = false;
        Scanner fileScan = new Scanner(System.in);
        System.out.println("Hello User!\nThis program manipulates a text file containing " +
                "information on Persons.\n");

        System.out.println("(Proper Format: [First Name, Last Name, SSID, Address,"
         + " Country1, Country2, Country3]");

        System.out.print("Please input a text file containing a properly"
         + " formatted person information: ");
        String filePath = fileScan.nextLine();

        // System.out.print(filePath);
        // CHECKS IF FILE EXISTS
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            parseFile(filePath, personList);
            fileExists = true;
        } else {
            //System.out.print("File does not exist");
            fileExists = false;
        }
        return fileExists;
    }

    /*
     * optionSelector() method displays the user with a list of options and
     * returns the response (option that the user chose) for the other methods
     * to interact with
     * 
     */
    public static String optionSelector() {
        System.out.println("""   

                Option 1) Binary Search Tree Manipulation
                Option 2) Identity Theft Detection
                Option 3) Data Protection
                Option 4) Exit Program
                """);

        Scanner option = new Scanner(System.in);
        System.out.print("Please select an option (1-4): ");
        String selectedOption = option.nextLine();
        // System.out.print(selectedOption);
        return selectedOption;
    }

    /*
    * The parseFile method takes a filePath and an ArrayList
    *  of Person objects as parameters. It reads the content
    *  of a text file located at the given filePath and extracts
    *  person information from each line. It splits each line by commas
    *  to separate the different fields of information and creates a
    *  new Person object using the extracted data. The created Person object
    *  is then added to the personList. Finally, if the file is not found,
    *  it will print the stack trace of the exception.
    */
    public static void parseFile(String filePath, ArrayList<Person> personList) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");

                // Create a new person object and add it to the personList
                Person individual = new Person(tokens[0], tokens[1],
                        Integer.parseInt(tokens[2]), tokens[3], tokens[4],
                         tokens[5], tokens[6]);
                personList.add(individual);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     * The binaryTreeManipulation method takes an ArrayList
     *  of Person objects and a BinaryTreeManagement object
     *  as parameters. It provides a menu-driven interface for
     *  manipulating a binary tree and performing various operations
     *  on the personList and the binary tree managed by binaryTreeManagement.

        The method displays a menu with different manipulation options:

        Option 1: Add a new person to the personList and the binary tree.
        Option 2: Remove a person from the personList and the binary tree based on
         their SSID.
        Option 3: Edit the information of a person in the personList and the
         binary tree based on their SSID.
        Option 4: Print all persons in the binary tree using in-order traversal.

        The method repeatedly prompts the user to select a manipulation
         option and performs the corresponding operation based on the user's input.
          After each operation, the binary tree is traversed using in-order traversal,
           and the updated personList is saved to a file using the fileSaver method.
     */
    public static boolean binaryTreeManipulation(ArrayList<Person> personList,
     BinaryTreeManagement binaryTreeManagement) {
        System.out.print("BinaryTree Manipulation Manager\n\n");
        System.out.print("""
                Option 1) Add Person
                Option 2) Remove Person
                Option 3) Edit Person
                Option 4) Print All Persons (In Order Traversal)
                \n\n""");
    
        boolean isValid = true;
        while (isValid) {
            Scanner binaryOption = new Scanner(System.in);
            System.out.print("Please select a manipulation option: ");
            String selectedBinaryOption = binaryOption.nextLine();
    
            /*
             * Options for Binary tree manipulation
             */
            if (selectedBinaryOption.equals("1")) {
                isValid = false;
                Person newPerson = addPersonHelper(personList);
                personList.add(newPerson);
                binaryTreeManagement.add(newPerson);

                binaryTreeManagement.inOrderTraversal();
                fileSaver(personList);
                
            } else if (selectedBinaryOption.equals("2")) {
                isValid = false;
                Scanner ssidCheck = new Scanner(System.in);
                System.out.print("Enter the individuals SSID you would like to remove: ");
                int removeSsid = Integer.parseInt(ssidCheck.nextLine());

                boolean isValidSsid = true;

                while(isValidSsid){
                    if(removeSsid <= 99999999){
                    System.out.print("Invalid SSID! SSID has to be 9 numbers!\n\n");
                    System.out.print("Enter the individuals SSID you would like to remove: ");
                    removeSsid = Integer.parseInt(ssidCheck.nextLine());

                    }
                    else{
                        isValidSsid = false;
                    }

                }

                int removeIndex = removeHelper(personList, removeSsid);
                
                if(removeIndex != 101){
                    
                    //System.out.println(removeIndex);

                    binaryTreeManagement.remove(personList.get(removeIndex));
                    //System.out.println("\n");
                    binaryTreeManagement.inOrderTraversal();
                    //System.out.println("\n");
                    personList.remove(removeIndex);
                    fileSaver(personList);
                }
                else{
                    System.out.print("Error! SSID not found!");

                }
                
            } else if (selectedBinaryOption.equals("3")) {
                isValid = false;
                Scanner ssidEdit = new Scanner(System.in);
                System.out.print("Enter the individuals SSID you would like to edit: ");
                int editSsid = Integer.parseInt(ssidEdit.nextLine());

                boolean isValidSsid = true;

                while(isValidSsid){
                    if(editSsid <= 99999999){
                    System.out.print("Invalid SSID! SSID has to be 9 numbers!\n\n");
                    System.out.print("Enter the individuals SSID you would like to edit: ");
                    editSsid = Integer.parseInt(ssidEdit.nextLine());

                    }
                    else{
                        isValidSsid = false;
                    }

                }

                int editIndex = removeHelper(personList, editSsid);
                
                if(editIndex != 101){
                    System.out.println("Initial Person Data:");
                    System.out.println("SSID Identified!\n");
                    System.out.println("First Name: " + personList.get(editIndex).getFirstName());
                    System.out.println("Last Name: " + personList.get(editIndex).getLastName());
                    System.out.println("SSID #: " + personList.get(editIndex).getSsid());
                    System.out.println("Address: " + personList.get(editIndex).getAddress());
                    System.out.println("Country Login One: "
                     + personList.get(editIndex).getCountryOne());
                    System.out.println("Country Login Two: "
                     + personList.get(editIndex).getCountryTwo());
                    System.out.println("Country Login Three: "
                     + personList.get(editIndex).getCountryThree());
                    System.out.println("\n");
                    System.out.println("Edit Information:\n");
                    Person editPerson = addPersonHelper(personList);

                    //BinaryTreeManagement.editPerson(personList.get(editIndex), editPerson);
                    personList.set(editIndex, editPerson);
                    fileSaver(personList);                    
                }
                else{
                    System.out.print("Error! SSID not found!");
                }

            } else if (selectedBinaryOption.equals("4")) {
                isValid = false;             
                System.out.print("\n\n");
                
                binaryTreeManagement.inOrderTraversal();
                fileSaver(personList);
            } else {
                System.out.print("Option does not exist!\n\n");
            }

        }
        // Checks if the user wants to perform another operation

        boolean runagain = true;
        Scanner runprog = new Scanner(System.in);
        System.out.print("\nWould you like to perform another operation (y/n): ");
        String yesorno = runprog.nextLine();
        //System.out.print(yesorno);
        
        if(yesorno.equals("y")){
            runagain = true;
            //System.out.print(runagain);

        }

        else{
            runagain = false;
            //System.out.print(runagain);

        }
        //System.out.print(runagain);
        return runagain;
        
        
    }

    /*
     * The addPersonHelper method is a utility method that assists in adding
     *  a new person to the personList. It takes an ArrayList of Person 
     * objects (personList) as a parameter and returns a Person object
     *  representing the newly added person. The method prompts the user to
     *  enter various fields of information for the new person, such as first name,
     *  last name, SSID number, address, and country login details. It validates the
     *  SSID input to ensure it is a valid 9-digit number. After gathering all
     *  the information, it creates a new Person object using the provided data
     *  and returns it. Additionally, it prints a confirmation message stating
     *  that the person has been added.
     */
    public static Person addPersonHelper(ArrayList<Person> personList){
        Scanner addOption = new Scanner(System.in);

        String tempFirstName = "";
        String tempLastName = "";
        int tempSsId = 0;
        String address = "";
        String tempCountryOne = "";
        String tempCountryTwo = "";
        String tempCountryThree = "";

        System.out.print("Enter the following fields...\n\n");

        System.out.print("First Name: ");
        tempFirstName = addOption.nextLine();
        System.out.print("\n");

        System.out.print("Last Name: ");
        tempLastName = addOption.nextLine();
        System.out.print("\n");

        System.out.print("SSID Number: ");
        tempSsId = Integer.parseInt(addOption.nextLine());

        // checks so that ssid is below 9 characters
        while(tempSsId < 99999999){
            System.out.print("Invalid SSID, 9 integers!\n");
            System.out.print("SSID Number: ");
            tempSsId = Integer.parseInt(addOption.nextLine());

        }

        System.out.print("\n");

        System.out.print("Address: ");
        address = addOption.nextLine();
        System.out.print("\n");

        System.out.print("Country Login One: ");
        tempCountryOne = addOption.nextLine();
        System.out.print("\n");

        System.out.print("Country Login Two: ");
        tempCountryTwo = addOption.nextLine();
        System.out.print("\n");

        System.out.print("Country Login Three: ");
        tempCountryThree = addOption.nextLine();
        System.out.print("\n");

        // System.out.print("\n\nFirst Name: " + tempFirstName + "\n" +
        // "Last Name: " + tempLastName + "\n" + "SSID Number: " + tempSsId + "\n" +
        // "Address: " + address + "\n" + "Country One: " + tempCountryOne + "\n" + 
        // "Country two: " + tempCountryTwo + "\n" + "Country Three: " 
        //+ tempCountryThree + "\n");

        Person newPerson = new Person(tempFirstName, tempLastName, tempSsId, address,
         tempCountryOne, tempCountryTwo, tempCountryThree);

        System.out.println(tempFirstName + " " + tempLastName + " has been added!");

        return newPerson;

    }

    /*
     * The removeHelper method takes an ArrayList of Person objects (personList)
     *  and an integer (removeSsid) representing the SSID of the person to be removed.
     *  It returns an integer representing the index of the person to be removed
     *  in the personList. The method iterates over each Person object in the
     *  personList using an enhanced for loop. It compares the SSID of each person
     *  with the removeSsid value. If a match is found, it sets the index variable
     *  to the index of that person in the personList using the indexOf method. 
     * If no match is found, the index remains as the default value of 101,
     *  indicating that the SSID was not found in the personList.
     * 
     */
    public static int removeHelper(ArrayList<Person> personList, int removeSsid){
        int index = 101;

        for(Person person : personList){
            if (person.getSsid() == removeSsid){
                index = personList.indexOf(person);
            }
            else{
                //System.out.println("Error! SSID not found!");

            }
        }
        return index;        
    }

    /*
     * The fileSaver method is responsible for saving the personList
     *  to a text file named "people.txt". It takes an ArrayList of
     *  Person objects (personList) as a parameter and does not return
     *  any value. The method uses a FileWriter object (myWriter)
     *  to write data to the text file. It iterates over each Person
     *  object in the personList using an enhanced for loop. For each person,
     *  it retrieves the relevant information (such as first name, last name,
     *  SSID, address, and country login details) using the appropriate getter
     *  methods of the Person class. It then writes this information as a formatted
     *  line to the text file using the write method of myWriter. Each person's
     *  data is written on a new line. After writing all the data, the method closes
     *  the FileWriter using the close method. If the writing process is successful,
     *  it prints a success message to the console. If an exception occurs during the
     *  file writing process, an error message is printed, and the stack trace is displayed.
     */
    public static void fileSaver(ArrayList<Person> personList){
        try {
            FileWriter myWriter = new FileWriter("people.txt");
            for(Person person : personList){
                myWriter.write(person.getFirstName() + "," + person.getLastName()
                 + "," + person.getSsid() +
                "," + person.getAddress() + "," + person.getCountryOne() + ","
                 + person.getCountryTwo() +
                "," + person.getCountryThree());
                myWriter.write("\n");

            }
            myWriter.close();
            System.out.println("Successfully wrote to the file!");
        }

        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static void IdentityTheft(ArrayList<Person> personList, IdentityTheft Identity){
        //Make an arraylist for the names, social security number, address, and country
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> id = new ArrayList<>();
        ArrayList<String> addresses = new ArrayList<>();
        ArrayList<String> countries = new ArrayList<>();
        for(Person person : personList){
            //combine the name by getting the first name and the last name
            String name = person.getFirstName() + person.getLastName();
            //add the name to the arraylist
            names.add(name);
            //getting the social security number
            int SSid = person.getSsid();
            //add the number to the arraylist
            id.add(SSid);
            //getting the address
            String address = person.getAddress();
            //add the address to the arraylist
            addresses.add(address);
            //getting the countries
            String country1 = person.getCountryOne();
            String country2 = person.getCountryTwo();
            String country3 = person.getCountryThree();
            //adding the countries to the arraylist
            countries.add(country1);
            countries.add(country2);
            countries.add(country3);
        }
        Identity.IdentityTheft(names, id, addresses, countries);
    }
    
}
