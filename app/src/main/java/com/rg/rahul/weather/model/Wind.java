package com.rg.rahul.weather.model;

import android.support.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 03-11-2016.
 */
public class Wind {
    @SerializedName("speed")
    private double speed;

    public double getSpeed() {
        return speed;
    }

    public double getDeg() {
        return deg;
    }

    @SerializedName("deg")
    private double deg;

    public Wind(double speed, double deg) {
        this.speed = speed;
        this.deg = deg;
    }
}
