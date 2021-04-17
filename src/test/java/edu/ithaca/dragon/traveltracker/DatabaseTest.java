package edu.ithaca.dragon.traveltracker;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {


    /*
        Simple checking to be sure the database connects, if not it will throw an exception
     */
    @Test
    void connectionTest(){
        Connection con = Database.connect();
    }


    /*
        Examples of some queries that could be useful, how to use SQL statements
        to store and retrieve values
     */
    @Test
    void queryTest() throws SQLException {
        Connection con = Database.connect();

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
        Database.addAccount(acc);
        Database.printAccounts();
    }

    @Test
    void removeAccountTest() throws SQLException {
        Database.removeAccountByUsername("cam");
        Database.printAccounts();
    }

    @Test
    void addLocationTest() throws SQLException {
        Connection con = Database.connect();

        Location loc = new Location("Taco Shack", "123 Taco Lane");
        Database.addLocation(loc);

        String sql = "SELECT * FROM locations WHERE locationName='Taco Shack'";
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(sql);

        String name = result.getString("locationName");
        String add = result.getString("locationAddress");

        assertEquals("Taco Shack", name);
        assertEquals("123 Taco Lane", add);


    }

    @Test
    void removeLocationTest() throws SQLException{
        Connection con = Database.connect();

        Database.removeLocation("Taco Shack");

        String sql = "SELECT * FROM locations WHERE locationName='Taco Shack'";
        Statement statement = con.createStatement();
        try {
            ResultSet result = statement.executeQuery(sql);
            assertEquals("Taco Shack", result.getString("locationName"));
        }
        catch(SQLException se){
            System.out.println("Working, this means there was nothing to query");
        }

    }

    @Test
    void findLocationByNameTest() throws SQLException{
        ArrayList<Location> filteredLocations = new ArrayList<>();
        filteredLocations = Database.findLocationByName("T");
        for(int i = 0; i < filteredLocations.size() ; i++)
        {
            System.out.println(filteredLocations.get(i).getName());
        }

    }

    @Test
    void findLocationByCategoryTest() throws SQLException{
        //assertEquals("Taco Shack", Database.findLocationByCategory("Fast Food").get(0).getName()); //Category 1 with Location 1
        //assertEquals("Taco Shack", Database.findLocationByCategory("Restaurant").get(0).getName()); //Category 2 with Location 1
        //assertEquals("Banana Town", Database.findLocationByCategory("Fast Food").get(1).getName()); //Category 1 with Location 1
    }
}
