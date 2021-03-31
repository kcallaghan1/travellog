package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
public class TravelLogTest {

    @Test
    void constructorTest(){
        TravelLog travelLog = new TravelLog("Ithaca", "All the best locations in downtown Ithaca!");

        assertEquals("Ithaca", travelLog.getTitle());
        assertEquals("All the best locations in downtown Ithaca!", travelLog.getDescription());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new TravelLog("", "description here"));
        assertThrows(IllegalArgumentException.class, () -> new TravelLog("Ithaca", ""));
        assertThrows(IllegalArgumentException.class, () -> new TravelLog("", ""));
    }

    @Test
    void addDestinationTest(){
        TravelLog travelLog = new TravelLog("Ithaca", "All the best locations in downtown Ithaca!");
        Location location1 = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");
        Location location2 = new Location("Starbucks", "130 E Seneca St, Ithaca, NY 14850");
        Location location3 = new Location("New Delhi Diamond's", "106 W Green St, Ithaca, NY 14850");

        List<Location> compare = new ArrayList<>();
        compare.add(location1);

        //When places list is still empty
        assertNotEquals(compare, travelLog.getPlaces());
        travelLog.addDestination(location1);
        //Base case
        assertEquals(compare, travelLog.getPlaces());
        //Adding multiple locations
        travelLog.addDestination(location2);
        travelLog.addDestination(location3);
        //adding the same location
        assertThrows(IllegalArgumentException.class, () -> travelLog.addDestination(location1));
    }
}
