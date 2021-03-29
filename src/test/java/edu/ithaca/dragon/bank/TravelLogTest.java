package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TravelLogTest {

    @Test
    void constructorTest(){
        TravelLog travelLog = new TravelLog("Ithaca", "All the best locations in downtown Ithaca!");

        assertEquals("Ithaca", travelLog.getTitle());
        assertEquals("All the best locations in downtown Ithaca!", travelLog.getDescription());
        //check for exception throw correctly
        assertThrows(IllegalArgumentException.class, () -> new TravelLog("", "description here"));
        assertThrows(IllegalArgumentException.class, () -> new TravelLog("Ithaca", ""));
        assertThrows(IllegalArgumentException.class, () -> new TravelLog("", ""));
    }

    
}
