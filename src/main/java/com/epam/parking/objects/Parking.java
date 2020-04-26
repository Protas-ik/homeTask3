package com.epam.parking.objects;

import java.util.concurrent.locks.ReentrantLock;

public class Parking {
    private static Parking instance;
    private Place[] places;
    private static final int PLACES_COUNT = 5;
    private static ReentrantLock locker = new ReentrantLock();

    private Parking(){
        this.places = new Place[PLACES_COUNT];
        for(int i = 0; i < places.length; i++){
            places[i] = new Place(i);
            places[i].setIsFree(true);
        }
    }

    public static Parking getInstance(){
        if(instance == null){
            locker.lock();
            try {
                instance = new Parking();
                return instance;
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }


    public  Place[] getPlaces(){
        return  places;
    }

    public static int getPlacesCount(){
        return PLACES_COUNT;
    }
}
