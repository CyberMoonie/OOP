import java.util.ArrayList;
import java.util.List;

// Define the Dineable interface
interface Dineable {
    void serveDinner(String carId);
}

// Define the Refuelable interface
interface Refuelable {
    void refuel(String carId);
}

// Concrete implementation for people dining
class PeopleDinner implements Dineable {
    @Override
    public void serveDinner(String carId) {
        System.out.println("Serving dinner to people in car " + carId + ".");
    }
}

// Concrete implementation for robot dining
class RobotDinner implements Dineable {
    @Override
    public void serveDinner(String carId) {
        System.out.println("Serving dinner to robots in car " + carId + ".");
    }
}

// Concrete implementation for refueling gas cars
class GasStation implements Refuelable {
    @Override
    public void refuel(String carId) {
        System.out.println("Refueling gas car " + carId + ".");
    }
}

// Concrete implementation for refueling electric cars
class ElectricStation implements Refuelable {
    @Override
    public void refuel(String carId) {
        System.out.println("Refueling electric car " + carId + ".");
    }
}

// Car class to represent a car's attributes
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

// ServiceStation class to manage cars and track stats
class ServiceStation {
    private Refuelable refuelable;
    private Dineable dineable;
    private int servedCars;
    private int peopleServed;
    private int robotsServed;

    public ServiceStation(Refuelable refuelable, Dineable dineable) {
        this.refuelable = refuelable;
        this.dineable = dineable;
        this.servedCars = 0;
        this.peopleServed = 0;
        this.robotsServed = 0;
    }

    public void serveCar(Car car) {
        servedCars++;
        if (car.isDining && dineable != null) {
            if (car.passengers.equals("PEOPLE")) {
                peopleServed++;
                dineable.serveDinner(car.id);
            } else if (car.passengers.equals("ROBOTS")) {
                robotsServed++;
                dineable.serveDinner(car.id);
            }
        }

        if (refuelable != null) {
            refuelable.refuel(car.id);
        }
    }

    public int getTotalCarsServed() {
        return servedCars;
    }

    public int getPeopleServed() {
        return peopleServed;
    }

    public int getRobotsServed() {
        return robotsServed;
    }
}

// Main class to test the implementation
public class Task2TestExtended {
    public static void main(String[] args) {
        // Create stations
        ServiceStation electricStation1 = new ServiceStation(new ElectricStation(), new PeopleDinner());
        ServiceStation electricStation2 = new ServiceStation(new ElectricStation(), null); // No dining
        ServiceStation gasStation = new ServiceStation(new GasStation(), new RobotDinner());

        // Create a list of cars
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("1", "ELECTRIC", "PEOPLE", true));
        cars.add(new Car("2", "ELECTRIC", "PEOPLE", false));
        cars.add(new Car("3", "ELECTRIC", "ROBOTS", false));
        cars.add(new Car("4", "GAS", "ROBOTS", true));
        cars.add(new Car("5", "GAS", "PEOPLE", false));
        cars.add(new Car("6", "GAS", "ROBOTS", false));

        // Serve cars
        for (Car car : cars) {
            if (car.type.equals("ELECTRIC")) {
                if (car.isDining) {
                    electricStation1.serveCar(car);
                } else {
                    electricStation2.serveCar(car);
                }
            } else if (car.type.equals("GAS")) {
                gasStation.serveCar(car);
            }