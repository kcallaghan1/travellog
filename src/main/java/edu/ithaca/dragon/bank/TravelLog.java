package edu.ithaca.dragon.bank;

import java.util.ArrayList;

public class TravelLog {
    
    private String title;
    private String description;
    private ArrayList<Location> places;

    public TravelLog(String title, String description){
        this.title = title;
        this.description = description;
        places = new ArrayList<Location>();
    }
}
