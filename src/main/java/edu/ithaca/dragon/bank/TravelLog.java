package edu.ithaca.dragon.bank;

import java.util.ArrayList;

public class TravelLog {
    
    private String title;
    private String description;
    private ArrayList<Location> places;

    public TravelLog(String title, String description){
        if (title.length() > 0){
            if (description.length() > 0){
                this.title = title;
                this.description = description;
                places = new ArrayList<Location>();
            }
            else throw new IllegalArgumentException("The description for this travel log has been left empty.");
        }
        else throw new IllegalArgumentException("The title of this travel log has been left empty.");
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

}
