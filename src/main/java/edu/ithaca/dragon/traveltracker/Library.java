package edu.ithaca.dragon.traveltracker;

import java.util.ArrayList;

public class Library {

    ArrayList<Location> locationList;
    ArrayList<Account> accountList;

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

    Boolean isValid(String name){
        return true;
    }

}