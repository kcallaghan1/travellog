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

        assertEquals(true, search("Waffle Frolic", locations));  //Perfect match
        assertEquals(true, search("waFfLe FrOlIc", locations));  //Case insensitive match
        assertEquals(false, search("wafffffle frolic", locations)); //No match
    }
}
