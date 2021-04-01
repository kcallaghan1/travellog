package edu.ithaca.dragon.traveltracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {

    @Test
    void searchTest(){
        Location location1 = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");
        Location location2 = new Location("Starbucks", "130 E Seneca St, Ithaca, NY 14850");
        Location location3 = new Location("New Delhi Diamond's", "106 W Green St, Ithaca, NY 14850");

        ArrayList<Location> locations = new ArrayList<Location>;
        locations.add(location1);
        locations.add(location2);
        locations.add(location3);

        assertTrue(search("Waffle Frolic", locations));  //Perfect match
        assertTrue(search("waFfLe FrOlIc", locations));  //Case insensitive match

        assertFalse(search("wafffffle frolic", locations)); //No match
    }

    @Test
    void isInputValidTest(){
        assertTrue(isInputValid("StarBucks"));  //Mixing cases
        assertTrue(isInputValid("starbucks"));  //Lower case
        assertTrue(isInputValid("STARBUCKS"));  //Upper case
        assertTrue(isInputValid("5 guys"));  //Numbers
        assertTrue(isInputValid("5 and Below"));  //Numbers and mixed case

        assertFalse(isInputValid("$tarbucks!")); // special characters ($!)
        assertFalse(isInputvalid("Starbucks..?"); // special characters (.?)
    }
}
