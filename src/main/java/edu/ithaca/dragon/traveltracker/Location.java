package edu.ithaca.dragon.traveltracker;

import java.util.ArrayList;
import java.util.List;

public class Location {

    private String name;
    private String address;
    private ArrayList<Categories> categories;

    public Location(String name, String address){
        if (name.length() > 0){
            if (address.length() > 0){
                this.name = name;
                this.address = address;
                categories = new ArrayList<>();
            }
            else throw new IllegalArgumentException("The name for this location has been left empty.");
        }
        else throw new IllegalArgumentException("The address of this location has been left empty.");
    }

    public Location(String name, String address, ArrayList<Categories> categories){
        if (name.length() > 0){
            if (address.length() > 0){
                this.name = name;
                this.address = address;
                this.categories = categories;
            }
            else throw new IllegalArgumentException("The name for this location has been left empty.");
        }
        else throw new IllegalArgumentException("The address of this location has been left empty.");
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public List<Categories> getCategories(){
        return categories;
    }
}
