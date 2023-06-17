//Author : Aldrich Siwi
//6/09/2023
//Group project ; CS143 143
//Group 4
//Description: This is a class that tells the user if there is a duplication in the data or not.
//The program would tell the user if there is a duplicate in the names, SSID number, address, and
//it will check the countries if there is a suspicious activity like the user try to enter, but from
//a different country.
//
//Methods:
//1. checkName(for checking name if there are the same)
//2. checkSSID(for checking the social security number if there are the same)
//3. checkAddress(for checking if the Address is the same)
//4. checkCountries(for checking if the country are all the same)
import java.util.*;
public class IdentityTheft {

    public void IdentityTheft(ArrayList<String> people, ArrayList<Integer> number, ArrayList<String> address, ArrayList<String> countries){
        checkName(people);
        checkSSId(number);
        checkAddress(address);
        checkCountries(countries);
    }

    private void checkName(ArrayList<String> people){
        //Make a hashset
        HashSet<String> uniqueNames = new HashSet<>();
        boolean hasDuplicates = false;
        //Iterate through and check if there are duplicates in the name
        for(String name : people){
            if(!uniqueNames.add(name)){
                hasDuplicates = true;
                System.out.println("\nThere is a person with the same name");
            } else {
                //System.out.println("There is no unusual activity");
            }
        }
    }

    private void checkSSId(ArrayList<Integer> numbers){
        HashSet<Integer> uniqueNumber = new HashSet<>();
        //Iterate through and check if there are duplicates in the SSID
        for(Integer SSId : numbers){
            if(!uniqueNumber.add(SSId)){
                System.out.println("\nThere is a same SSID number");
            } else {
                //System.out.println("There is no unusual activity");
            }
        }
    }

    private void checkAddress(ArrayList<String> address){
        HashSet<String> uniqueAddress = new HashSet<>();
        //Iterate through and check if there are duplicates in the Address
        for(String Address : address){
            if(!uniqueAddress.add(Address)){
                System.out.print("\nThere is someone living in the same address");
            } else {
                //System.out.println("There is no unusual activity");
            }
        }
    }

    private void checkCountries(ArrayList<String> countries){
        //make an arraylist for the countries
        ArrayList<String> country = new ArrayList<>();
        boolean samecountries = true;
        //Make a loop to set the first country and the rest
        for(int i = 0; i < countries.size() - 1; i++){
            String first = countries.get(i);
            String currcountry = countries.get(i + 1);
            //adding the first country to the arraylist
            country.add(first);
            //if the indext of the arraylist goes to 4 make the arraylist empty and renew it by clearing it and adding it
            if(i % 4 == 0){
                country.clear();
                country.add(currcountry);
                if(!currcountry.equals(first)){
                    System.out.println("\nThere is a user who is trying to entry from a different country");
                } else {
                    //System.out.println("There is no unusual activity");
                }
            } else {
               country.add(first);
            }

        }
    }
}
