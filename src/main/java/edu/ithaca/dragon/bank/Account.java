package edu.ithaca.dragon.bank;

public class Account{

    private String username;
    private String email;
    private String password;
    ArrayList<TravelLog> tLogs;
    ArrayList<Location> fav;

    public Account(String username, String email, String password){
        if(isEmailValid(email) || username.length()<6){
            this.username=username;
            this.email = email;
            this.password = password;
            tLogs = new ArrayList<TravelLog>();
            fav = new ArrayList<Location>();
        }
        else{
            throw new IllegalArgumentException("Invalid email or username");
        }
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
        for(int i = 0; i<tLogs.length; i++){
            if(tLogs[i].getName ==  name){
                return tLogs[i];
            }
        }
        return null;
    }

    TravelLog getLogAt(int i){
        return tLogs.get(i);
    }

    TravelLog removeLogAt(int i){
        return tLogs.remove(i);
    }

    Location getFavAt(int i){
        return fav.get(i);
    }

    Location removeFavAt(int i){
        return fav.remove(i);
    }

    Boolean verifyAccount(String usernameIn, String passIn){
        if(usernameIn==username && passIn == password){
            return true;
        }
        return false;
    }

    boolean resetPassword(String p1In, String p2In, String passIn){
        if(p1In == p2In && passIn == password){
            password = p1In;
            return true;
        }
        else{
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}