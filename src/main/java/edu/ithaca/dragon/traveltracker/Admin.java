package edu.ithaca.dragon.traveltracker;

public class Admin extends Library{
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
            if(addLocationRequests.isEmpty())
                System.out.println("");
            else{
                for (int i = 0; i < addLocationRequests.size(); i++){
                    System.out.println(addLocationRequests.get(i).getName() + ", " + addLocationRequests.get(i).getAddress());
                }
            }
        }
        else if (listname.equals("remove")){
            System.out.println("Locations to remove requests:");
            if(Library.removeLocationRequests.isEmpty())
                System.out.println("");
            else{
                for (int i = 0; i < removeLocationRequests.size(); i++){
                    System.out.println(removeLocationRequests.get(i).getName() + ", " + removeLocationRequests.get(i).getAddress());
                }
            }
        }
    }

    public void approveAddRequest(Location name){
        locationList.add(name);
        addLocationRequests.remove(name);
    }

    public void approveRemoveRequest(Location name){
        locationList.remove(name);
        removeLocationRequests.remove(name);
    }

    public void denyAddRequest(Location name){
        //TODO
    }

    public void denyRemoveRequest(Location name){
        //TODO
    }
}
