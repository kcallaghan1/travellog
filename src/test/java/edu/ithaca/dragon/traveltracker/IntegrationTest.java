package edu.ithaca.dragon.traveltracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class IntegrationTest {
    
    @Test
    public static void main(String[] args){
        Library lib = new Library();

        Account account1 = new Account("harry", "harry99@gmail.com", "password");
        lib.add(account1);
        Account account2 = new Account("larry", "larry99@gmail.com", "password");
        lib.add(account2);
        System.out.println("\nAccount names:");
        for(int i = 0; i < lib.getAccountList().size(); i++){
            System.out.println(i + 1 +  ". " + lib.getAccountList().get(i).getUsername());
        }

        Location loc1 = new Location("Ithaca College", "953 Danby Road");
        lib.add(loc1);
        Location loc2 = new Location("Madison Square Garden", "4 Pennsylvania Plaza");
        lib.add(loc2);
        Location loc3 = new Location("White House", "1600 Pennsylvania Avenue");
        lib.add(loc3);
        Location loc4 = new Location("Domino's Pizza", "30 Frank Lloyd Wright Drive");
        lib.add(loc4);

        System.out.println("\nLocations:");
        for(int i = 0; i < lib.getLocationList().size(); i++){
            System.out.println(i+1 + ". " + lib.getLocationList().get(i).getName() + ", " + lib.getLocationList().get(i).getAddress());
        }


        TravelLog log1 = new TravelLog("NY", "My trip to New York!");
        log1.addDestination(loc1);
        log1.addDestination(loc2);
        account1.addTravelLog(log1);

        System.out.println();
        System.out.println(account1.getUsername() + "'s Travel Logs");
        for(int i = 0; i < account1.getTravelLogs().size(); i++){
            System.out.println(i + 1 + ". " + account1.getTravelLogs().get(i).getTitle() + " - " + account1.getTravelLogs().get(i).getDescription());
        }

        System.out.println("\n" + account1.getTravelLogs().get(0).getTitle() + ": ");
        for(int i = 0; i < account1.getTravelLogs().get(0).getPlaces().size(); i++){
            System.out.println(i + 1 + ". " + account1.getTravelLogs().get(0).getPlaces().get(i).getName() + " - " + account1.getTravelLogs().get(0).getPlaces().get(i).getAddress());
        }
    }
}
