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

    public void viewLocationRequests(String listname){
        if (listname.equals("add")){
            System.out.println("Locations to add requests:");
            if(Library.getAddLocationRequests().isEmpty())
                System.out.println("");
            else{
                for (int i = 0; i < Library.getAddLocationRequests().size(); i++){
                    System.out.println(Library.getAddLocationRequests().get(i).getName() + ", " + Library.getAddLocationRequests().get(i).getAddress());
                }
            }
        }
        else if (listname.equals("remove")){
            System.out.println("Locations to remove requests:");
            if(Library.getAddLocationRequests().isEmpty())
                System.out.println("");
            else{
                for (int i = 0; i < Library.getRemoveLocationRequests().size(); i++){
                    System.out.println(Library.getRemoveLocationRequests().get(i).getName() + ", " + Library.getRemoveLocationRequests().get(i).getAddress());
                }
            }
        }
    }

    public void approveRequest(Location name){
        Library.approveLocationRequest(name, "add");
    }
}
