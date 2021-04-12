package edu.ithaca.dragon.traveltracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class AccountTest {

    @Test
    void constructorTest() {
        Account a = new Account("Happy", "s@s.com", "Party");
        assertThrows(IllegalArgumentException.class, () -> new Account("something", "notanemail","password"));
    }

    @Test
    void verifyAccountTest(){
        Account a = new Account("Happy", "s@s.com", "party123");
        assertFalse(a.verifyAccount("Hap", "party123"));
        assertTrue(a.verifyAccount("Happy", "party123"));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(Account.isEmailValid( "a@b.com"));
        assertFalse( Account.isEmailValid(""));
    }


    @Test
    void resetPasswordTest(){
        Account a = new Account("Happy", "s@s.com", "party123");
        assertTrue(a.verifyAccount("Happy", "party123"));
        a.resetPassword("newparty123", "newparty123", "party123");
        assertFalse(a.verifyAccount("Happy", "party123"));
        assertTrue(a.verifyAccount("Happy", "newparty123"));

    }

    @Test
    void addFavTest(){
        Account a = new Account("Happy", "s@s.com", "party123");
        Location location1 = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");
        Location location2 = new Location("Starbucks", "130 E Seneca St, Ithaca, NY 14850");
        Location location3 = new Location("New Delhi Diamond's", "106 W Green St, Ithaca, NY 14850");

        List<Location> compare = new ArrayList<>();
        compare.add(location1);

        //Fav list in account should be intitally empty
        assertNotEquals(compare, a.getFav());
        //Adding a location to the fav list
        a.addFav(location1);
        assertEquals(compare, a.getFav());
        //Adding multiple locations
        compare.add(location2);
        compare.add(location3);
        assertNotEquals(compare, a.getFav());
        a.addFav(location2);
        a.addFav(location3);
        assertEquals(compare, a.getFav());
        //adding the same location
        assertThrows(IllegalArgumentException.class, () -> a.addFav(location1));
    }

    @Test
    void removeFavTest(){
        Account a = new Account("Happy", "s@s.com", "party123");
        Location location1 = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");
        Location location2 = new Location("Starbucks", "130 E Seneca St, Ithaca, NY 14850");
        Location location3 = new Location("New Delhi Diamond's", "106 W Green St, Ithaca, NY 14850");

        a.addFav(location1);
        a.addFav(location2);
        a.addFav(location3);

        List<Location> compare = new ArrayList<>();
        compare.add(location1);
        compare.add(location2);
        compare.add(location3);

        //base case
        a.removeFav(location1);
        assertNotEquals(compare, a.getFav());
        compare.remove(location1);
        assertEquals(compare, a.getFav());
        //removing an already removed location
        assertThrows(IllegalArgumentException.class, () -> a.removeFav(location1));
        //removing mutliple locations
        a.removeFav(location2);
        a.removeFav(location3);
        assertNotEquals(compare, a.getFav());
        compare.remove(location2);
        compare.remove(location3);
        assertEquals(compare, a.getFav());
    }

    @Test
    void requestAddLocationTest(){
        Account a = new Account("Happy", "s@s.com", "party123");
        Location location1 = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");
        Location location2 = new Location("Starbucks", "130 E Seneca St, Ithaca, NY 14850");
        Location location3 = new Location("New Delhi Diamond's", "106 W Green St, Ithaca, NY 14850");

        ArrayList<Location> compare = new ArrayList<>();

        //no requests
        assertEquals(compare, Library.getAddLocationRequests());
        //base case
        a.requestAddLocation(location1);
        assertNotEquals(compare, Library.getAddLocationRequests());
        compare.add(location1);
        assertEquals(compare, Library.getAddLocationRequests());
        //requesting multiple locations
        a.requestAddLocation(location2);
        a.requestAddLocation(location3);
        assertNotEquals(compare, Library.getAddLocationRequests());
        compare.add(location2);
        compare.add(location3);
        assertEquals(compare, Library.getAddLocationRequests());
        //requesting the same location
        assertThrows(IllegalArgumentException.class, () -> a.requestAddLocation(location1));
    }

    @Test
    void requestRemoveLocationTest(){
        Account a = new Account("Happy", "s@s.com", "party123");
        Location location1 = new Location("Waffle Frolic", "146 E State St, Ithaca, NY 14850");
        Location location2 = new Location("Starbucks", "130 E Seneca St, Ithaca, NY 14850");
        Location location3 = new Location("New Delhi Diamond's", "106 W Green St, Ithaca, NY 14850");

        ArrayList<Location> compare = new ArrayList<>();

        //no requests
        assertEquals(compare, Library.getRemoveLocationRequests());
        //base case
        a.requestRemoveLocation(location1);
        assertNotEquals(compare, Library.getRemoveLocationRequests());
        compare.add(location1);
        assertEquals(compare, Library.getRemoveLocationRequests());
        //requesting multiple locations
        a.requestRemoveLocation(location2);
        a.requestRemoveLocation(location3);
        assertNotEquals(compare, Library.getRemoveLocationRequests());
        compare.add(location2);
        compare.add(location3);
        assertEquals(compare, Library.getRemoveLocationRequests());
        //requesting the same location
        assertThrows(IllegalArgumentException.class, () -> a.requestRemoveLocation(location1));
    }
}