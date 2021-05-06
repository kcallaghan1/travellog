package edu.ithaca.dragon.traveltracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DateTest {

    @Test
    void construtorTest(){
        Date date = new Date("May", 4, 2021);
        assertEquals("May", date.getMonth());
        assertEquals(4, date.getDay());
        assertEquals(2021, date.getYear());
        assertThrows(IllegalArgumentException.class, () -> new Date("May", 0, 2021));
        assertThrows(IllegalArgumentException.class, () -> new Date("May", 32, 2021));
        assertThrows(IllegalArgumentException.class, () -> new Date("May", 4, 2022));
    }

    @Test
    void verifyDateTest(){
        Date date = new Date("May", 4, 2021);
        assertTrue(date.verifyDate("May", 4, 2021));
        assertFalse(date.verifyDate("", 4, 2021));
        assertFalse(date.verifyDate("May", 0, 2021));
        assertFalse(date.verifyDate("May", 32, 2021));
        assertFalse(date.verifyDate("May", 4, 2022));
    }
    
}
