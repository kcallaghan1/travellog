package edu.ithaca.dragon.traveltracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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

    public static void addAccount(Account acc){
        String sql = "INSERT INTO accounts(email,username,password,permissions) VALUES(?,?,?,?)";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, acc.getEmail());
            pstmt.setString(2, acc.getUsername());
            pstmt.setString(3, acc.getPassword());
            pstmt.setString(4, "user");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void removeAccountByUsername(String username){
        String sql = "DELETE FROM accounts WHERE username = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, username);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void printAccounts(){
        String sql = "Select * from accounts";
        try (Connection conn = Database.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("accountId") +  "\t" + 
                                   rs.getString("email") + "\t" +
                                   rs.getString("username") + "\t" +
                                   rs.getString("password") + "\t" +
                                   rs.getString("permissions"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addLocation(Location loc){
        String sql = "INSERT INTO locations(locationName, locationAddress) VALUES(?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loc.getName());
            pstmt.setString(2, loc.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeLocation(String loc){
        String sql = "DELETE FROM locations WHERE locationName = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, loc);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Location> findLocationByName(String locationName) throws SQLException {
        ArrayList<Location> filtered_locations = new ArrayList<Location>();
        Connection con = Database.connect();
        int count = 0;


        String sql = "SELECT * FROM locations WHERE locationName LIKE ?";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, "%" + locationName + "%");

        ResultSet result = statement.executeQuery();
        while(result.next()){
            count++;
            String name = result.getString("locationName");
            String location = result.getString("locationAddress");

            filtered_locations.add(new Location(name, location));
        }
        return filtered_locations;
    }
}
