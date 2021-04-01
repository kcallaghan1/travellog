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

        assertEquals(3, filtered_locations.size());  //Partial match, should have all 3 locations
        assertEquals("Waffle Frolic", filtered_locations.get(0).getName());  //Values should match

        filtered_locations = search("s", locations);  //Partial match

        assertEquals(2, filtered_locations.size());  //Partial match, should have 2 locations
        assertEquals("Starbucks", filtered_locations.get(0).getName());  //Values should match

        filtered_locations = search("d", locations);  //Partial match

        assertEquals(1, filtered_locations.size());  //Partial match, should have one location
        assertEquals("New Delhi Diamond's", filtered_locations.get(0).getName());  //Values should match

        filtered_locations = search("x", locations);  //No match

        assertEquals(0, filtered_locations.size());  //No match, 0 locations
    }

    @Test
    void searchCategoriesTest(){
        Location location1 = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");
        Location location2 = new Location("Starbucks", "130 E Seneca St, Ithaca, NY 14850");
        Location location3 = new Location("New Delhi Diamond's", "106 W Green St, Ithaca, NY 14850");

        ArrayList<Location> locations = new ArrayList<Location>();
        ArrayList<Location> filtered_locations = new ArrayList<Location>();

        locations.add(location1);
        locations.add(location2);
        locations.add(location3);

        System.out.println("Need Library To Filter Categories");
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
