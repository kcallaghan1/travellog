package edu.ithaca.dragon.traveltracker;

import java.util.ArrayList;

public class Library {

    static ArrayList<Location> addLocationRequests = new ArrayList<>();
    static ArrayList<Location> removeLocationRequests = new ArrayList<>();
    ArrayList<Location> locationList;
    ArrayList<Account> accountList;

    public Library(){
        locationList = new ArrayList<Location>();
        accountList = new ArrayList<Account>();
    }

    void add(Account a){
        accountList.add(a);
    }

    void addAccount(Account a){
        accountList.add(a);
    }

    void addAccount(String username, String email, String password){
        accountList.add(new Account(username, email, password));
    }

    void deleteAccount(int i){
        accountList.remove(i);
    }

    void deleteAccount(Account a, String username, String password){
        if(a.verifyAccount(username, password)){
            accountList.remove(a);
        }
    }

    void add(Location loc){
        locationList.add(loc);
    }

    void addLocation(String name, String address, ArrayList<Categories> categories){
        locationList.add(new Location(name, address, categories));
    }

    void addLocation(Location loc){
        locationList.add(loc);
    }

    void removeLocation(int i){
        locationList.remove(i);
    }

    void removeLocation(String name){
        for(int i = 0; i < locationList.size(); i++){
            if(locationList.get(i).getName().equals(name)){
                locationList.remove(i);
            }
        }
    }

    static void addLocationRequest(Location name) throws IllegalArgumentException{
        boolean sameLocation = false;
        for (int i = 0; i < addLocationRequests.size(); i++){
            if (addLocationRequests.get(i).equals(name)){
                sameLocation = true;
            }
        }
        if (sameLocation)
            throw new IllegalArgumentException("This location is already in the request list");
        else
            addLocationRequests.add(name);
    }

    static void removeLocationRequest(Location name) throws IllegalArgumentException{
        boolean sameLocation = false;
        for (int i = 0; i < removeLocationRequests.size(); i++){
            if (removeLocationRequests.get(i).equals(name)){
                sameLocation = true;
            }
        }
        if (sameLocation)
            throw new IllegalArgumentException("This location is already in the request list");
        else
            removeLocationRequests.add(name);
    }

    Boolean isValid(String name){
        return true;
    }

    ArrayList<Account> getAccountList(){
        return accountList;
    }

    ArrayList<Location> getLocationList(){
        return locationList;
    }

    static ArrayList<Location> getAddLocationRequests(){
        return addLocationRequests;
    }

    static ArrayList<Location> getRemoveLocationRequests(){
        return removeLocationRequests;
    }

}