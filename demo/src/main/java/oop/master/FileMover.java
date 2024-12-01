package oop.master;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class FileMover {
    public static void moveCarFile(String id, String sourcePath, String targetPath) {
        // Assuming the car's file is named "Car{id}.json"
        String carFileName = "Car" + id + ".json";
        
        // Correct the paths to include the file names
        Path queueDirectory = Paths.get(sourcePath);
        Path servedDirectory = Paths.get(targetPath);
        
        // Construct the full path for the car file in the queue directory
        Path carFilePath = queueDirectory.resolve(carFileName);
        
        // Construct the full path for the destination file in the served directory
        Path servedFilePath = servedDirectory.resolve(carFileName);
    
        try {
            // Move the car file from the queue directory to the served directory
            Files.move(carFilePath, servedFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed to move file " + carFileName + ": " + e.getMessage());
        }
    }
}
