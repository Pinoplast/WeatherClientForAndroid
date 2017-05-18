package com.example.andriikolomys.start;

import android.provider.ContactsContract;

/**
 * Created by andrii.kolomys on 5/16/17.
 */

public class DataByDay {
    private int id;
    private String dayDate;
    private String clouds;
    private double maxT;
    private double minT;
    private int imageId;


    public String getDayDate(){
        return dayDate;
    }
    public void setDayDate(String dayDate)
    {
        this.dayDate = dayDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public double getMaxT() {
        return maxT;
    }

    public void setMaxT(double maxT) {
        this.maxT = maxT;
    }

    public double getMinT() {
        return minT;
    }

    public void setMinT(double minT) {
        this.minT = minT;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
