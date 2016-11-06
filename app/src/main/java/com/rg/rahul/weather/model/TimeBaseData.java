package com.rg.rahul.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul on 03-11-2016.
 */
public class TimeBaseData {
    /*;


    Rain rain;
    Snow snow;
    ;*/
    @SerializedName("main")
    private Main main;
    @SerializedName("weather")
    private List<Weather> weathers;

    @SerializedName("dt_txt")
    private String dt_txt;
    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("wind")
    private Wind wind;

    public TimeBaseData(Main main, List<Weather> weathers, String dt_txt, Clouds clouds, Wind wind) {
        this.main = main;
        this.weathers = weathers;
        this.dt_txt = dt_txt;
        this.clouds = clouds;
        this.wind = wind;
    }


    public Main getMain() {
        return main;
    }

    public Weather getWeather() {
        return weathers.get(0);
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
