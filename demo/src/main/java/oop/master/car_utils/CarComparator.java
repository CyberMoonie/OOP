package oop.master.car_utils;

public class CarComparator{
    public static int compareByConsumption(Car c1, Car c2) {
        return Integer.compare(c1.getConsumption(), c2.getConsumption());
    }
    public static int compareByDinner(Car c1, Car c2) {
        return Integer.compare(c1.isDining() ? 1 : 0, c2.isDining() ? 1 : 0);
    }
}
