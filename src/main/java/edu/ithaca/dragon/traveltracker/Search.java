package edu.ithaca.dragon.traveltracker;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {

    /**
     *  Returns true if userInput contains any character other than
     *  letters, numbers, or spaces.  Returns false otherwise.
     */

    public static boolean isInputValid(String userInput) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(userInput);
        boolean b = m.find();

        if (b)
            return false;
        return true;
    }

    /**
     *  Returns a list of locations if the location was searched for by name
     *  returns an empty list if otherwise
     */


    public static ArrayList<Location> search(String userInput, ArrayList<Location> locations){
        ArrayList<Location> filtered_locations = new ArrayList<Location>();

        for(int i=0;i<locations.size();i++){
            if(locations.get(i).getName().toLowerCase().contains(userInput.toLowerCase())){
                filtered_locations.add(locations.get(i));
            }
        }

        return filtered_locations;
    }

    /**
     *  Returns a list of locations if the location belongs to the category searched for
     *  returns an empty list if otherwise
     */

    public static ArrayList<Location> searchCategories(String userInput, ArrayList<Location> locations){
        ArrayList<Location> filtered_locations = new ArrayList<Location>();

        for(int i=0;i<locations.size();i++){
            for(int k = 0;k<locations.get(i).getCategories().size();k++){
                if(locations.get(i).getCategories().get(k).toString().toLowerCase().contains(userInput) && !filtered_locations.contains(locations.get(i)))
                    filtered_locations.add(locations.get(i));
            }
        }

        return filtered_locations;
    }
}
