package edu.ithaca.dragon.traveltracker;

import java.util.ArrayList;
import java.util.List;

public class Library {

    ArrayList<Location> locationList;
    ArrayList<Account> accountList;

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
        for(int i = 0; i < locationList.length; i++){
            if(locationList.get(i).getName() == name){
                locationList.remove(i);
            }
        }
    }

    Boolean isValid(String name){
        return true;
    }

}