package com.rg.rahul.weather;

import android.app.Application;

import com.karumi.dexter.Dexter;

/**
 * Created by Rahul on 06-11-2016.
 */

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

                super.onCreate();
                Dexter.initialize(this);

        }
    }

