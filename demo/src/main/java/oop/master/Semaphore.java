package oop.master;

import oop.master.car_utils.Car;
import oop.master.car_utils.CarKey;
import oop.master.car_utils.CarStation;
import oop.master.enums.CarTypes;
import oop.master.enums.PassengerTypes;
import java.util.HashMap;
import java.util.Map;

public class Semaphore {

    private final Map<CarKey, CarStation> carStationMap = new HashMap<>();

    public void registerStation(CarTypes carType, PassengerTypes passengerType, CarStation station) {
        carStationMap.put(new CarKey(carType, passengerType), station);
    }

    public void guideCar(Car car) {
        CarKey key = new CarKey(car.getType(), car.getPassengers());
        CarStation targetStation = carStationMap.get(key);
        if (targetStation != null) {
            targetStation.addCar(car);
        } else {
            throw new IllegalArgumentException("Target station was not found");
        }
    }

    public void serveCarsType(CarTypes carType, PassengerTypes passengerType) {
        CarKey key = new CarKey(carType, passengerType);
        CarStation station = carStationMap.get(key);
        if (station == null){
            throw new IllegalArgumentException("Station not found for car type");
        }
        station.serveCars();
    }

    public int getCarsCountForType(CarTypes carType, PassengerTypes passengerType) {
        CarKey key = new CarKey(carType, passengerType);
        CarStation station = carStationMap.get(key);
        return station != null ? station.getTotalCarsServed() : 0;
    }
    public int getPassegersCountForType(CarTypes carType, PassengerTypes passengerType) {
        CarKey key = new CarKey(carType, passengerType);
        CarStation station = carStationMap.get(key);
        return station != null ? station.getTotalPassengersServed() : 0;
    }
    public Integer getTotalCarConsumptionForType(CarTypes carType, PassengerTypes passengerType) {
        CarKey key = new CarKey(carType, passengerType);
        CarStation station = carStationMap.get(key);
        return station != null ? station.getTotalConsumption() : 0;
    }

    public int getDinnerServedCountForType(CarTypes carType, PassengerTypes passengerType) {
        CarKey key = new CarKey(carType, passengerType);
        CarStation station = carStationMap.get(key);
        return station != null ? station.getTotalDinnersServed() : 0;
    }

    public void printStatus() {
        // Initialize the maps for counting car types and consumption
        Map<String, Integer> carCounts = new HashMap<>();
        Map<String, Integer> consumption = new HashMap<>();
        int totaldinnerServedCount = 0;
        int totalConsumptionElectric = 0;
        int totalConsumptionGas = 0;
        int gasCarsCount = 0;
        int electricCarsCount = 0;
        int peopleCount = 0;
        int robotsCount = 0;
    
        // Loop through all car types and passenger types to gather stats
        for (CarTypes carType : CarTypes.values()) {
            for (PassengerTypes passengerType : PassengerTypes.values()) {
                // Get statistics for each car type and passenger type
                int carsServedCount = getCarsCountForType(carType, passengerType);
                int passegersServedCount = getPassegersCountForType(carType, passengerType);
                int dinnerServedCount = getDinnerServedCountForType(carType, passengerType);
                int totalConsumption = getTotalCarConsumptionForType(carType, passengerType);
    
                // Update the counters
                if (carType == CarTypes.ELECTRIC) {
                    electricCarsCount += carsServedCount;
                    totalConsumptionElectric += totalConsumption;
                } else if (carType == CarTypes.GAS) {
                    gasCarsCount += carsServedCount;
                    totalConsumptionGas += totalConsumption;
                }
    
                if (passengerType == PassengerTypes.PEOPLE) {
                    peopleCount += passegersServedCount;
                } else if (passengerType == PassengerTypes.ROBOTS) {
                    robotsCount += passegersServedCount;
                }
                totaldinnerServedCount += dinnerServedCount;
            }
        }
        consumption.put("ELECTRIC", totalConsumptionElectric);
        consumption.put("GAS", totalConsumptionGas);
        carCounts.put("ELECTRIC", electricCarsCount);
        carCounts.put("GAS", gasCarsCount);
        carCounts.put("PEOPLE", peopleCount);
        carCounts.put("ROBOTS", robotsCount);
        carCounts.put("DINING", totaldinnerServedCount);
        carCounts.put("NOT_DINING", peopleCount + robotsCount - totaldinnerServedCount);
        // Print the stats in the JSON format
        System.out.print("{");
        System.out.print("\"ELECTRIC\": " + carCounts.get("ELECTRIC") + ",");
        System.out.print(" \"GAS\": " + carCounts.get("GAS") + ",");
        System.out.print(" \"PEOPLE\": " + carCounts.get("PEOPLE") + ",");
        System.out.print(" \"ROBOTS\": " + carCounts.get("ROBOTS") + ",");
        System.out.print(" \"DINING\": " + carCounts.get("DINING") + ",");
        System.out.print(" \"NOT_DINING\": " + carCounts.get("NOT_DINING") + ",");
        System.out.print(" \"CONSUMPTION\": {");
        System.out.print("\"ELECTRIC\": " + consumption.get("ELECTRIC") + ",");
        System.out.print(" \"GAS\": " + consumption.get("GAS"));
        System.out.print("}");
        System.out.print("}");
        System.out.println();
    }
    
    
}
