package oop.master.car_utils;

import oop.master.FileMover;
import oop.master.queue.Queue;
import oop.master.services.Dineable;
import oop.master.services.Refuelable;

public class CarStation {
    private Dineable dinnerService;
    private Refuelable fuelService;
    private Queue<Car> queue;
    private int totalConsumption = 0;
    private int totalPassengersServed = 0;
    private int totalCarsServed = 0;
    private int totalDinnersServed = 0;
    public int getTotalDinnersServed() {
		return this.totalDinnersServed;
	}
    public int getTotalCarsServed() {
		return this.totalCarsServed;
	}
	public int getTotalPassengersServed() {
		return this.totalPassengersServed;
	}
	public int getTotalConsumption() {
		return this.totalConsumption;
	}

	public Dineable getDinnerService() {
		return this.dinnerService;
	}

	public Refuelable getFuelService() {
		return this.fuelService;
	}

	public Queue<Car> getQueue() {
		return this.queue;
	}

    public CarStation(Dineable dinnerService, Refuelable fuelService, Queue<Car> queue) {
        this.dinnerService = dinnerService;
        this.fuelService = fuelService;
        this.queue = queue;
    }

    public void serveCars(){
        while (!queue.isEmpty()){
            Car car = queue.dequeue();
            if (car != null){

                if (car.isDining()){
                    dinnerService.serveDinner(car.getId().toString());
                    totalDinnersServed++;
                }
                totalCarsServed++;
                totalPassengersServed++;
                totalConsumption = totalConsumption + car.getConsumption();
                fuelService.refuel(car.getId().toString());
                FileMover.moveCarFile(car.getId().toString(), "src/main/resources/pending", "src/main/resources/served");
            }
        }
    }
    

    public void addCar(Car car){
        queue.enqueue(car);
    }

    
}
