package com.rg.rahul.weather.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 03-11-2016.
 */
public class Main {

    @SerializedName("temp")
    private double temp;
    @SerializedName("temp_min")
    private double temp_min;
    @SerializedName("temp_max")
    private double temp_max;
    @SerializedName("pressure")
    private double pressure;
    @SerializedName("sea_level")
    private double sea_level;
    @SerializedName("grnd_level")
    private double grnd_level;
    @SerializedName("humidity")
    private double humidity;

    public double getTemp() {
        return temp;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getPressure() {
        return pressure;
    }

    public double getSea_level() {
        return sea_level;
    }

    public double getGrnd_level() {
        return grnd_level;
    }

    public double getHumidity() {
        return humidity;
    }




    public Main(double temp, double temp_min, double temp_max, double pressure, double sea_level, double grnd_level, double humidity) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.sea_level = sea_level;
        this.grnd_level = grnd_level;
        this.humidity = humidity;
    }
}
