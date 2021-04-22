package edu.ithaca.dragon.traveltracker;

public class Admin {
    private String username;
    private String password;

    public Admin(String username, String password){
        if(username.length()>6){
            this.username=username;
            this.password = password;
        }
        else{
            throw new IllegalArgumentException("Invalid username");
        }
    }

    boolean verifyAccount(String usernameIn, String passIn){

        if(usernameIn.equals(username) && passIn.equals(password)){
            return true;
        }
        return false;
    }

    public void approveRequest(Location name){
        Library.approveLocationRequest(name, "add");
    }
}
