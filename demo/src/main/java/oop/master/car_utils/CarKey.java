package oop.master.car_utils;

import java.util.Objects;

import oop.master.enums.CarTypes;
import oop.master.enums.PassengerTypes;

public class CarKey {
    private final CarTypes carType;
    private final PassengerTypes passengerType;

    public CarKey(CarTypes carType, PassengerTypes passengerType) {
        this.carType = carType;
        this.passengerType = passengerType;
    }

    @Override
    public boolean equals(Object o) {
        // Consider equal if it matches the reference value
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        // Consider equal if it also matches also the types
        CarKey carKey = (CarKey) o;
        return carType == carKey.carType && passengerType == carKey.passengerType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(carType, passengerType);
    }
}
