package edu.ithaca.dragon.traveltracker;

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
    
    public static void main(String[] args){
        
        Scanner sc = new Scanner(System.in);
        boolean open = true, loggedIn = false;
        Library lib = new Library();

        System.out.println("Welcome to Travel Tracker!");

        while(open){
            while(!loggedIn){
                System.out.println("Type 'l' to log in, 'r' to register, or 'q' to quit.");

                Account currentAccount;
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
                    System.exit(0);
                }
                else{
                    System.out.println("Please enter a valid input!");
                }
            }
        }
    }
}
