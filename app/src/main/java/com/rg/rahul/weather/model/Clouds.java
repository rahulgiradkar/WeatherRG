package com.rg.rahul.weather.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 03-11-2016.
 */
public class Clouds {
    @SerializedName("all")
    private double all;

    public Clouds(double all) {
        this.all = all;
    }

    public double getAll() {
        return all;
    }
}
