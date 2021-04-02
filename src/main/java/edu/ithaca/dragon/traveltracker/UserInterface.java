package edu.ithaca.dragon.traveltracker;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

    public static Account login(Library lib, Scanner sc){
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
                for(int i = 0; i < lib.accountList.size(); i++){
                    if(lib.accountList.get(i).getUsername().equals(userIn)){
                        if(lib.accountList.get(i).getPassword().equals(passIn)){
                            loginSuccess = true;
                            acc = lib.accountList.get(i);
                        }
                    }
                }
                System.out.println("Username or password are invalid.\n");
            }
        }
        return acc;
    }    
    
    
    public static Account register(Library lib, Scanner sc){
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
        lib.addAccount(acc);
        return acc;
    }


    public static void openTravelLogs(Account currentAccount, Library lib, Scanner sc){

        boolean open = true;
        while(open){
            System.out.println("Select a travel log, 'b' for back, or 'q' to quit.");

            for(int i = 0; i < currentAccount.getTravelLogs().size(); i++){
                System.out.println((i+1) + ". " + currentAccount.getLogAt(i).getTitle());
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
            else if(Integer.parseInt(stringIn) <= currentAccount.getTravelLogs().size()){
                openLog(currentAccount.getLogAt(Integer.parseInt(stringIn) - 1), currentAccount, sc);
            }
            else{
                System.out.println("Please enter a valid input!");
            }
        }
    }


    public static void openLog(TravelLog log, Account currentAccount, Scanner sc){
        boolean open = true;
        while(open){
            System.out.println("Title: " + log.getTitle());
            System.out.println("Description: " + log.getDescription());
            for(int i = 0; i < log.getPlaces().size(); i++){
                System.out.println(i+1 + ". " + log.getPlaces().get(i).getName());
            }
            System.out.println("'a' to add, 'r' to remove, 'b' to go back, or 'q' to quit.");

            /**
             * TODO: Add functionality for editing log.
             */
        }
    }


    public static void openFavorites(Account currentAccount, Scanner sc){

    }

    public static void changePassword(Account currentAccount, Scanner sc){

    }



    public static void home(Account currentAccount, Library lib, Scanner sc){
        boolean open = true;
        while(open){
            System.out.println("Welcome " + currentAccount.getUsername() + "!");
            System.out.println("Please select an option: ");
            System.out.println("1. View Travel Logs");
            System.out.println("2. View Favorite Locations");
            System.out.println("3. Search for a Location"); // TODO
            System.out.println("4. Change password");
            System.out.println("5. Logout");
            System.out.println("6. Quit");


            int userInput = Integer.parseInt(sc.nextLine());
            switch(userInput){
                case 1:
                    openTravelLogs(currentAccount, lib, sc);
                    break;
                case 2:
                    openFavorites(currentAccount, sc);
                    break;
                case 3:
                    System.out.println("Search is not yet available");
                    break;
                case 4:
                    changePassword(currentAccount, sc);
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
    
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        boolean open = true, loggedIn = false;
        Library lib = new Library();

        System.out.println("Welcome to Travel Tracker!");

        while(open){
            Account currentAccount = null;
            while(!loggedIn){
                System.out.println("Type 'l' to log in, 'r' to register, or 'q' to quit.");

                String response = sc.nextLine();

                if(response.equalsIgnoreCase("l")){
                    currentAccount = login(lib, sc);
                    if(currentAccount != null){
                        loggedIn = true;
                    }
                }
                else if(response.equalsIgnoreCase("r")){
                    currentAccount = register(lib, sc);
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

            home(currentAccount, lib, sc);
        }
    }
}
