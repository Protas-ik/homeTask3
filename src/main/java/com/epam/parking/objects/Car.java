package com.epam.parking.objects;

import com.epam.parking.service.ParkingService;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Car extends Thread {
    private int placeNumber = 0;
    private Boolean isTakePlace = false;
    public Boolean goAway = false;
    private ParkingService parkingService;

    @Override
    public void run(){
        try {
            parkingService.takeCar(this);
            parkingService.leaveCar(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ParkingService getParkingService() {
        return parkingService;
    }

    public void setParkingService(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    public Boolean getIsTakePlace() {
        return isTakePlace;
    }

    public void setIsTakePlace(boolean isTakePlace) {
        this.isTakePlace= isTakePlace;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }
}
