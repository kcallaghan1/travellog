package edu.ithaca.dragon.traveltracker;

import org.sqlite.SQLiteConfig;

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
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            con = DriverManager.getConnection("jdbc:sqlite:Database/TravelTracker.db", config.toProperties());
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


        String sql = "SELECT * FROM locations WHERE locationName LIKE ?";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, "%" + locationName + "%");

        ResultSet result = statement.executeQuery();
        while(result.next()){
            String name = result.getString("locationName");
            String location = result.getString("locationAddress");

            filtered_locations.add(new Location(name, location));
        }
        con.close();
        return filtered_locations;
    }

    public static ArrayList<Location> findLocationByCategory(String category) throws SQLException {
        ArrayList<Location> filtered_locations = new ArrayList<Location>();
        Connection con = Database.connect();


        String sql = "SELECT\n" +
                "       l.locationId, l.locationName, l.locationAddress, ltc.categoryId, c.categoryName\n" +
                "FROM\n" +
                "     locations l\n" +
                "JOIN\n" +
                "    locationToCategory ltc on l.locationId = ltc.locationId\n" +
                "JOIN categories c on ltc.categoryId = c.categoryId\n" +
                "WHERE\n" +
                "    c.categoryName LIKE ?";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, "%" + category+ "%");

        ResultSet result = statement.executeQuery();
        while(result.next()){
            String name = result.getString("locationName");
            String location = result.getString("locationAddress");

            filtered_locations.add(new Location(name, location));
        }
        return filtered_locations;
    }
}
