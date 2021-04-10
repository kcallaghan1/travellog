package edu.ithaca.dragon.traveltracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection connect(){
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Database/TravelTracker.db");
            System.out.println("Connected To Database");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e+"");
        }
        return con;
    }
}
