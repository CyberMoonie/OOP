package oop.master;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import oop.master.car_utils.CarComparator;
import oop.master.car_utils.CarStation;
import oop.master.enums.CarTypes;
import oop.master.enums.PassengerTypes;
import oop.master.queue.LimitQueue;
import oop.master.queue.LinearQueue;
import oop.master.queue.PriorityQueue;
import oop.master.services.Dineable;
import oop.master.services.ElectricStation;
import oop.master.services.GasStation;
import oop.master.services.PeopleDinner;
import oop.master.services.Refuelable;
import oop.master.services.RobotDinner;

public class Main {
    private static final Path QUEUE_DIR = Paths.get("src/main/resources/queue");
    private static final Path PENDING_DIR = Paths.get("src/main/resources/pending");
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static Semaphore semaphore;
    public static void main(String[] args) {
        semaphore = new Semaphore();
        Scheduler scheduler = new Scheduler(semaphore);

        Dineable peopleDinnerService = new PeopleDinner();
        Dineable robotDinnerService = new RobotDinner();
        Refuelable gasFuelService = new GasStation();
        Refuelable electricFuelService = new ElectricStation();
        // Register CarStations
        semaphore.registerStation(CarTypes.GAS, PassengerTypes.PEOPLE, 
            new CarStation(peopleDinnerService, gasFuelService, new LinearQueue<>()));
        semaphore.registerStation(CarTypes.GAS, PassengerTypes.ROBOTS, 
            new CarStation(robotDinnerService, gasFuelService, new LimitQueue<>(1000)));
        semaphore.registerStation(CarTypes.ELECTRIC, PassengerTypes.PEOPLE, 
            new CarStation(peopleDinnerService, electricFuelService, new PriorityQueue<>(CarComparator::compareByConsumption)));
        semaphore.registerStation(CarTypes.ELECTRIC, PassengerTypes.ROBOTS, 
            new CarStation(robotDinnerService, electricFuelService, new PriorityQueue<>(CarComparator::compareByDinner)));        


        
        // Start the guide car and serve car processes
        scheduler.startGuideCarProcess(2);
        scheduler.startServeCarProcess(4, CarTypes.GAS, PassengerTypes.PEOPLE);
        scheduler.startServeCarProcess(4, CarTypes.GAS, PassengerTypes.ROBOTS); 
        scheduler.startServeCarProcess(4, CarTypes.ELECTRIC, PassengerTypes.PEOPLE);
        scheduler.startServeCarProcess(4, CarTypes.ELECTRIC, PassengerTypes.ROBOTS);
        scheduler.startPrintStatsProcess(5);
        monitorDirectories();

    }
    private static void monitorDirectories() {
        executorService.submit(() -> {
            while (true) {
                try {
                    if (isDirectoryEmpty(QUEUE_DIR) && isDirectoryEmpty(PENDING_DIR)) {
                        // One final call
                        semaphore.printStatus();
                        System.out.println("Both queue and pending folders are empty. Stopping the program...");
                        executorService.shutdownNow(); // Stop all ongoing processes
                        System.exit(0);  // Stop the program
                    }
                    Thread.sleep(1000); // Check every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }
    private static boolean isDirectoryEmpty(Path directory) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            return !stream.iterator().hasNext();
        } catch (IOException e) {
            System.err.println("Error checking directory: " + directory + " - " + e.getMessage());
            return false;
        }
    }
}

