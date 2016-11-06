package com.rg.rahul.weather.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 03-11-2016.
 */
public class Weather {
    @SerializedName("id")
    private int id;
    @SerializedName("main")
    private String main;
    @SerializedName("description")
    private String description;
    @SerializedName("icon")
    private String icon;
    private Bitmap bitmapIcon;

    public String getDescription() {
        return description;
    }
    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getIcon() {
        return icon;
    }



    public Weather(int id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public void setBitmapIcon(Bitmap bitmapIcon) {
        this.bitmapIcon = bitmapIcon;
    }

    public Bitmap getBitmapIcon() {
        return bitmapIcon;
    }
}
