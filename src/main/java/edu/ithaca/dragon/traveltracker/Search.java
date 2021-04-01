package edu.ithaca.dragon.traveltracker;
import java.util.ArrayList;
import java.util.Scanner;

public class Search {

    /**
     *  Returns true if userInput contains any character other than
     *  letters, numbers, or spaces.  Returns false otherwise.
     */

    public boolean isInputValid(String userInput) {
        return (userInput == null) ? false : userInput.matches("[^A-Za-z0-9 ]");
    }

    /**
     *  Returns true if the location is in the locations database
     *  returns false otherwise
     */

    public static boolean Search(String userInput, ArrayList<Location> locations){
        for(int i=0;i<locations.size();i++){
            if((userInput.toLowerCase()).equals(locations.get(i).getName().toLowerCase())){
                return true;
            }
        }return false;
    }
}
