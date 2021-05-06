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
    public String dbPath;

    public Database(String databasePath){
        this.dbPath = databasePath;
    }

    public Connection connect(){
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            con = DriverManager.getConnection(dbPath, config.toProperties());
            //System.out.println("Connected To Database");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e+"");
        }
        return con;
    }

    public void addAccount(Account acc){
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


    public  void updatePassword(Account acc){
        String sql = "UPDATE accounts SET password = ?" + " WHERE accountId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, acc.getPassword());
            pstmt.setInt(2, acc.getAccountId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public  void removeAccountByUsername(String username){
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


    public  void printAccounts(){
        String sql = "Select * from accounts";
        try (Connection conn = connect();
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


    public  ArrayList<Account> getAccounts(){
        String sql = "Select * from accounts";
        ArrayList<Account> accounts = new ArrayList<Account>();
        try (Connection conn = connect();
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


    public  Account findAccountByUsername(String username) {
        String sql = "SELECT * FROM accounts WHERE username = ?";

        try(Connection conn = connect();
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


    public  ArrayList<String> getCategories(){
        String sql = "Select * from categories";
        ArrayList<String> categories = new ArrayList<String>();
        try (Connection conn = connect();
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



    public  void addLocation(Location loc){
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

    public  void removeLocation(String loc){
        String sql = "DELETE FROM locations WHERE locationName = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, loc);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public  ArrayList<Location> findLocationByName(String locationName) throws SQLException {
        ArrayList<Location> filtered_locations = new ArrayList<Location>();
        Connection con = connect();


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

    public  ArrayList<Location> findLocationByCategory(String category) throws SQLException {
        ArrayList<Location> filtered_locations = new ArrayList<Location>();
        Connection con = connect();


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


    public  ArrayList<String> getCategoriesByLocation(int locationId) {
        ArrayList<String> categories = new ArrayList<String>();
        String sql = "Select * from locationToCategory join categories using(categoryId) where locationId = ?";

        try (Connection conn = connect();
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



    public  ArrayList<Location> getLocations() throws SQLException {
        ArrayList<Location> locations = new ArrayList<Location>();
        String sql = "Select * from locations";
        try (Connection conn = connect();
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


    public  ArrayList<Location> getLocationsFromTravelLog(TravelLog log){
        ArrayList<Location> locations = new ArrayList<Location>();

        String sql = "Select locationName, locationAddress, locationId from loggedLocations join locations using(locationId) where travelLogId = ?";
        try (Connection conn = connect();
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




    public  void addTravelLog(TravelLog log, Account acc){
        String sql = "INSERT INTO travelLogs(accountId, logName, logDescription) VALUES(?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, acc.getAccountId());
            pstmt.setString(2, log.getTitle());
            pstmt.setString(3, log.getDescription());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public  ArrayList<Location> getLoggedLocations(){
        ArrayList<Location> locations = new ArrayList<Location>();
        String sql = "Select * from loggedLocations join Locations using(locationId)";
        try (Connection conn = connect();
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


    public  void removeTravelLog(int travelLogId){
        String sql = "DELETE FROM travelLogs where travelLogId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,travelLogId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public  void addLocationToTravelLog(int locationId, int travelLogId){
        String sql = "insert into loggedLocations(travelLogId, locationId) values(?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, travelLogId);
            pstmt.setInt(2,locationId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public  void removeLocationFromTravelLog(int locationId, int travelLogId){
        String sql = "DELETE FROM loggedLocations where (travelLogId = ? and locationId = ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, travelLogId);
            pstmt.setInt(2,locationId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public  ArrayList<TravelLog> getTravelLogsByAccount(Account acc) throws SQLException {
        ArrayList<TravelLog> logs = new ArrayList<TravelLog>();

        String sql = "Select logName, logDescription, travelLogId from travelLogs where accountId = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(sql)){

            statement.setInt(1, acc.getAccountId());

            ResultSet rs = statement.executeQuery();

            // loop through the result set
            while (rs.next()) {

                String logName = rs.getString("logName");
                String logDescription = rs.getString("logDescription");
                int travelLogId = rs.getInt("travelLogId");

                logs.add(new TravelLog(logName, logDescription, travelLogId));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return logs;
    }

    public void addPicture(String picPath, Account acc, int locationId){
        String sql = "insert into pictures(accountId, locationId, picturePath) values(?,?,?)";

        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, acc.getAccountId());
                pstmt.setInt(2, locationId);
                pstmt.setString(3, picPath);
                pstmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void addLocationRequest(Location loc){
        String sql = "INSERT INTO requestedLocations(locationName, locationAddress) VALUES(?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loc.getName());
            pstmt.setString(2, loc.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeLocationRequest(Location loc){
        String sql = "DELETE FROM requestedLocations where locationName = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loc.getName());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public  ArrayList<Location> getRequestedLocations() throws SQLException {
        ArrayList<Location> locations = new ArrayList<Location>();
        String sql = "Select * from requestedLocations";
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {

                String name = rs.getString("locationName");
                String address = rs.getString("locationAddress");

                locations.add(new Location(name, address));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return locations;
    }


    public  Location findRequestedLocationByName(String locationName) throws SQLException {
        Connection con = connect();

        ArrayList<Location> locations = new ArrayList<Location>();

        String sql = "SELECT * FROM requestedLocations WHERE locationName LIKE ?";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, "%" + locationName + "%");

        ResultSet result = statement.executeQuery();
        while(result.next()){
            String name = result.getString("locationName");
            String address = result.getString("locationAddress");

            locations.add(new Location(name, address));
        }
        con.close();
        return locations.get(0);
    }


    public void addFavoriteLocation(Account acc, Location loc) {
        String sql = "INSERT INTO favorites(accountId, locationId) VALUES(?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, acc.getAccountId());
            pstmt.setInt(2, loc.getLocationId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void removeFavoriteLocation(int locationId){
        String sql = "DELETE FROM favorites WHERE locationId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, locationId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public ArrayList<Location> getFavoriteLocations(){
        String sql = "Select * from favorites join locations using (locationId)";
        ArrayList<Location> favoriteLocations = new ArrayList<Location>();
        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {

                int locationId = rs.getInt("locationId");
                String locationName = rs.getString("locationName");
                String locationAddress = rs.getString("locationAddress");
                ArrayList<String> categories = getCategoriesByLocation(locationId);

                favoriteLocations.add(new Location(locationName, locationAddress, categories, locationId));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return favoriteLocations;
    }

    public ArrayList<Pictures> getPictures(){
        String sql = "Select * from pictures";
        ArrayList<Pictures> pics = new ArrayList<Pictures>();

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {

                pics.add(new Pictures());
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pics;
    }
}
