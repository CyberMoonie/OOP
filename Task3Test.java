import java.util.LinkedList;
import java.util.Queue;

class Car {
    String id;
    String type; // ELECTRIC or GAS
    String passengers; // PEOPLE or ROBOTS
    boolean isDining;

    Car(String id, String type, String passengers, boolean isDining) {
        this.id = id;
        this.type = type;
        this.passengers = passengers;
        this.isDining = isDining;
    }
}

// Interface for Dining
interface Dineable {
    void serveDinner(String carId);
}

// Interface for Refueling
interface Refuelable {
    void refuel(String carId);
}

// CarStation class implementing IoC and DI
class CarStation {
    private Dineable diningService;
    private Refuelable refuelingService;
    private Queue<Car> queue;

    public CarStation(Dineable diningService, Refuelable refuelingService) {
        this.diningService = diningService;
        this.refuelingService = refuelingService;
        this.queue = new LinkedList<>();
    }

    public void addCar(Car car) {
        queue.add(car);
        System.out.println("Added car: " + car.id);
    }

    public void serveCars() {
        while (!queue.isEmpty()) {
            Car car = queue.poll();

            if (car.isDining && diningService != null) {
                diningService.serveDinner(car.id);
            }

            if (refuelingService != null) {
                refuelingService.refuel(car.id);
            }

            System.out.println("Finished serving car: " + car.id);
        }
    }
}

// Implementation of PeopleDinner
class PeopleDinner implements Dineable {
    @Override
    public void serveDinner(String carId) {
        System.out.println("Serving dinner to people in car " + carId + ".");
    }
}

// Implementation of RobotDinner
class RobotDinner implements Dineable {
    @Override
    public void serveDinner(String carId) {
        System.out.println("Serving dinner to robots in car " + carId + ".");
    }
}

// Implementation of ElectricStation
class ElectricStation implements Refuelable {
    @Override
    public void refuel(String carId) {
        System.out.println("Refueling electric car " + carId + ".");
    }
}

// Implementation of GasStation
class GasStation implements Refuelable {
    @Override
    public void refuel(String carId) {
        System.out.println("Refueling gas car " + carId + ".");
    }
}

// Main class for testing
public class Task3Test {
    public static void main(String[] args) {
        // Test Case 1: Gas station serving gas cars with robot dining service
        System.out.println("=== Test Case 1: Gas Station ===");
        CarStation gasStation = new CarStation(new RobotDinner(), new GasStation());
        gasStation.addCar(new Car("1", "GAS", "ROBOTS", true));
        gasStation.addCar(new Car("2", "GAS", "ROBOTS", false));
        gasStation.serveCars();

        System.out.println("\n=== Test Case 2: Electric Station ===");
        // Test Case 2: Electric station serving electric cars with people dining service
        CarStation electricStation = new CarStation(new PeopleDinner(), new ElectricStation());
        electricStation.addCar(new Car("3", "ELECTRIC", "PEOPLE", true));
        electricStation.addCar(new Car("4", "ELECTRIC", "PEOPLE", false));
        electricStation.serveCars();

        System.out.println("\n=== Test Case 3: Basic Station ===");
        // Test Case 3: No dining service (only refueling)
        CarStation basicStation = new CarStation(null, new GasStation());
        basicStation.addCar(new Car("5", "GAS", "PEOPLE", false));
        basicStation.serveCars();
    }
}