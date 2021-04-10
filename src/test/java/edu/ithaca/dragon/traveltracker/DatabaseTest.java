package edu.ithaca.dragon.traveltracker;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @Test
    void connectionTest(){
        Connection con = Database.connect();
    }
}
