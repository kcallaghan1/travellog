package edu.ithaca.dragon.traveltracker;

public class Date {

    private String month;
    private int day;
    private int year;

    public Date(String month, int day, int year){
        if (verifyDate(month, day, year)){
            this.month = month;
            this.day = day;
            this.year = year;
        }else{
            throw new IllegalArgumentException("Invalid month, date or year");
        }
        
    }

    boolean verifyDate(String month, int day, int year){
        return month.length() > 0 && day > 0 && day < 32 && year < 2022;
    }

    String getMonth(){
        return month;
    }

    int getDay(){
        return day;
    }

    int getYear(){
        return year;
    }
    
}
