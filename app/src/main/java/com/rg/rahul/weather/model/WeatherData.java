package com.rg.rahul.weather.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rahul on 03-11-2016.
 */

public class WeatherData {
    @SerializedName("city")
    private City city;
    @SerializedName("list")
    private List<TimeBaseData> list;

    public WeatherData(City city, List<TimeBaseData> list) {
        this.city = city;
        this.list = list;
    }

    public List<TimeBaseData> getList() {
        return list;
    }

    public City getCity() {
        return city;
    }



    public List<DateBaseData> getDateWiseList() throws ParseException {
        SimpleDateFormat defaultFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat showedFormat=new SimpleDateFormat("yyyy-MM-dd");
        List< DateBaseData> dateBaseDatas=new ArrayList<>();

        for (TimeBaseData timeBaseData :list){
            Date defaultDate = defaultFormat.parse(timeBaseData.getDt_txt());
            String showedFormatDate=showedFormat.format(defaultDate);
            boolean flag=false;
            for (DateBaseData dateBaseData: dateBaseDatas){
                if(showedFormatDate.equalsIgnoreCase(dateBaseData.getDate())){
                    dateBaseData.addTimeBase(timeBaseData);
                    flag=true;
                    break;

                }
            }
            if(!flag){
                DateBaseData dateBaseData=new DateBaseData();
                dateBaseDatas.add(dateBaseData);
                dateBaseData.setDate(showedFormatDate);
                dateBaseData.addTimeBase(timeBaseData);
            }
        }
        return dateBaseDatas;
    }

    public TimeBaseData getCurrentDateTimeData() throws ParseException {
        SimpleDateFormat defaultFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat showedFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date currenDate = new Date();
        for (TimeBaseData timeBaseData: list){
            Date timeBaseDatadate=defaultFormat.parse( timeBaseData.getDt_txt());

            long diff=currenDate.getTime()-timeBaseDatadate.getTime();
            long diffHours = diff / (60 * 60 * 1000) % 24;
            if(diffHours>=0&& diffHours<=3){
return  timeBaseData;
            }
        }
    return null;
    }

}
