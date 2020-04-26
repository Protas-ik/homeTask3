package com.epam.parking.service;

import com.epam.parking.objects.Car;
import com.epam.parking.objects.Parking;
import org.apache.log4j.Logger;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingService {
    private Semaphore semaphore;
    private ReentrantLock locker = new ReentrantLock();
    private Parking parking;
    private static final Logger LOGGER = Logger.getLogger(Parking.class);

    public ParkingService(Semaphore semaphore, Parking parking){
        this.semaphore = semaphore;
        this.parking = parking;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void takeCar(Car car) throws InterruptedException {
        LOGGER.info(car.getName() + " car come to parking");
        if(!car.goAway) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                LOGGER.error("Acquire failed");
                Thread.currentThread().interrupt();
            }
            locker.lock();

            for (int i = 0; i < parking.getPlaces().length; i++) {
                if (parking.getPlaces()[i].getIsFree()) {
                    parking.getPlaces()[i].setIsFree(false);
                    car.setPlaceNumber(parking.getPlaces()[i].getId());
                    car.setIsTakePlace(true);
                    break;
                }
            }
            LOGGER.info("Place " + car.getPlaceNumber() + " take a car " + Thread.currentThread().getName());
            locker.unlock();
            try {
                LOGGER.info("car stay at parking");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.error("car failed");
            }
        }
    }

    public void changePlace(Car car, Car car1){
        int step = car.getPlaceNumber();
        car.setPlaceNumber(car1.getPlaceNumber());
        car1.setPlaceNumber(step);
    }

    public void leaveCar(Car car) throws InterruptedException {
        locker.lock();
        if(car.getIsTakePlace()){
            parking.getPlaces()[car.getPlaceNumber()].setIsFree(true);
            car.setIsTakePlace(false);
            LOGGER.info("Car " + Thread.currentThread().getName() + " go away from place  " + car.getPlaceNumber());
            semaphore.release();
        }
        else{
            TimeUnit.SECONDS.sleep(2);
            if(!car.getIsTakePlace()){
                car.goAway = true;
                System.out.println("Car go away");
                LOGGER.info("Car go away");
            }
        }
        locker.unlock();
    }
}
