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
    void approveAddLocationRequestTest(){
        Admin admin = new Admin("AdminUser", "password");
        Account a = new Account("Happy", "s@s.com", "party123");

        Location location1 = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");
        Location location2 = new Location("Starbucks", "130 E Seneca St, Ithaca, NY 14850");
        Location location3 = new Location("New Delhi Diamond's", "106 W Green St, Ithaca, NY 14850");

        ArrayList<Location> compareLib = new ArrayList<>();
        ArrayList<Location> compareRequest = new ArrayList<>();

        admin.addLocation(location1);
        compareLib.add(location1);
        a.requestAddLocation(location2);
        a.requestAddLocation(location3);
        compareRequest.add(location2);
        compareRequest.add(location3);
        
        //deafult list
        assertEquals(compareLib, admin.getLocationList());
        assertEquals(compareRequest, Library.getAddLocationRequests());
        //expected outcome
        admin.approveAddRequest(location2);
        compareLib.add(location2);
        compareRequest.remove(location2);
        assertEquals(compareLib, admin.getLocationList());
        assertEquals(compareRequest, Library.getAddLocationRequests());
        admin.approveAddRequest(location3);
        compareLib.add(location3);
        compareRequest.remove(location3);
        assertEquals(compareLib, admin.getLocationList());
        assertEquals(compareRequest, Library.getAddLocationRequests());
    }

    @Test
    void approveRemovedLocationRequestTest(){
        Admin admin = new Admin("AdminUser", "password");
        Account a = new Account("Happy", "s@s.com", "party123");

        Location location1 = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");
        Location location2 = new Location("Starbucks", "130 E Seneca St, Ithaca, NY 14850");
        Location location3 = new Location("New Delhi Diamond's", "106 W Green St, Ithaca, NY 14850");

        ArrayList<Location> compareLib = new ArrayList<>();
        ArrayList<Location> compareRequest = new ArrayList<>();

        admin.add(location1);
        admin.add(location2);
        admin.add(location3);
        compareLib.add(location1);
        compareLib.add(location2);
        compareLib.add(location3);
        a.requestRemoveLocation(location2);
        a.requestRemoveLocation(location3);
        compareRequest.add(location2);
        compareRequest.add(location3);
        
        //deafult list
        assertEquals(compareLib, admin.getLocationList());
        assertEquals(compareRequest, Library.getRemoveLocationRequests());
        //expected outcome
        admin.approveRemoveRequest(location2);
        compareLib.remove(location2);
        compareRequest.remove(location2);
        assertEquals(compareLib, admin.getLocationList());
        assertEquals(compareRequest, Library.getRemoveLocationRequests());
        admin.approveRemoveRequest(location3);
        compareLib.remove(location3);
        compareRequest.remove(location3);
        assertEquals(compareLib, admin.getLocationList());
        assertEquals(compareRequest, Library.getRemoveLocationRequests());
    }

    public static void main(String[] args) {
        Admin admin = new Admin("AdminUser", "password");
        Account a = new Account("Happy", "s@s.com", "party123");

        Location location1 = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");
        Location location2 = new Location("Starbucks", "130 E Seneca St, Ithaca, NY 14850");
        Location location3 = new Location("New Delhi Diamond's", "106 W Green St, Ithaca, NY 14850");

        a.requestAddLocation(location1);
        a.requestRemoveLocation(location2);
        a.requestRemoveLocation(location3);

        admin.viewLocationRequests("add");
        admin.viewLocationRequests("remove");
    }
}
