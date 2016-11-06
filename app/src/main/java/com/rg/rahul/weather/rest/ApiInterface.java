package com.rg.rahul.weather.rest;

import com.rg.rahul.weather.model.WeatherData;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Rahul on 03-11-2016.
 */
public interface ApiInterface {

    @GET("data/2.5/forecast")
    Call<WeatherData> getWeather(@QueryMap Map<String, Object> options);

    @GET("img/w/{id}")
    Call<ResponseBody> fetchCaptcha(@Path("id") String id);



}
