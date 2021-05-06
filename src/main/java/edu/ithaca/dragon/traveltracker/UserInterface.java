package edu.ithaca.dragon.traveltracker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

    public static Database Database = new Database("jdbc:sqlite:traveltracker/Database/TravelTracker.db");

    public static Account login(Scanner sc){
        boolean loginSuccess = false;
        String userIn, passIn;
        Account acc = null;
        while(!loginSuccess){
            System.out.println("Please enter your username and password, 'b' to go back, or 'q' to quit.");

            System.out.println("Username: ");
            userIn = sc.nextLine();

            if(userIn.equalsIgnoreCase("q")){
                sc.close();
                System.exit(0);
            }

            System.out.println("Password: ");
            passIn = sc.nextLine();
            
            if(userIn.equalsIgnoreCase("q")){
                sc.close();
                System.exit(0);
            }
            else if(userIn.equalsIgnoreCase("b")){
                return null;
            }
            else {
                acc = Database.findAccountByUsername(userIn);
                if(acc != null){
                    if(passIn.equals(acc.getPassword())){
                        loginSuccess = true;
                        break;
                    }
                }
            }
            System.out.println("Username or password are invalid.\n");
        }
        return acc;
    }    
    
    
    public static Account register(Scanner sc){
        boolean userDone = false, emailDone = false, passDone = false;
        String username = "", email = "", password = "";

        System.out.println("Register an account or type 'b' to go back or 'q' to quit.");

        while(!userDone){
            System.out.println("Username: ");
            String userIn = sc.nextLine();
            if(userIn.equalsIgnoreCase("q")){
                sc.close();
                System.exit(0);
            }
            else if(userIn.equalsIgnoreCase("b")){
                return null;
            }
            else{
                username = userIn;
                userDone = true;
            }
        }

        while(!emailDone){
            System.out.println("Email: ");
            String emailIn = sc.nextLine();
            if(emailIn.equalsIgnoreCase("q")){
                sc.close();
                System.exit(0);
            }
            else if(emailIn.equalsIgnoreCase("b")){
                return null;
            }
            else if(Account.isEmailValid(emailIn)){
                email = emailIn;
                emailDone = true;
            }
            else{
                System.out.println("Please enter a valid email!");
            }
        }

        while(!passDone){
            System.out.println("Password: ");
            String passIn = sc.nextLine();
            if(passIn.equalsIgnoreCase("q")){
                sc.close();
                System.exit(0);
            }
            else if(passIn.equalsIgnoreCase("b")){
                return null;
            }
            else{
                password = passIn;
                passDone = true;
            }
        }
        Account acc = new Account(username, email, password);
        Database.addAccount(acc);
        return acc;
    }


    public static void openTravelLogs(Account currentAccount, Scanner sc) throws SQLException {

        boolean open = true;


        while(open){
            ArrayList<TravelLog> logs = Database.getTravelLogsByAccount(currentAccount);
            System.out.println("Select a travel log, 'n' to create new log, 'r' to remove a log, 'b' for back, or 'q' to quit.");

            for(int i = 0; i < logs.size(); i++){
                System.out.println((i+1) + ". " + logs.get(i).getTitle());
            }

            String stringIn = sc.nextLine();
            if(stringIn.equalsIgnoreCase("b")){
                open = false;
                return;
            }
            else if(stringIn.equalsIgnoreCase("q")){
                sc.close();
                System.exit(0);
            }
            else if(stringIn.equalsIgnoreCase("n")){
                makeNewLog(currentAccount, sc);
            }
            else if(stringIn.equalsIgnoreCase("r")){
                System.out.println("Select a log to remove:");
                stringIn = sc.nextLine();
                Database.removeTravelLog(logs.get(Integer.parseInt(stringIn)-1).getLogId());
            }
            else if(Integer.parseInt(stringIn) <= logs.size()){
                openLog(logs.get(Integer.parseInt(stringIn) - 1), currentAccount, sc);
            }
            else{
                System.out.println("Please enter a valid input!");
            }
        }
    }


    private static void makeNewLog(Account currentAccount, Scanner sc) {
        System.out.println("Enter a name for travel log:");
        String nameIn = sc.nextLine();
        System.out.println("Enter a description for the log:");
        String descriptionIn = sc.nextLine();
        TravelLog log = new TravelLog(nameIn, descriptionIn);
        Database.addTravelLog(log, currentAccount);
    }


    public static void openLog(TravelLog log, Account currentAccount, Scanner sc){
        boolean open = true;
        while(open){
            System.out.println("Title: " + log.getTitle());
            System.out.println("Description: " + log.getDescription());
            ArrayList<Location> locations = Database.getLocationsFromTravelLog(log);
            for(int i = 0; i < locations.size(); i++){
                System.out.println(i+1 + ". " + locations.get(i).getName());
            }
            System.out.println("'a' to add, 'r' to remove, 'b' to go back, or 'q' to quit.");

            String stringIn = sc.nextLine();
            if(stringIn.equalsIgnoreCase("b")){
                open = false;
                return;
            }
            else if(stringIn.equalsIgnoreCase("q")){
                sc.close();
                System.exit(0);
            }
            else if(stringIn.equalsIgnoreCase("r")){
                System.out.println("Select a location to remove or 'b' to go back.\n");
                stringIn = sc.nextLine();
                if(stringIn.equalsIgnoreCase("b")){
                    return;
                }
                else if(Integer.parseInt(stringIn) < 0 || Integer.parseInt(stringIn) > locations.size()){
                    System.out.println("Please select a valid location!");
                }
                else{
                    Location locationToRemove = locations.get(Integer.parseInt(stringIn) - 1);
                    Database.removeLocationFromTravelLog(locationToRemove.getLocationId(), log.getLogId());
                }
            }
            else if(stringIn.equalsIgnoreCase("a")){
                System.out.println("Select a location to add: ");
                try {
                    ArrayList<Location> allLocations = Database.getLocations();
                    for(Location loc : allLocations){
                        if(!locations.contains(loc)){
                            System.out.println(loc.getLocationId() + ". " + loc.getName() + ", " + loc.getAddress());
                        }
                    }
                    stringIn = sc.nextLine();
                    Database.addLocationToTravelLog(Integer.parseInt(stringIn), log.getLogId());
                } catch (SQLException e) {
                }
                
            }

        }
    }


    public static void openFavorites(Account currentAccount, Scanner sc){
        Database.getFavoriteLocations();
    }

    public static void changePassword(Account currentAccount, Scanner sc){

        System.out.println("Current password: \n");
        String oldPass = sc.nextLine();
        System.out.println("New password: \n");
        String newPass = sc.nextLine();
        System.out.println("Confirm new password: \n");
        String confNewPass = sc.nextLine();

        if(currentAccount.resetPassword(newPass, confNewPass, oldPass)){
            Database.updatePassword(currentAccount);
        }
        else{
            System.out.println("Current password is incorrect or new passwords do not match.\n");
        }
    }



    public static void home(Account currentAccount, Scanner sc){
        boolean open = true;
        while(open){
            System.out.println("Welcome " + currentAccount.getUsername() + "!");
            System.out.println("Please select an option: ");
            System.out.println("1. View Travel Logs");
            System.out.println("2. View Favorite Locations"); // TODO
            System.out.println("3. Search for a Location"); // TODO
            System.out.println("4. Change password");
            System.out.println("5. Logout");
            System.out.println("6. Quit");


            int userInput = 0;

            try{
                userInput = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e){

            }
            switch(userInput){
                case 0:
                    System.out.println("Undefined input, please enter a proper integer");
                    break;
                case 1:
                try {
                    openTravelLogs(currentAccount, sc);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                    break;
                case 2:
                    openFavorites(currentAccount, sc);
                    break;
                case 3:
                    searchLocation(currentAccount, sc);
                    break;
                case 4:
                    changePassword(currentAccount, sc);
                    break;
                case 5:
                    open = false;
                    return;
                case 6:
                    open = false;
                    sc.close();
                    System.exit(0);
            }
        }
    }

    private static void searchAccounts(Account currentAccount, Scanner sc){
        boolean open = true;
        while(open){
            System.out.println("Enter an account name, type 'e' to edit an account, type 'd' to dump all accounts, 'b' to go back, or 'q' to quit");
            String stringIn = sc.nextLine();
            if(stringIn.equalsIgnoreCase("b")){
                open = false;
                return;
            }
            else if(stringIn.equalsIgnoreCase("q")){
                sc.close();
                System.exit(0);
            }
            else if(stringIn.equalsIgnoreCase("d")){
                Database.printAccounts();
            }
            else if(stringIn.equalsIgnoreCase("e")){
                System.out.println("Please enter the exact username of the account you would like to edit.");
                stringIn = sc.nextLine();
                Account a = Database.findAccountByUsername(stringIn);
                if(a == null){
                    System.out.println("Username not found.");
                }
                else{
                    System.out.println("Type 'a' to toggle permissions, 'u' to edit username, 'p' to change password, or 'e' to change email");
                    stringIn = sc.nextLine();
                    if(stringIn.equalsIgnoreCase("a")){
                        a.togglePermissions();
                        Database.updatePermissions(a);;
                    }
                    else if(stringIn.equalsIgnoreCase("u")){
                        System.out.println("Please enter a new username.");
                        String username = sc.nextLine();
                        a.setUsername(username);
                        Database.updateUsername(a);
                    }
                    else if(stringIn.equalsIgnoreCase("p")){
                        System.out.println("Please enter a new password.");
                        String password = sc.nextLine();
                        a.setPassword(password);
                        Database.updatePassword(a);;
                    }
                    else if(stringIn.equalsIgnoreCase("e")){
                        System.out.println("Please enter a new email.");
                        String email = sc.nextLine();
                        a.setEmail(email);
                        Database.updateEmail(a);
                    }
                    else{
                        System.out.println("Undefined command.");
                    }
                }
            }
            else{
                ArrayList<Account> accountList = Database.getAccountsWith(stringIn);
                for(int i = 0; i < accountList.size(); i++){
                    System.out.println(accountList.get(i).getAccountId() +  "\t" +
                        accountList.get(i).getEmail() + "\t" +
                        accountList.get(i).getUsername() + "\t" +
                        accountList.get(i).getPassword() + "\t" +
                        accountList.get(i).getPermissions());
                }
            }
        }
    }
    
    private static void searchLocation(Account currentAccount, Scanner sc) {
        System.out.println("Enter location name or press 'c' to see categories:");
        String stringIn = sc.nextLine();
        if(stringIn.equalsIgnoreCase("c")){
            ArrayList<String> categories = Database.getCategories();
            for(int i = 0; i < categories.size(); i++){
                System.out.println(i+1 + ". " + categories.get(i));
            }
            int categoryNum = Integer.parseInt(sc.nextLine());
            try {
                ArrayList<Location> locations = Database.findLocationByCategory(categories.get(categoryNum-1));
                for(int i = 0; i < locations.size(); i++){
                    System.out.println(i+1 + ". " + locations.get(i).getName() + ", " + locations.get(i).getAddress());
                }
            } catch (SQLException e) {
            }
        }
        else{
            String locationName = stringIn;
            try {
                ArrayList<Location> locations = Database.findLocationByName(locationName);
                for(int i = 0; i < locations.size(); i++){
                    System.out.println(i+1 + ". " + locations.get(i).getName() + ", " + locations.get(i).getAddress());
                }
            } catch (SQLException e) {
            }
        }
    }

    public static void adminHome(Account currentAccount, Scanner sc){
        boolean open = true;
        while(open){
            System.out.println("Welcome " + currentAccount.getUsername() + "!");
            System.out.println("Please select an option: ");
            System.out.println("1. View Location Add Requests");
            System.out.println("2. Search for a location"); // TODO
            System.out.println("3. Search for an account");
            System.out.println("4. Add new Location");
            System.out.println("5. Remove Location");
            System.out.println("6. Change Password");
            System.out.println("7. Logout");
            System.out.println("8. Quit");

            int userInput = 0;

            try{
                userInput = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e){

            }
            switch(userInput){
                case 0:
                    System.out.println("Undefined input, please enter a proper integer");
                    break;
                case 1:
                    viewLocationAddRequests(currentAccount,sc);
                    break;
                case 2:
                    searchLocation(currentAccount, sc);
                    break;
                case 3:
                    searchAccounts(currentAccount, sc);
                    break;
                case 4:
                    addNewLocation(currentAccount, sc);
                    break;
                case 5:
                    removeLocation(currentAccount, sc);
                    break;
                case 6:
                    changePassword(currentAccount, sc);
                    break;
                case 7:
                    open = false;
                    return;
                case 8:
                    open = false;
                    sc.close();
                    System.exit(0);
            }
        }
    }

    private static void viewLocationAddRequests(Account currentAccount, Scanner sc) {
        try{
            ArrayList<Location> l = Database.getRequestedLocations();
            for(int i = 0; i<l.size(); i++){
                System.out.println(l.get(i).getName() + "\t" + l.get(i).getAddress());
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    private static void removeLocation(Account currentAccount, Scanner sc) {
        System.out.println("Please enter the name of the location you would like to remove: ");
        String userString = sc.nextLine();
        Database.removeLocation(userString);
    }


    private static void addNewLocation(Account currentAccount, Scanner sc) {
        boolean open = true;
        while(open){
            System.out.println("Please enter a name for your location: ");
            String name = sc.nextLine();
            System.out.println("Please enter an address for your location: ");
            String address = sc.nextLine();
            Location l = new Location(name, address);
            Database.addLocation(l);
            ArrayList<String> cat = Database.getCategories();
            for(int i = 0; i < cat.size(); i++){
                System.out.println(i + cat.get(i));
            }
            System.out.println("Enter as many categories from the list as you'd like, type 'q' to stop");
            String userInput = "";
            while(!userInput.equalsIgnoreCase("q")){
                userInput = sc.nextLine();
                if(userInput.equalsIgnoreCase("q")){
                    break;
                }
                try{
                    l.addCat(cat.get(Integer.parseInt(userInput)));
                }
                catch(Exception e){
                    System.out.println("Invalid arguement.");
                }
            }
            System.out.println("Successfully added: " + name);
            open = false;
        }
    }


    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        boolean open = true, loggedIn = false;

        System.out.println("Welcome to Travel Tracker!");

        while(open){
            Account currentAccount = null;
            while(!loggedIn){
                System.out.println("Type 'l' to log in, 'r' to register, or 'q' to quit.");

                String response = sc.nextLine();

                if(response.equalsIgnoreCase("l")){
                    currentAccount = login(sc);
                    if(currentAccount != null){
                        loggedIn = true;
                    }
                }
                else if(response.equalsIgnoreCase("r")){
                    currentAccount = register(sc);
                    if(currentAccount != null){
                        loggedIn = true;
                    }
                }
                else if(response.equalsIgnoreCase("q")){
                    sc.close();
                    open = false;
                    System.exit(0);
                }
                else{
                    System.out.println("Please enter a valid input!");
                }
            }
            if(currentAccount.getPermissions().equalsIgnoreCase("user")){
                home(currentAccount, sc);
            }
            else if(currentAccount.getPermissions().equalsIgnoreCase("admin")){
                adminHome(currentAccount,sc);
            }
            else{
                System.out.println(currentAccount.getPermissions());
                System.out.println("Fatal Error: Undefined Permissions");
            }
            loggedIn = false;
        }
    }
}
