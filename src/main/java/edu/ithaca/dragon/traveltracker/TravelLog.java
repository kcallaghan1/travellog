package edu.ithaca.dragon.traveltracker;

import java.util.ArrayList;
import java.util.List;

public class TravelLog {
    
    private String title;
    private String description;
    private int logId;
    private List<Location> places;
    private List<Date> dates;

    public TravelLog(String title, String description){
        if (title.length() > 0){
            if (description.length() > 0){
                this.title = title;
                this.description = description;
                this.logId = -1;
                places = new ArrayList<>();
                dates = new ArrayList<>();
            }
            else throw new IllegalArgumentException("The description for this travel log has been left empty.");
        }
        else throw new IllegalArgumentException("The title of this travel log has been left empty.");
    }



    public TravelLog(String title, String description, int logId){
        if (title.length() > 0){
            if (description.length() > 0){
                this.title = title;
                this.description = description;
                this.logId = logId;
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

    public int getLogId(){
        return logId;
    }

    public List<Date> getDates(){
        return dates;
    }

    public void addDestination(Location name) throws IllegalArgumentException{
        boolean sameLocation = false;
        for (int i = 0; i < places.size(); i++){
            if (places.get(i).equals(name)){
                sameLocation = true;
            }
        }
        if (sameLocation)
            throw new IllegalArgumentException("This location is already in the travel log.");
        else
            places.add(name);
    }

    public void addDestinationV2(Location name, Date date) throws IllegalArgumentException{
        boolean sameLocation = false;
        for (int i = 0; i < places.size(); i++){
            if (places.get(i).equals(name)){
                sameLocation = true;
            }
        }
        if (sameLocation)
            throw new IllegalArgumentException("This location is already in the travel log.");
        else{
            places.add(name);
            dates.add(date);
        }
    }

    public void removeLocation(int idx){
        places.remove(idx);
    }

}
