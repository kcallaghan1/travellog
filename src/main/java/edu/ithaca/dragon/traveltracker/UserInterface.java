package edu.ithaca.dragon.traveltracker;

import java.util.Scanner;

public class UserInterface {


    public static Account register(Library lib, Scanner sc){
        boolean userDone = false, emailDone = false, passDone = false;
        String username, email, password;

        System.out.println("Register an account or type 'q' to quit.");

        while(!userDone){
            System.out.println("Username: ");
            String userIn = sc.nextLine();
            if(userIn.equalsIgnoreCase("q")){
                System.exit(0);
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
                System.exit(0);
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
                System.exit(0);
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
        boolean open = true;
        Library lib = new Library();

        System.out.println("Welcome to Travel Tracker!");

        while(open){
            System.out.println("Type 'l' to log in, 'r' to register, or 'q' to quit.");

            Account currentAccount;
            String response = sc.nextLine();

            if(response.equalsIgnoreCase("l")){

            }
            else if(response.equalsIgnoreCase("r")){
                currentAccount = register(lib, sc);
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
