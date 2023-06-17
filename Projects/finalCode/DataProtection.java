// Jeffrey Tang
// 06/07/2023    
// Final Group Project; CS143 Spring 2023
//
// The class DataProtection handles the task of protecting data by encrypting and decrypting it.
// The user is provided with options to either encrypt or decrypt data. 
// The encryption and decryption process uses a simple Caesar Cipher shift algorithm, 
// where a 4-digit PIN is used as the shift value. 
// Once the user chooses to encrypt, the data is encrypted and saved into a file. 
// If the user chooses to decrypt, the encrypted data is read from a file, decrypted, and saved into a new file.
//

import java.util.*;
import java.io.*;

public class DataProtection {
    private static final String ENCRYPTION_DIR = ""; // specify your directory here

    /**
     * Main function for user interaction. It provides encryption or decryption based on user's choice.
     *
     * @param personList A list of persons to encrypt or decrypt their data.
     */
    public void protectData(ArrayList<Person> personList) {
        Scanner scanner = new Scanner(System.in);
        
        // Prompt the user for an option
        System.out.print("\nWelcome to data protection center.\n" +
                           "1) Encryption\n" +
                           "2) Decryption\n" +
                           "Please choose an option: ");
        String option = scanner.nextLine();

        // Check user option
        // Encryption
        if (option.equals("1")) {
            System.out.println("\nYou have selected the option: Encryption");
            System.out.print("\nPlease enter a 4-digit numeric pin for encryption: ");
            String pin = scanner.nextLine();
            // Validate the PIN
            if (pin.length() == 4 && pin.matches("[0-9]+")) {
                encryptData(personList, Integer.parseInt(pin));
            } else {
                System.out.println("\nInvalid PIN. Please make sure it is a 4-digit number.");
            }
        // Decryption
        } else if (option.equals("2")) {
            System.out.println("You have selected the option: Decryption");
            System.out.print("\nPlease enter the filename of the .txt file you want to decrypt: ");
            String fileName = scanner.nextLine();
            // Validate the filename
            if (fileName.matches("\\d{4}\\.txt$")) {
                System.out.print("\n'Hint: The PIN is the first 4 digits of the filename.'\n" +
                    "Please enter the 4-digit numeric pin for decryption: ");
                String pin = scanner.nextLine();
                // Validate the PIN
                if (pin.equals(fileName.substring(0, 4))) {
                    decryptData(fileName, Integer.parseInt(pin));
                } else {
                    System.out.println("\nIncorrect PIN.");
                }
            } else {
                System.out.println("\nInvalid filename. Please make sure it is a 4-digit number followed by .txt");
            }
        } else {
            System.out.println("\nInvalid option. Please choose 1 for encryption or 2 for decryption.");
        }
    }

    /**
     * Encrypt the data of a list of persons using a given PIN and write it to a file.
     *
     * @param personList A list of persons to encrypt their data.
     * @param pin A 4-digit PIN used for encryption.
     */
    public void encryptData(ArrayList<Person> personList, int pin) {
        // Save the encrypted data into a file named pin.txt
        String filename = ENCRYPTION_DIR + pin + ".txt";

        try {
            FileWriter writer = new FileWriter(filename);
            // Loop over each person in the list
            for (Person individual : personList) {
                // Encrypt individual fields
                String encryptedFirstName = encrypt(individual.getFirstName(), pin);
                String encryptedLastName = encrypt(individual.getLastName(), pin);
                String encryptedSsid = encrypt(String.valueOf(individual.getSsid()), pin);

                // Write the encrypted data into the file
                writer.write(encryptedFirstName + "," + encryptedLastName + "," +
                             encryptedSsid + "," + individual.getAddress() + "," +
                             individual.getCountryOne() + "," + individual.getCountryTwo() + "," +
                             individual.getCountryThree() + "\n");
            }
            writer.close();
            System.out.println("\nEncryption succeed! Encrypted file saved as " + pin + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Decrypt the data in a file using a given PIN and write it to another file.
     *
     * @param filename The name of the file to decrypt.
     * @param pin A 4-digit PIN used for decryption.
     */
    public void decryptData(String filename, int pin) {
        // Save the decrypted data into a file named decryption<Pin>.txt
        String decryptedFilename = ENCRYPTION_DIR + "decryption" + pin + ".txt";

        try {
            Scanner scanner = new Scanner(new File(filename));
            FileWriter writer = new FileWriter(decryptedFilename);
            // Loop over each line in the file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");

                // Decrypt individual fields
                String decryptedFirstName = decrypt(tokens[0], pin);
                String decryptedLastName = decrypt(tokens[1], pin);
                String decryptedSsid = decrypt(tokens[2], pin);

                // Write the decrypted data into the file
                writer.write(decryptedFirstName + "," + decryptedLastName + "," +
                             decryptedSsid + "," + tokens[3] + "," + tokens[4] + "," +
                             tokens[5] + "," + tokens[6] + "\n");
            }
            scanner.close();
            writer.close();
            System.out.println("\nDecryption succeed! Decrypted file saved as decryption" + pin + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Encrypt a string using a given PIN.
     *
     * @param data The string to encrypt.
     * @param pin A 4-digit PIN used for encryption.
     * @return The encrypted string.
     */
    private String encrypt(String data, int pin) {
        
        StringBuilder encrypted = new StringBuilder();
        // Loop over each character in the string
        for (char c : data.toCharArray()) {
            encrypted.append((char) (c + pin % 26)); // Encrypt the character
        }
        return encrypted.toString();
    }

    /**
     * Decrypt a string using a given PIN.
     *
     * @param data The string to decrypt.
     * @param pin A 4-digit PIN used for decryption.
     * @return The decrypted string.
     */
    private String decrypt(String data, int pin) {
        
        StringBuilder decrypted = new StringBuilder();
        // Loop over each character in the string
        for (char c : data.toCharArray()) {
            decrypted.append((char) (c - pin % 26)); // Decrypt the character
        }
        return decrypted.toString();
    }
}
