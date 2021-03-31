package edu.ithaca.dragon.traveltracker;

public class Account{

    private String username;
    private String email;
    private String password;
    ArrayList<TravelLog> tLogs;
    ArrayList<Location> fav;

    public Account(String username, String email, String password){
        this.username=username;
        this.email = email;
        this.password = password;
        tLogs = new ArrayList<TravelLog>();
        fav = new ArrayList<Location>();
    }

    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1 || email.indexOf('.')== -1){
            return false;
        }
        //invalid if string is empty
        else if (email.isEmpty()){
            return false;
        }
        //invalid if '-' is in address. This is low boundary
        else if (email.indexOf('-') != -1){
            return false;
        }
        //invalid if # of characters after last '.' is 2 or less
        else if (email.length() - email.indexOf('.') <= 2){
            return false;
        }
        //invalid if '#' is before '@'
        
        else if (email.indexOf('#') != -1 || email.indexOf('*') != -1 || email.indexOf('$') != -1 || email.indexOf('!') != -1 || email.indexOf('^') != -1){
            return false;
        }
        //invalid if '..' is found. This could be for any 2 symbols
        else if (email.contains("..") || email.contains("@.")){
            return false;
        }

        else if(email.charAt(email.length() -2) == '.'){
            return false;
        }

        else if(email.indexOf('.') != -1 && email.indexOf('@') > email.lastIndexOf('.')){
            return false;
        }
        else {
            return true;
        }
    }

    TravelLog getLogWith(String name){
        return null;
    }

    TravelLog removeLogAt(int i){
        return null;
    }

    Location getFavAt(int i){
        return null;
    }

    Location removeFavAt(int i){
        return null;
    }

    Boolean verifyAccount(String usernameIn, String passIn){
        return false;
    }

    void resetPassword(String p1In, String p2In, String passIn){

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}