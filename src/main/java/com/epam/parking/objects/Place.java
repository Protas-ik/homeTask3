package com.epam.parking.objects;

public class Place {
    private Boolean isFree = true;
    private int id = 0;

    public Place(int id){
        this.id = id;
    }

    public Boolean getIsFree(){
        return isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
