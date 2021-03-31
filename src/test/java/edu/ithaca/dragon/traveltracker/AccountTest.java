package edu.ithaca.dragon.traveltracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

}