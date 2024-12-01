package oop.master.services;

// RobotDinner class
public class RobotDinner implements Dineable {
    private int robotCount = 0;

    @Override
    public void serveDinner(String carId) {
        robotCount++;
        System.out.println("Serving dinner to robots in car " + carId + ".");
    }

    @Override
    public int getCount() {
        return robotCount;
    }
}