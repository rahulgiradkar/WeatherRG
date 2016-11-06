package com.rg.rahul.weather.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 04-11-2016.
 */
public class DateBaseData {
    private String date;

    public List<TimeBaseData> getTimeBaseDatas() {
        return timeBaseDatas;
    }

    private List<TimeBaseData> timeBaseDatas=new ArrayList<>();
    public String getDate() {
        return date;
    }

    public void addTimeBase(TimeBaseData timeBaseData) {
        timeBaseDatas.add(timeBaseData);
    }

    public void setDate(String date) {
        this.date = date;
    }
}
