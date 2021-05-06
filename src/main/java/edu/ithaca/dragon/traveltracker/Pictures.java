package edu.ithaca.dragon.traveltracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class Pictures {

    static ArrayList<String> pics;

    public Pictures(){
        pics = new ArrayList<String>();
    }

    public static void addPicture(String name, String path) throws IOException{
        copyImage(name, path);
        pics.add(name);
    }
    
    public static void copyImage(String name, String path) throws IOException{
        Path source = Paths.get(path);
        Path targetDir = Paths.get("src/images/");
        Path target = targetDir.resolve(name);
        System.out.println("copying into " + target);
        try{
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        addPicture("star.jpg", "C:/Users/dxuma/Downloads/starbucks.jpg");
        System.out.println(pics.get(0));
    }
}
