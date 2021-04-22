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
}
