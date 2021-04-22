package edu.ithaca.dragon.traveltracker;

import java.io.IOException;

public class IntegrationTest2 {
    public static void main(String[] args){
        try {
            Pictures.addPicture("Ithaca1.jpg", "C:/Users/Kenny/Desktop/Ithaca-College-New-York.jpg");
            System.out.println(Pictures.pics.get(0));
        } catch (IOException e) {
        }
    }
}
