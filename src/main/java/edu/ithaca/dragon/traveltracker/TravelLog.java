package edu.ithaca.dragon.traveltracker;

import java.util.ArrayList;
import java.util.List;

public class TravelLog {
    
    private String title;
    private String description;
    private List<Location> places;

    public TravelLog(String title, String description){
        if (title.length() > 0){
            if (description.length() > 0){
                this.title = title;
                this.description = description;
                places = new ArrayList<>();
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

    public List<Location> getPlaces(){
        return places;
    }

    public void addDestination(Location name) throws IllegalArgumentException{
        boolean sameLocation = false;
        for (int i = 0; i < places.size(); i++){
            if (places.get(i) == name){
                sameLocation = true;
            }
        }
        if (sameLocation)
            throw new IllegalArgumentException("This location is already in the travel log.");
        else
            places.add(name);
    }

}
