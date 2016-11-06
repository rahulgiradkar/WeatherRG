package com.rg.rahul.weather.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 03-11-2016.
 */
public class Coord {
    @SerializedName("lon")
    private double lon;
    @SerializedName("lat")
    private double lat;

    public Coord(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }
}
