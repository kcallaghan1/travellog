package edu.ithaca.dragon.traveltracker;

import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    public static void addAccount(Account acc){
        String sql = "INSERT INTO accounts(email,username,password,permissions) VALUES(?,?,?,?)";

        try (Connection conn = connectToTest();
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


    public static void updatePassword(Account acc){
        String sql = "UPDATE accounts SET password = ?" + " WHERE accountId = ?";

        try (Connection conn = connectToTest();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, acc.getPassword());
            pstmt.setInt(2, acc.getAccountId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void removeAccountByUsername(String username){
        String sql = "DELETE FROM accounts WHERE username = ?";

        try (Connection conn = connectToTest();
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
        try (Connection conn = connectToTest();
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


    public static ArrayList<Account> getAccounts(){
        String sql = "Select * from accounts";
        ArrayList<Account> accounts = new ArrayList<Account>();
        try (Connection conn = connectToTest();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {

                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int accountId = rs.getInt("accountId");

                accounts.add(new Account(username, email, password, accountId));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }


    public static Account findAccountByUsername(String username) {
        String sql = "SELECT * FROM accounts WHERE username = ?";

        try(Connection conn = connectToTest();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            String email = rs.getString("email");
            String password = rs.getString("password");
            int accountId = rs.getInt("accountId");
            return new Account(username, email, password, accountId);
        }

        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static ArrayList<String> getCategories(){
        String sql = "Select * from categories";
        ArrayList<String> categories = new ArrayList<String>();
        try (Connection conn = connectToTest();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {

                String categoryName = rs.getString("categoryName");

                categories.add(categoryName);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }



    public static void addLocation(Location loc){
        String sql = "INSERT INTO locations(locationName, locationAddress) VALUES(?,?)";

        try (Connection conn = connectToTest();
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

        try (Connection conn = connectToTest();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, loc);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Location> findLocationByName(String locationName) throws SQLException {
        ArrayList<Location> filtered_locations = new ArrayList<Location>();
        Connection con = connectToTest();


        String sql = "SELECT * FROM locations WHERE locationName LIKE ?";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, "%" + locationName + "%");

        ResultSet result = statement.executeQuery();
        while(result.next()){
            String name = result.getString("locationName");
            String location = result.getString("locationAddress");
            int locationId = result.getInt("locationId");
            ArrayList<String> categories = getCategoriesByLocation(locationId);

            filtered_locations.add(new Location(name, location, categories, locationId));
        }
        con.close();
        return filtered_locations;
    }

    public static ArrayList<Location> findLocationByCategory(String category) throws SQLException {
        ArrayList<Location> filtered_locations = new ArrayList<Location>();
        Connection con = connectToTest();


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


    public static ArrayList<String> getCategoriesByLocation(int locationId) {
        ArrayList<String> categories = new ArrayList<String>();
        String sql = "Select * from locationToCategory join categories using(categoryId) where locationId = ?";

        try (Connection conn = connectToTest();
             PreparedStatement statement = conn.prepareStatement(sql)){

            statement.setInt(1, locationId);

            ResultSet rs = statement.executeQuery();

            // loop through the result set
            while (rs.next()) {

                categories.add(rs.getString("categoryName"));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categories;
    }



    public static ArrayList<Location> getLocations() throws SQLException {
        ArrayList<Location> locations = new ArrayList<Location>();
        String sql = "Select * from locations";
        try (Connection conn = connectToTest();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {

                int locationId = rs.getInt("locationId");
                String name = rs.getString("locationName");
                String address = rs.getString("locationAddress");
                ArrayList<String> categories = getCategoriesByLocation(locationId);

                locations.add(new Location(name, address, categories, locationId));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return locations;
    }


    public static ArrayList<Location> getLocationsFromTravelLog(TravelLog log){
        ArrayList<Location> locations = new ArrayList<Location>();

        String sql = "Select locationName, locationAddress, locationId from loggedLocations join locations using(locationId) where logId = ?";
        try (Connection conn = connectToTest();
             PreparedStatement statement = conn.prepareStatement(sql)){

            statement.setInt(1, log.getLogId());

            ResultSet rs = statement.executeQuery();

            // loop through the result set
            while (rs.next()) {

                String locationName = rs.getString("locationName");
                String locationAddress = rs.getString("locationAddress");
                int locationId = rs.getInt("locationId");
                ArrayList<String> categories = getCategoriesByLocation(locationId);


                locations.add(new Location(locationName, locationAddress, categories, locationId));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return locations;
    }




    public static void addTravelLog(TravelLog log, Account acc){
        String sql = "INSERT INTO travelLogs(accountId, logName, logDescription) VALUES(?,?,?)";

        try (Connection conn = connectToTest();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, acc.getAccountId());
            pstmt.setString(2, log.getTitle());
            pstmt.setString(3, log.getDescription());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Location> getLoggedLocations(){
        ArrayList<Location> locations = new ArrayList<Location>();
        String sql = "Select * from loggedLocations join Locations using(locationId)";
        try (Connection conn = connectToTest();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {

                int locationId = rs.getInt("locationId");
                String name = rs.getString("locationName");
                String address = rs.getString("locationAddress");
                ArrayList<String> categories = getCategoriesByLocation(locationId);

                locations.add(new Location(name, address, categories, locationId));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return locations;
    }


    public static void removeTravelLog(int logId){
        String sql = "DELETE FROM travelLogs where logId = ?";

        try (Connection conn = connectToTest();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,logId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public static void addLocationToTravelLog(int locationId, int logId){
        String sql = "insert into loggedLocations(logId, locationId) values(?,?)";

        try (Connection conn = connectToTest();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, logId);
            pstmt.setInt(2,locationId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void removeLocationFromTravelLog(int locationId, int logId){
        String sql = "DELETE FROM loggedLocations where (logId = ? and locationId = ?)";

        try (Connection conn = connectToTest();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, logId);
            pstmt.setInt(2,locationId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static ArrayList<TravelLog> getTravelLogsByAccount(Account acc) throws SQLException {
        ArrayList<TravelLog> logs = new ArrayList<TravelLog>();

        String sql = "Select logName, logDescription, logId from travelLogs where accountId = ?";
        try (Connection conn = connectToTest();
             PreparedStatement statement = conn.prepareStatement(sql)){

            statement.setInt(1, acc.getAccountId());

            ResultSet rs = statement.executeQuery();

            // loop through the result set
            while (rs.next()) {

                String logName = rs.getString("logName");
                String logDescription = rs.getString("logDescription");
                int logId = rs.getInt("logId");

                logs.add(new TravelLog(logName, logDescription, logId));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return logs;
    }
    

    public static Connection connectToTest(){
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            con = DriverManager.getConnection("jdbc:sqlite:Database/TravelTrackerTest.db", config.toProperties());
            //System.out.println("Connected To Database");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e+"");
        }
        return con;
    }


    /*
        Simple checking to be sure the database connects, if not it will throw an exception
     */
    @Test
    void connectionTest(){
        Connection con = connectToTest();
    }


    /*
        Examples of some queries that could be useful, how to use SQL statements
        to store and retrieve values
     */
    @Test
    void queryTest() throws SQLException {
        Connection con = connectToTest();

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
        addAccount(acc);
        printAccounts();
    }

    @Test
    void removeAccountTest() throws SQLException {
        removeAccountByUsername("cam");
        printAccounts();
    }

    @Test
    void getAccountsTest() throws SQLException {
        ArrayList<Account> accounts = getAccounts();
        for(Account cur : accounts){
            System.out.println(cur.getUsername());
            System.out.println(cur.getEmail());
        }
    }

    @Test
    void findAccountByUsernameTest() throws SQLException {
        Account acc = new Account("admin", "admin@gmail.com", "admin123");
        Account returned = findAccountByUsername("admin");
        assertEquals(acc.getUsername(), returned.getUsername());
        assertEquals(acc.getEmail(), returned.getEmail());
        assertEquals(acc.getPassword(), returned.getPassword());
    }

    @Test
    void updatePasswordTest() throws SQLException {
        Account acc = findAccountByUsername("cam");
        acc.resetPassword("cam1234", "cam1234", "cam123");
        updatePassword(acc);
        Account acc2 = findAccountByUsername("cam");
        assertEquals(acc2.getPassword(), "cam1234");
    }

    @Test
    void addLocationTest() throws SQLException{
        Location loc = new Location("removeTest", "123 remove test");
        addLocation(loc);
    }

    @Test
    void RemoveLocationTest() throws SQLException{
        Connection con = connectToTest();

        Location loc = new Location("removeTest", "123 remove test");
        addLocation(loc);

        removeLocation("removeTest");

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

    }

    @Test
    void findLocationByNameTest() throws SQLException{
        ArrayList<Location> filteredLocations = new ArrayList<>();
        filteredLocations = findLocationByName("T");
        for(int i = 0; i < filteredLocations.size() ; i++)
        {
            System.out.println(filteredLocations.get(i).getName());
        }
    }

    @Test
    void findLocationByCategoryTest() throws SQLException{
        ArrayList<Location> filteredLocations = new ArrayList<>();
        System.out.println("Search String: 'Restaurant'");
        filteredLocations = findLocationByCategory("Restaurant");
        for(int i = 0; i < filteredLocations.size() ; i++)
        {
            System.out.println(filteredLocations.get(i).getName());
        }
    }


    @Test
    void addLocationToTravelLogTest() throws SQLException {
        Location loc = findLocationByName("Madison Square Garden").get(0);
        System.out.println(loc.getLocationId() + ". " + loc.getName() + ", " + loc.getAddress());
        ArrayList<Location> locations = getLoggedLocations();
        for(Location cur : locations){
            System.out.println(cur.getLocationId() + ". " + cur.getName() + ", " + cur.getAddress());
        }
        addLocationToTravelLog(loc.getLocationId(), 1);
        for(Location cur : locations){
            System.out.println(cur.getLocationId() + ". " + cur.getName() + ", " + cur.getAddress());
        }
    }


    @Test
    void removeLocationFromTravelLogTest() {
        ArrayList<Location> loggedLocations = getLoggedLocations();
        TravelLog log = new TravelLog("New York", "New York", 1);
        for(Location loc : loggedLocations){
            System.out.println(loc.getLocationId() + ". " + loc.getName() + ", " + loc.getAddress());
        }
        removeLocationFromTravelLog(2, 1);
        loggedLocations = getLocationsFromTravelLog(log);
        for(Location loc : loggedLocations){
            System.out.println(loc.getLocationId() + ". " + loc.getName() + ", " + loc.getAddress());
        }
    }
}
