package edu.ithaca.dragon.traveltracker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static edu.ithaca.dragon.traveltracker.Search.isInputValid;
import static edu.ithaca.dragon.traveltracker.Search.search;
import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {

    @Test
    void searchTest(){
        Location location1 = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");
        Location location2 = new Location("Starbucks", "130 E Seneca St, Ithaca, NY 14850");
        Location location3 = new Location("New Delhi Diamond's", "106 W Green St, Ithaca, NY 14850");

        ArrayList<Location> locations = new ArrayList<Location>();
        ArrayList<Location> filtered_locations = new ArrayList<Location>();

        locations.add(location1);
        locations.add(location2);
        locations.add(location3);

        filtered_locations = search("a", locations);  //Partial match, should still return matching
        for(int i =0;i<filtered_locations.size();i++){
            System.out.println(filtered_locations.get(i).getName());
        }

    }

    @Test
    void isInputValidTest(){
        assertTrue(isInputValid("StarBucks"));  //Mixing cases
        assertTrue(isInputValid("starbucks"));  //Lower case
        assertTrue(isInputValid("STARBUCKS"));  //Upper case
        assertTrue(isInputValid("5 guys"));  //Numbers
        assertTrue(isInputValid("5 and Below"));  //Numbers and mixed case

        assertFalse(isInputValid("$tarbucks!")); // special characters ($!)
        assertFalse(isInputValid("Starbucks..?")); // special characters (.?)
    }
}
