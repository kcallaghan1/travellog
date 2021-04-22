package edu.ithaca.dragon.traveltracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class AdminTest {

    @Test
    void constructorTest(){
        Admin admin = new Admin("AdminUser", "password");
        assertThrows(IllegalArgumentException.class, () -> new Admin("admin", "password"));
    }

    @Test
    void verifyTest(){
        Admin admin = new Admin("AdminUser", "password");
        assertFalse(admin.verifyAccount("AdminUs", "password"));
        assertTrue(admin.verifyAccount("AdminUser", "password"));
    }

    @Test
    void viewLocationRequestsTest(){
        Admin admin = new Admin("AdminUser", "password");
        Account a = new Account("Happy", "s@s.com", "party123");

        Location location1 = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");
        Location location2 = new Location("Starbucks", "130 E Seneca St, Ithaca, NY 14850");
        Location location3 = new Location("New Delhi Diamond's", "106 W Green St, Ithaca, NY 14850");

        //empty list
        assertEquals("", admin.viewLocationRequests("add"));
        assertEquals("", admin.viewLocationRequests("remove"));
        //adding to both lists
        a.requestAddLocation(location1);
        a.requestAddLocation(location2);
        a.requestAddLocation(location3);
        a.requestRemoveLocation(location1);
        a.requestRemoveLocation(location2);
        a.requestRemoveLocation(location3);
        //Displays all the locations
        assertEquals("Waffle Frolic, 146 E State St, Ithaca, NY 14850\nStarbuck, 130 E Seneca St, Ithaca, NY 14850\nNew Delhi Diamond's, 106 W Green St, Ithaca, NY 14850\n", admin.viewLocationRequests("add"));
        assertEquals("Waffle Frolic, 146 E State St, Ithaca, NY 14850\nStarbuck, 130 E Seneca St, Ithaca, NY 14850\nNew Delhi Diamond's, 106 W Green St, Ithaca, NY 14850\n", admin.viewLocationRequests("remove"));
    }
}