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
        a.resetPassword("party123", "party123", "newparty123");
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

}