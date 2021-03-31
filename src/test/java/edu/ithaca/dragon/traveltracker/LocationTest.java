package edu.ithaca.dragon.traveltracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
    
    @Test
    void constructorTest(){
        Location location = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");

        assertEquals("Waffle Frolic", location.getName());
        assertEquals("146 E State St, Ithaca, NY 14850", location.getAddress());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, () -> new Location("", "146 E State St, Ithaca, NY 14850"));
        assertThrows(IllegalArgumentException.class, () -> new Location("Waffle Frolic", ""));
    }
}
