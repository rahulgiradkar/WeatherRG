package com.rg.rahul.weather.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 03-11-2016.
 */

public class City {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("country")
    private String country;
    @SerializedName("coord")
    private Coord coord;


    public City(int id, String name, String country, Coord coord) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.coord = coord;

    }

    public String getName() {
        return name;
    }

    //Date dt;

}
