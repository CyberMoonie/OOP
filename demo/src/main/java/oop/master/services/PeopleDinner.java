package oop.master.services;

// PeopleDinner class
public class PeopleDinner implements Dineable {
    private int peopleCount = 0;

    @Override
    public void serveDinner(String carId) {
        peopleCount++;
        System.out.println("Serving dinner to people in car " + carId + ".");
    }

    @Override
    public int getCount() {
        return peopleCount;
    }
}
