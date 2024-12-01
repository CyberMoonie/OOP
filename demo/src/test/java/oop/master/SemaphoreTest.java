package oop.master;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import oop.master.car_utils.Car;
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

public class SemaphoreTest {

    private Semaphore semaphore;
    private Gson gson;

    @BeforeEach
    void setUp() {
        semaphore = new Semaphore();
        gson = new GsonBuilder().create();

        Dineable peopleDinnerService = new PeopleDinner();
        Dineable robotDinnerService = new RobotDinner();
        Refuelable gasFuelService = new GasStation();
        Refuelable electricFuelService = new ElectricStation();

        // Register CarStations
        semaphore.registerStation(CarTypes.GAS, PassengerTypes.PEOPLE, 
            new CarStation(peopleDinnerService, gasFuelService, new LinearQueue<>()));
        semaphore.registerStation(CarTypes.GAS, PassengerTypes.ROBOTS, 
            new CarStation(robotDinnerService, gasFuelService, new LimitQueue<>(3)));
        semaphore.registerStation(CarTypes.ELECTRIC, PassengerTypes.ROBOTS, 
            new CarStation(peopleDinnerService, electricFuelService, new PriorityQueue<>(CarComparator::compareByConsumption)));
    }

    @Test
    void testGuideElectricCarWithDining() {
        String jsonCar = """
        {
          "id": 1,
          "type": "ELECTRIC",
          "passengers": "ROBOTS",
          "isDining": true,
          "consumption": 30
        }
        """;

        Car electricCar = gson.fromJson(jsonCar, Car.class);
        semaphore.guideCar(electricCar);
        semaphore.serveCarsType(CarTypes.ELECTRIC, PassengerTypes.ROBOTS);
        assertEquals(1, semaphore.getDinnerServedCountForType(CarTypes.ELECTRIC, PassengerTypes.ROBOTS));
        assertTrue(electricCar.isDining());
    }

    @Test
    void testServeElectricCar() {
        String jsonCar = """
        {
          "id": 2,
          "type": "ELECTRIC",
          "passengers": "ROBOTS",
          "isDining": true,
          "consumption": 40
        }
        """;

        Car electricCar = gson.fromJson(jsonCar, Car.class);
        semaphore.guideCar(electricCar);

        assertEquals(1, semaphore.getCarsCountForType(CarTypes.ELECTRIC, PassengerTypes.ROBOTS));
        semaphore.serveCarsType(CarTypes.ELECTRIC, PassengerTypes.ROBOTS);
        assertEquals(0, semaphore.getCarsCountForType(CarTypes.ELECTRIC, PassengerTypes.ROBOTS));
        assertEquals(1, semaphore.getDinnerServedCountForType(CarTypes.ELECTRIC, PassengerTypes.ROBOTS));
    }

    @Test
    void testInvalidCarThrowsException() {
        String invalidJsonCar = """
        {
          "id": 3,
          "type": "HYBRID",
          "passengers": "ALIENS",
          "isDining": false,
          "consumption": 25
        }
        """;

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            Car car = gson.fromJson(invalidJsonCar, Car.class);
            semaphore.guideCar(car);
        });
        System.out.println(e.getMessage());
    }
    @Test
    void testUnregisteredThrowsException() {

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            semaphore.serveCarsType(CarTypes.ELECTRIC, PassengerTypes.PEOPLE);
        });
        System.out.println(e.getMessage());
    }
    @Test
    void testStats() {
        String jsonCar1 = """
            {
              "id": 1,
              "type": "ELECTRIC",
              "passengers": "ROBOTS",
              "isDining": true,
              "consumption": 30
            }
            """;
            Car car1 = gson.fromJson(jsonCar1, Car.class);

        semaphore.guideCar(car1);
        semaphore.serveCarsType(CarTypes.ELECTRIC, PassengerTypes.ROBOTS);
        assertEquals(1, semaphore.getDinnerServedCountForType(CarTypes.ELECTRIC, PassengerTypes.ROBOTS));
        semaphore.printStatus();
        String jsonCar2 = """
        {
          "id": 2,
          "type": "ELECTRIC",
          "passengers": "ROBOTS",
          "isDining": true,
          "consumption": 40
        }
        """;
        
        Car car2 = gson.fromJson(jsonCar2, Car.class);

        semaphore.guideCar(car2);

        semaphore.serveCarsType(CarTypes.ELECTRIC, PassengerTypes.ROBOTS);

    }
    @Test
    void testDinning() {
        assertEquals(0, semaphore.getDinnerServedCountForType(CarTypes.GAS, PassengerTypes.PEOPLE));
        assertEquals(0, semaphore.getDinnerServedCountForType(CarTypes.ELECTRIC, PassengerTypes.PEOPLE));
        assertEquals(0, semaphore.getDinnerServedCountForType(CarTypes.ELECTRIC, PassengerTypes.ROBOTS));

    }
}

