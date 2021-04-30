package edu.ithaca.dragon.traveltracker;

import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    public Database db = new Database("jdbc:sqlite:Database/TravelTrackerTest.db");

    /*
        Simple checking to be sure the database connects, if not it will throw an exception
     */
    @Test
    void connectionTest(){
        Connection con = db.connect();
    }


    /*
        Examples of some queries that could be useful, how to use SQL statements
        to store and retrieve values
     */
    @Test
    void queryTest() throws SQLException {
        Connection con = db.connect();

        String sql = "SELECT * FROM accounts";
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(sql);

        //while(result.next()) will be used when we have multiple rows of values
        //it would encapsulate the below code

        String email = result.getString("email");
        String username = result.getString("username");
        String password = result.getString("password");
        String permissions = result.getString("permissions");

        //Just tests to make sure we get the right values from the database
        assertEquals("admin@gmail.com", email);
        assertEquals("admin", username);
        assertEquals("admin123", password);
        assertEquals("admin", permissions);


        System.out.println(email + ", " + username +", "
        +password + ", "+permissions);

        con.close();
    }

    /*
        Testing adding/removing from the database dynamically
        Displays how to use multiple statements in the most efficient/readable way
        Notice how we use statement.executeUpdate() for updating the dataset, but we
        use executeQuery() for searching through.
     */
    @Test
    void addAccountTest() throws SQLException {
        Account acc = new Account("cam", "cam@gmail.com", "cam123");
        db.addAccount(acc);

        Connection con = db.connect();

        String sql = "SELECT * FROM accounts WHERE username LIKE 'cam'";
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(sql);

        String username = result.getString("username");
        String email = result.getString("email");
        String password = result.getString("password");

        db.printAccounts();

        /* Equivalence Testing */
        assertEquals("cam", username);
        assertEquals("cam@gmail.com", email);
        assertEquals("cam123", password);

        con.close();
    }

    @Test
    void removeAccountTest() throws SQLException {
        Account acc = new Account("cam", "cam@gmail.com", "cam123");
        db.addAccount(acc);

        Connection con = db.connect();

        //--BEFORE REMOVAL
        System.out.println("\n--BEFORE--\n");

        String sql = "SELECT COUNT(*) as count FROM accounts WHERE username LIKE 'cam'";
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(sql);

        int count = result.getInt("count");

        assertEquals(1, count); //Equivalence test, making sure that the value is present before removal

        db.printAccounts();

        con.close();

        //--AFTER REMOVAL
        System.out.println("\n--After--\n");

        con = db.connect();
        db.removeAccountByUsername("cam");

        sql = "SELECT COUNT(*) as count FROM accounts WHERE username LIKE 'cam'";
        statement = con.createStatement();
        result = statement.executeQuery(sql);

        count = result.getInt("count");

        assertEquals(0, count); //Equivalence test, making sure that there are no results after removal

        db.printAccounts();
        con.close();
    }

    @Test
    void getAccountsTest() throws SQLException {
        db.removeAccountByUsername("cam");
        db.removeAccountByUsername("mike");

        ArrayList<Account> accounts = db.getAccounts();
        System.out.println("\n--Pre Addition--");
        for(Account cur : accounts){
            System.out.println(cur.getUsername());
            System.out.println(cur.getEmail());
        }
        assertEquals(1, accounts.size()); //Equivalence testing for only one value in the accounts table
        Account acc = new Account("cam", "cam@gmail.com", "cam123");

        db.addAccount(acc);
        accounts = db.getAccounts();
        System.out.println("\n--Post Addition--");
        for(Account cur : accounts){
            System.out.println(cur.getUsername());
            System.out.println(cur.getEmail());
        }

        assertEquals(2, accounts.size()); //Equivalence testing for two values in the accounts table

        db.removeAccountByUsername("cam");
        System.out.println("\n--Post Removal--");
        accounts = db.getAccounts();
        for(Account cur : accounts){
            System.out.println(cur.getUsername());
            System.out.println(cur.getEmail());
        }
        assertEquals(1, accounts.size()); //Making sure it works pre/post add/removal
    }

    @Test
    void findAccountByUsernameTest() throws SQLException {
        Account acc = new Account("admin", "admin@gmail.com", "admin123");
        Account returned = db.findAccountByUsername("admin");
        assertEquals(acc.getUsername(), returned.getUsername()); //Check to make sure we got the right username
        assertEquals(acc.getEmail(), returned.getEmail());  //Check to make sure we got the right email
        assertEquals(acc.getPassword(), returned.getPassword()); //Check to make sure we got the right password

        returned = db.findAccountByUsername("Bananas");
        assertEquals(null, returned);  //Make sure null is returned when there are no results
    }

    @Test
    void updatePasswordTest() throws SQLException {
        db.removeAccountByUsername("mike");

        //--BEFORE
        Account acc = new Account("mike", "mike@gmail.com", "mike123");
        db.addAccount(acc);
        acc = db.findAccountByUsername("mike"); //add account to database, then query it to a variable
        assertEquals(acc.getPassword(), "mike123"); //Check that the password in the database is the original

        //--AFTER

        acc.resetPassword("mike1234", "mike1234", "mike123");
        db.updatePassword(acc);
        Account acc2 = db.findAccountByUsername("mike");
        assertEquals(acc2.getPassword(), "mike1234"); //Check that the password in the database has been change
    }

    @Test
    void addLocationTest() throws SQLException{
        db.removeLocation("addTest");

        //--Before
        Location loc = new Location("addTest", "123 add test");
        ArrayList<Location> locations = db.findLocationByName("addTest");
        assertEquals(0, locations.size()); //Making sure that there isn't a location by this name

        //--After
        db.addLocation(loc);
        locations = db.findLocationByName("addTest");
        assertEquals(1, locations.size());  //Making sure that the location has been added

    }

    @Test
    void removeLocationTest() throws SQLException{
        Connection con = db.connect();

        Location loc = new Location("removeTest", "123 remove test");
        db.addLocation(loc);

        db.removeLocation("removeTest");

        String sql = "SELECT * FROM locations WHERE locationName='removeTest'";
        Statement statement = con.createStatement();
        statement.executeUpdate("PRAGMA FOREIGN_KEYS = ON");
        try {
            ResultSet result = statement.executeQuery(sql);
            assertEquals("removeTest", result.getString("locationName"));
        }
        catch(SQLException se){
            System.out.println("Working, this means there was nothing to query");
        }
        con.close();
    }

    @Test
    void findLocationByNameTest() throws SQLException{
        db.removeLocation("queryTest");
        ArrayList<Location> filteredLocations = new ArrayList<>();

        //--Query with nothing
        filteredLocations = db.findLocationByName("query");
        Location loc = new Location("queryTest", "123 QueryTest");
        assertEquals(0, filteredLocations.size()); //Be sure nothing is returned when location isn't found


        //Query with location added
        db.addLocation(loc);
        filteredLocations = db.findLocationByName("query");
        assertEquals(1, filteredLocations.size()); //Check that the location can be found

    }

    @Test
    void findLocationByCategoryTest() throws SQLException{
        ArrayList<Location> filteredLocations = new ArrayList<>();

        filteredLocations = db.findLocationByCategory("Restaurant");
        assertEquals(2, filteredLocations.size()); //There are two locations that fit inside this category

        filteredLocations = db.findLocationByCategory("Fast Food");
        assertEquals(1, filteredLocations.size()); //There is only one location that fits this category

        filteredLocations = db.findLocationByCategory("fnjksakjfnds");
        assertEquals(0, filteredLocations.size()); // There are no locations of this category
    }


    @Test
    void addLocationToTravelLogTest() throws SQLException {
        Location loc = db.findLocationByName("Taco Town").get(0);
        ArrayList<Location> locations = db.getLoggedLocations();
        db.addLocationToTravelLog(loc.getLocationId(), 1);

        Connection con = db.connect();

        String sql = "SELECT COUNT(*) as count FROM loggedLocations WHERE travelLogId==1";
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(sql);

        int count = result.getInt("count");

        assertEquals(2, count); //Making sure that the appropriate amount of locations are in the log
        con.close();

        con = db.connect();

        sql = "SELECT COUNT(*) as count FROM loggedLocations WHERE travelLogId==1323";
        statement = con.createStatement();
        result = statement.executeQuery(sql);

        count = result.getInt("count");

        assertEquals(0, count); //Making sure that nothing is returned when the log isn't there

        con.close();


    }


    @Test
    void removeLocationFromTravelLogTest() throws SQLException {

        //Add the location to the travelLog
        Location loc = db.findLocationByName("Taco Town").get(0);
        ArrayList<Location> locations = db.getLoggedLocations();
        db.addLocationToTravelLog(loc.getLocationId(), 1);

        Connection con = db.connect();

        String sql = "SELECT COUNT(*) as count FROM loggedLocations WHERE travelLogId==1";
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(sql);

        int count = result.getInt("count");

        assertEquals(2, count); //Making sure that the appropriate amount of locations are in the log
        con.close();

        //Remove the location from the travellog
        db.removeLocationFromTravelLog(loc.getLocationId(), 1);
        con = db.connect();

        sql = "SELECT COUNT(*) as count FROM loggedLocations WHERE travelLogId==1";
        statement = con.createStatement();
        result = statement.executeQuery(sql);

        count = result.getInt("count");
        assertEquals(1, count); //Make sure that there is one less travellog connected to the account

        con.close();
    }
}
