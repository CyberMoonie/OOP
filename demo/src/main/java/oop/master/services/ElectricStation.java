package oop.master.services;

// ElectricStation class
public class ElectricStation implements Refuelable {
    private int electricCarCount = 0;
    @Override
    public void refuel(String carId) {
        electricCarCount++;
        System.out.println("Refueling electric car " + carId + ".");
    }

    @Override
    public int getCount() {
        return electricCarCount;
    }
}
