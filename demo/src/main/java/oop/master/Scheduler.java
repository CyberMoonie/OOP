package oop.master;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import oop.master.enums.CarTypes;
import oop.master.enums.PassengerTypes;

import java.util.concurrent.*;

import com.google.gson.Gson;

import oop.master.car_utils.Car;

public class Scheduler {

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Semaphore semaphore;

    public Scheduler(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    // Method to start the serve car process for specific car type and passenger type
    public void startServeCarProcess(long serveCarInterval, CarTypes carType, PassengerTypes passengerType) {
        executorService.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // Serve cars for the specific car type and passenger type
                    Thread.sleep(serveCarInterval * 1000); // Sleep for the specified interval
                    semaphore.serveCarsType(carType, passengerType);
                    System.out.println();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    public void startPrintStatsProcess(long printStatusInterval) {
        executorService.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // Serve cars for the specific car type and passenger type
                    Thread.sleep(printStatusInterval * 1000); // Sleep for the specified interval
                    semaphore.printStatus();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    private static Car readCarFromQueueFolder() {
        Car car = null;
        Path queueDir = Paths.get("src/main/resources/queue");    
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(queueDir, "*.json")) {
            // Collect paths into a list
            List<Path> carFiles = new ArrayList<>();
            for (Path entry : stream) {
                carFiles.add(entry);
            }
            
            // Sort files based on the number after "Car" in the filename
            carFiles.sort(new Comparator<Path>() {
                @Override
                public int compare(Path c1, Path c2) {
                    // Regular expression to match the number after "Car"
                    String regex = "Car(\\d+)";
    
                    // Extract numbers from the filenames
                    int number1 = extractNumber(c1.getFileName().toString(), regex);
                    int number2 = extractNumber(c2.getFileName().toString(), regex);
    
                    // Compare the extracted numbers
                    return Integer.compare(number1, number2);
                }
    
                private int extractNumber(String filename, String regex) {
                    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
                    java.util.regex.Matcher matcher = pattern.matcher(filename);
    
                    if (matcher.find()) {
                        return Integer.parseInt(matcher.group(1));
                    }
                    return Integer.MAX_VALUE;
                }
            });

            // Now read the first car in the sorted order and move it to the pending directory
            Path entry = carFiles.get(0); 
            // Read the car file
            String json = new String(Files.readAllBytes(entry));
            System.out.println("Reading car: " + entry.getFileName());
            car = new Gson().fromJson(json, Car.class);

            // Move the car file to the pending directory
            FileMover.moveCarFile(car.getId().toString(), "src/main/resources/queue", "src/main/resources/pending");
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return car;
    }
    
    public void startGuideCarProcess(long guideCarInterval) {
        executorService.submit(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(guideCarInterval * 1000); // Sleep for the specified interval
                    Car car = readCarFromQueueFolder();
                    if (car != null){
                        semaphore.guideCar(car);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    // Method to stop the scheduler and all threads
    public void stopScheduler() {
        executorService.shutdownNow(); // Interrupts all tasks
    }
}
