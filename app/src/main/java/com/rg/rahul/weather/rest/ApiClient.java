package com.rg.rahul.weather.rest;

import android.support.v7.app.AppCompatDelegate;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rahul on 03-11-2016.
 */
public class ApiClient {
    private static AppCompatDelegate client;

    public static final String BASE_URL = "http://api.openweathermap.org/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
